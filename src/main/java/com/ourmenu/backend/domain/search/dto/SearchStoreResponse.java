package com.ourmenu.backend.domain.search.dto;

import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class SearchStoreResponse {

    private String storeTitle;
    private String storeAddress;
    private boolean isCrawled;
    private String storeId;

    public static SearchStoreResponse from(SearchableStore searchableStore) {
        return SearchStoreResponse.builder()
                .storeTitle(searchableStore.getTitle())
                .storeAddress(searchableStore.getAddress())
                .isCrawled(true)
                .storeId(searchableStore.getStoreId())
                .build();
    }

    public static SearchStoreResponse from(NotFoundStore notFoundStore) {
        return SearchStoreResponse.builder()
                .storeTitle(notFoundStore.getTitle())
                .storeAddress(notFoundStore.getAddress())
                .isCrawled(false)
                .storeId(notFoundStore.getStoreId())
                .build();
    }
}
