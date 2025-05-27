package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuFolderInfoOnMapDto {

    private String menuFolderTitle;
    private String menuFolderIconImgUrl;
    private int menuFolderCount;

    public static MenuFolderInfoOnMapDto of(MenuFolder menuFolder, int count, UrlConverter urlConverter) {
        String menuFolderIconImgUrl = urlConverter.getMenuFolderImgUrl(menuFolder.getIcon());

        return MenuFolderInfoOnMapDto.builder()
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderIconImgUrl(menuFolderIconImgUrl)
                .menuFolderCount(count)
                .build();
    }

    public static MenuFolderInfoOnMapDto empty() {
        return MenuFolderInfoOnMapDto.builder()
                .menuFolderTitle("")
                .menuFolderIconImgUrl(null)
                .menuFolderCount(0)
                .build();
    }
}
