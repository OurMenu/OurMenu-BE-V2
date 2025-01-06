package com.ourmenu.backend.domain.search.dto;

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

    public static SearchStoreResponse from(SearchableStore searchableStore) {
        return SearchStoreResponse.builder()
                .storeTitle(searchableStore.getTitle())
                .storeAddress(searchableStore.getAddress())
                .build();
    }
}
