package com.ourmenu.backend.domain.search.application;

import com.ourmenu.backend.domain.search.dao.NotFoundStoreRepository;
import com.ourmenu.backend.domain.search.dao.NotOwnedMenuSearchRepository;
import com.ourmenu.backend.domain.search.dao.SearchableStoreRepository;
import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.domain.NotOwnedMenuSearch;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
import com.ourmenu.backend.domain.search.dto.GetSearchHistoryResponse;
import com.ourmenu.backend.domain.search.dto.GetStoreResponse;
import com.ourmenu.backend.domain.search.dto.SearchCriterionDto;
import com.ourmenu.backend.domain.search.dto.SearchStoreResponse;
import com.ourmenu.backend.domain.search.dto.SimpleSearchDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchableStoreRepository searchableStoreRepository;
    private final NotFoundStoreRepository notFoundStoreRepository;
    private final NotOwnedMenuSearchRepository notOwnedMenuSearchRepository;
    private final KakaoApiService kakaoApiService;

    /**
     * 몽고DB -> mysql -> kakaoAPI 순으로 검색한다
     *
     * @param searchCriterionDto 검색 Dto
     * @return
     */
    @Transactional
    public List<SearchStoreResponse> searchStore(SearchCriterionDto searchCriterionDto) {
        List<SearchStoreResponse> searchStoreResponses = searchMongoStore(searchCriterionDto);
        if (searchStoreResponses.size() != 0) {
            return searchStoreResponses;
        }

        //몽고DB 에서 찾을 수 없는 경우, 캐시테이블 조회
        searchStoreResponses = searchCacheEntity(searchCriterionDto.getQuery());
        if (searchStoreResponses.size() != 0) {
            return searchStoreResponses;
        }

        //캐시테이블(mysql)에서 찾을 수 없는 경우, openAPI 호출
        return searchByKakaoApiAndSave(searchCriterionDto.getQuery());
    }

    /**
     * 가게 검색
     *
     * @param userId
     * @param isCrawled true(몽고DB), false(mysql)
     * @param storeId
     * @return
     */
    @Transactional
    public GetStoreResponse getStore(Long userId, boolean isCrawled, String storeId) {
        if (isCrawled) {
            SearchableStore searchableStore = findByStoreId(storeId);
            GetStoreResponse response = GetStoreResponse.from(searchableStore);
            saveSearchHistory(userId, response);
            return response;

        }
        NotFoundStore cacheEntityByStoreId = findCacheEntityByStoreId(storeId);
        GetStoreResponse response = GetStoreResponse.from(cacheEntityByStoreId);
        saveSearchHistory(userId, response);
        return response;
    }

    /**
     * 단순 가게 정보 Dto를 반환한다.
     *
     * @param isCrawled
     * @param storeId
     * @return
     */
    @Transactional
    public SimpleSearchDto getSearchDto(boolean isCrawled, String storeId) {
        if (isCrawled) {
            SearchableStore searchableStore = findByStoreId(storeId);
            return SimpleSearchDto.of(searchableStore, isCrawled);

        }
        NotFoundStore notFoundStore = findCacheEntityByStoreId(storeId);
        return SimpleSearchDto.of(notFoundStore, isCrawled);
    }

    /**
     * 메뉴 검색 기록 조회 유저가 소유하지 메뉴
     *
     * @param userId
     * @return
     */
    @Transactional
    public List<GetSearchHistoryResponse> getSearchHistory(Long userId) {
        List<NotOwnedMenuSearch> notOwnedMenuSearches = notOwnedMenuSearchRepository.findByUserIdOrderByModifiedAtDesc(
                userId);
        return notOwnedMenuSearches.stream()
                .map(GetSearchHistoryResponse::from)
                .toList();
    }


    /**
     * 몽고db store 검색 검색후 정렬
     *
     * @param searchCriterionDto 검색, 위치 Dto
     * @return
     */
    private List<SearchStoreResponse> searchMongoStore(SearchCriterionDto searchCriterionDto) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<SearchableStore> searchableStores = searchableStoreRepository.findByMenuNameOrStoreNameContaining(
                searchCriterionDto.getQuery(), searchCriterionDto.getMapX(), searchCriterionDto.getMapY(), pageRequest);

        List<SearchableStore> storesWithNameMatch = sortStoreByNameMatch(searchableStores,
                searchCriterionDto.getQuery());

        return storesWithNameMatch.stream()
                .limit(5)
                .map(SearchStoreResponse::from)
                .toList();
    }

    /**
     * 가게이름 -> 메뉴 이름 순으로 검색어와 유사도 기준 정렬
     *
     * @param searchableStores
     * @param query
     * @return
     */
    private List<SearchableStore> sortStoreByNameMatch(List<SearchableStore> searchableStores, String query) {
        //가게 이름 우선적으로 필터링
        List<SearchableStore> storesWithNameMatch = searchableStores.stream()
                .filter(store -> store.getTitle().contains(query))
                .collect(Collectors.toList());

        //메뉴 이름은 후순위
        List<SearchableStore> otherStores = searchableStores.stream()
                .filter(store -> !store.getTitle().contains(query))
                .toList();
        storesWithNameMatch.addAll(otherStores);

        return storesWithNameMatch;
    }

    /**
     * mysql에 식당 정보를 검색한다. mysql에는 이전 Kakao API 통신의 일부를 저장하고 있다.
     *
     * @param query
     * @return
     */
    private List<SearchStoreResponse> searchCacheEntity(String query) {
        List<NotFoundStore> notFoundStores = notFoundStoreRepository.findByTitleContaining(query);
        if (notFoundStores.size() == 0) {
            return searchByKakaoApiAndSave(query);
        }
        return notFoundStores.stream()
                .limit(5)
                .map(SearchStoreResponse::from)
                .toList();
    }

    /**
     * Kakao API로 검색후 mysql에 없다면 저장하고 반환한다.
     *
     * @param query
     * @return
     */
    private List<SearchStoreResponse> searchByKakaoApiAndSave(String query) {
        NotFoundStore notFoundStore = kakaoApiService.searchKakaoMap(query);

        Optional<NotFoundStore> optionalNotFoundStore = notFoundStoreRepository.findByStoreId(
                notFoundStore.getStoreId());
        if (optionalNotFoundStore.isPresent()) {
            notFoundStore = optionalNotFoundStore.get();
            return List.of(SearchStoreResponse.from(notFoundStore));
        }

        notFoundStore = notFoundStoreRepository.save(notFoundStore);
        return List.of(SearchStoreResponse.from(notFoundStore));
    }

    private SearchableStore findByStoreId(String storeId) {
        return searchableStoreRepository.findByStoreId(storeId).get();
    }

    private NotFoundStore findCacheEntityByStoreId(String storeId) {
        return notFoundStoreRepository.findByStoreId(storeId).get();
    }

    /**
     * 메뉴 검색 기록 저장 및 업데이트 유저가 소유하지 않은 메뉴 정보
     *
     * @param getStoreResponse
     */
    private void saveSearchHistory(Long userId, GetStoreResponse getStoreResponse) {
        Optional<NotOwnedMenuSearch> findNotOwnedMenuSearchOptional = notOwnedMenuSearchRepository.findByUserIdAndTitle(
                userId,
                getStoreResponse.getStoreTitle());

        if (findNotOwnedMenuSearchOptional.isPresent()) {
            NotOwnedMenuSearch findNotOwnedMenuSearch = findNotOwnedMenuSearchOptional.get();
            findNotOwnedMenuSearch.updateModifiedAt();
            return;
        }
        NotOwnedMenuSearch notOwnedMenuSearch = NotOwnedMenuSearch.builder()
                .title(getStoreResponse.getStoreTitle())
                .address(getStoreResponse.getStoreAddress())
                .userId(userId)
                .build();
        notOwnedMenuSearchRepository.save(notOwnedMenuSearch);
    }
}
