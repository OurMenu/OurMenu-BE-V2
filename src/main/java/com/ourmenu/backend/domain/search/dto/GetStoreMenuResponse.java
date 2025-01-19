package com.ourmenu.backend.domain.search.dto;

import com.ourmenu.backend.domain.search.domain.StoreMenu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetStoreMenuResponse {

    private String menuTitle;
    private String menuPrice;

    public static GetStoreMenuResponse from(StoreMenu storeMenu) {
        return GetStoreMenuResponse.builder()
                .menuTitle(storeMenu.getTitle())
                .menuPrice(storeMenu.getPrice())
                .build();
    }
}
