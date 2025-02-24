package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuFolderInfoOnMapDto {

    private String menuFolderTitle;
    private MenuFolderIcon menuFolderIcon;
    private int menuFolderCount;

    public static MenuFolderInfoOnMapDto of(MenuFolder menuFolder, int count) {
        return MenuFolderInfoOnMapDto.builder()
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderIcon(menuFolder.getIcon())
                .menuFolderCount(count)
                .build();
    }

    public static MenuFolderInfoOnMapDto empty() {
        return MenuFolderInfoOnMapDto.builder()
                .menuFolderTitle("")
                .menuFolderIcon(null)
                .menuFolderCount(0)
                .build();
    }
}
