package com.ourmenu.backend.domain.search.dto;

import com.ourmenu.backend.domain.search.domain.NotOwnedMenuSearch;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetSearchHistoryResponse {

    private String menuTitle;

    private String storeAddress;

    private LocalDateTime modifiedAt;

    public static GetSearchHistoryResponse from(NotOwnedMenuSearch notOwnedMenuSearch){
        return GetSearchHistoryResponse.builder()
                .menuTitle(notOwnedMenuSearch.getTitle())
                .storeAddress(notOwnedMenuSearch.getAddress())
                .modifiedAt(notOwnedMenuSearch.getModifiedAt())
                .build();
    }
}
