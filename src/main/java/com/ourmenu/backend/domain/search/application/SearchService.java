package com.ourmenu.backend.domain.search.application;

import com.ourmenu.backend.domain.search.dao.NotFoundStoreRepository;
import com.ourmenu.backend.domain.search.dao.SearchableStoreRepository;
import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
import com.ourmenu.backend.domain.search.dto.SearchStoreResponse;
import java.util.List;
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

    @Transactional(readOnly = true)
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

    private List<SearchStoreResponse> searchCacheEntity(String query) {
        List<NotFoundStore> notFoundStores = notFoundStoreRepository.findByTitleContaining(query);
        if (notFoundStores.size() == 0) {
            return searchByKakaoApi(query);
        }
        return notFoundStores.stream()
                .limit(5)
                .map(SearchStoreResponse::from)
                .toList();
    }

    private List<SearchStoreResponse> searchByKakaoApi(String query) {

    }
}
