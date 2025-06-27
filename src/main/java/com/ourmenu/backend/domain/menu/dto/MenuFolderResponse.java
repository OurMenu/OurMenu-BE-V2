package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.application.UrlConverterService;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MenuFolderResponse {

    private Long menuFolderId;
    private String menuFolderTitle;
    private String menuFolderImgUrl;
    private String menuFolderIconImgUrl;
    private List<Long> menuIds;
    private int index;

    public static MenuFolderResponse of(MenuFolder menuFolder, List<MenuMenuFolder> menuFolders,
                                        String defaultMenuFolderImgUrl, UrlConverterService urlConverterService) {
        String menuFolderImgUrl = menuFolder.getImgUrl();
        if (menuFolderImgUrl == null) {
            menuFolderImgUrl = defaultMenuFolderImgUrl;
        }

        String menuFolderIconImgUrl = urlConverterService.getMenuFolderImgUrl(menuFolder.getIcon());

        List<Long> menuIds = menuFolders.stream()
                .map(menuMenuFolder -> menuMenuFolder.getMenu().getId())
                .toList();
        return MenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderImgUrl(menuFolderImgUrl)
                .menuFolderIconImgUrl(menuFolderIconImgUrl)
                .menuIds(menuIds)
                .index(menuFolder.getIndex())
                .build();
    }
}
