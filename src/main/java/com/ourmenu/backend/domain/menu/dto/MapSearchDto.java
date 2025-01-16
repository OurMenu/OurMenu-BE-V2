package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.Menu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MapSearchDto {

    private String menuTitle;
    private String storeTitle;
    private String storeAddress;

    public static MapSearchDto from(Menu menu){
        return MapSearchDto.builder()
                .menuTitle(menu.getTitle())
                .storeTitle(menu.getStore().getTitle())
                .storeAddress(menu.getStore().getAddress())
                .build();
    }
}
