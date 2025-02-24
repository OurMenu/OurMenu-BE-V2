package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapSearchHistoryDto {

    private Long menuId;
    private String menuTitle;
    private String storeTitle;
    private String storeAddress;

    public static MapSearchHistoryDto from(OwnedMenuSearch ownedMenuSearch){
        return MapSearchHistoryDto.builder()
                .menuId(ownedMenuSearch.getMenuId())
                .menuTitle(ownedMenuSearch.getMenuTitle())
                .storeTitle(ownedMenuSearch.getStoreTitle())
                .storeAddress(ownedMenuSearch.getStoreAddress())
                .build();
    }
}
