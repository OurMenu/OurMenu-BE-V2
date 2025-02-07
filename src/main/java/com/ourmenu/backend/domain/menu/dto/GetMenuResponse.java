package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetMenuResponse {

    private Long menuId;
    private String menuTitle;
    private int menuPrice;
    private MenuPin menuPin;
    private String storeTitle;
    private String storeAddress;
    private List<String> tags;
    private List<String> menuImgUrls;

    private List<SimpleMenuFolder> menuFolders;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class SimpleMenuFolder {
        private Long menuFolderId;
        private String menuFolderTitle;
        private MenuFolderIcon menuFolderIcon;
    }

    public static GetMenuResponse of(Menu menu, List<String> imgUrls, List<String> tagNames,
                                     List<MenuFolder> menuFolders) {
        List<SimpleMenuFolder> simpleMenuFolders = menuFolders.stream()
                .map(menuFolder -> SimpleMenuFolder.builder()
                        .menuFolderId(menuFolder.getId())
                        .menuFolderTitle(menuFolder.getTitle())
                        .menuFolderIcon(menuFolder.getIcon())
                        .build())
                .toList();
        return GetMenuResponse.builder().
                menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuPin(menu.getPin())
                .storeAddress(menu.getStore().getAddress())
                .storeTitle(menu.getStore().getTitle())
                .tags(tagNames)
                .menuImgUrls(imgUrls)
                .menuFolders(simpleMenuFolders)
                .build();
    }
}


