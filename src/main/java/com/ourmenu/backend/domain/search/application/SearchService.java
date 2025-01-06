package com.ourmenu.backend.domain.search.application;

import com.ourmenu.backend.domain.search.dao.NotFoundStoreRepository;
import com.ourmenu.backend.domain.search.dao.SearchableStoreRepository;
import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
import com.ourmenu.backend.domain.search.dto.SearchStoreResponse;
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
    private final KakaoApiService kakaoApiService;

    /**
     * 몽고DB -> mysql -> kakaoAPI 순으로 검색한다
     *
     * @param query
     * @return
     */
    @Transactional
    public List<SearchStoreResponse> searchStore(String query) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<SearchableStore> searchableStores = searchableStoreRepository.findByMenuNameOrStoreNameContaining(
                query, pageRequest);
        if (searchableStores.size() == 0) {
            return searchCacheEntity(query);
        }

        //가게 이름 우선적으로 필터링
        List<SearchableStore> storesWithNameMatch = searchableStores.stream()
                .filter(store -> store.getTitle().contains(query))
                .collect(Collectors.toList());

        //메뉴 이름은 후순위
        List<SearchableStore> otherStores = searchableStores.stream()
                .filter(store -> !store.getTitle().contains(query))
                .toList();
        storesWithNameMatch.addAll(otherStores);

        return storesWithNameMatch.stream()
                .limit(5)
                .map(SearchStoreResponse::from)
                .toList();
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
}
