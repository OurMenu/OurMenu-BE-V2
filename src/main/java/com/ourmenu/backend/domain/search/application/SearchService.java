package com.ourmenu.backend.domain.search.application;

import com.ourmenu.backend.domain.search.dao.SearchableStoreRepository;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
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

    @Transactional(readOnly = true)
    public List<SearchableStore> searchStore(Long userId, String query) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<SearchableStore> searchableStores = searchableStoreRepository.findByMenuNameOrStoreNameContaining(
                query, pageRequest);

        //가게 이름 우선적으로 필터링
        List<SearchableStore> storesWithNameMatch = searchableStores.stream()
                .filter(store -> store.getTitle().contains(query))
                .collect(Collectors.toList());

        //메뉴 이름은 후순위
        List<SearchableStore> otherStores = searchableStores.stream()
                .filter(store -> !store.getTitle().contains(query))
                .toList();
        storesWithNameMatch.addAll(otherStores);

        return storesWithNameMatch.stream().limit(5).collect(Collectors.toList());
    }
}
