package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapSearchDto {

    private Long mapId;
    private String menuTitle;
    private String storeTitle;
    private String storeAddress;

    public static MapSearchDto from(Menu menu){
        return MapSearchDto.builder()
                .mapId(menu.getStore().getMap().getId())
                .menuTitle(menu.getTitle())
                .storeTitle(menu.getStore().getTitle())
                .storeAddress(menu.getStore().getAddress())
                .build();
    }

    public static MapSearchDto from(OwnedMenuSearch ownedMenuSearch){
        return MapSearchDto.builder()
                .menuTitle(ownedMenuSearch.getMenuTitle())
                .storeTitle(ownedMenuSearch.getStoreTitle())
                .storeAddress(ownedMenuSearch.getStoreAddress())
                .build();
    }
}
