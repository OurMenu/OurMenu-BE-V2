package com.ourmenu.backend.domain.menu.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuFolderMenuResponse {

    private Long menuId;
    private String menuTitle;
    private String storeTitle;
    private String storeAddress;
    private int menuPrice;
    private String menuImgUrl;
    private LocalDateTime createdAt;

    public static MenuFolderMenuResponse of(MenuSimpleDto menuSimpleDto, String menuImgUrl) {
        return MenuFolderMenuResponse.builder()
                .menuId(menuSimpleDto.getMenuId())
                .menuTitle(menuSimpleDto.getMenuTitle())
                .storeTitle(menuSimpleDto.getStoreTitle())
                .storeAddress(menuSimpleDto.getStoreAddress())
                .menuPrice(menuSimpleDto.getMenuPrice())
                .menuImgUrl(menuImgUrl)
                .createdAt(menuSimpleDto.getCreatedAt())
                .build();
    }
}
