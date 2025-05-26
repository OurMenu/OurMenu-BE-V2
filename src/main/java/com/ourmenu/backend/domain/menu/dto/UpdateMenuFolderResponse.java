package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.util.UrlConverter;
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
public class UpdateMenuFolderResponse {

    private Long menuFolderId;
    private String menuFolderTitle;
    private String menuFolderUrl;
    private String menuFolderIconImgUrl;
    private List<Long> menuIds;
    private int index;

    public static UpdateMenuFolderResponse of(MenuFolder menuFolder, List<MenuMenuFolder> menuMenuFolders,
                                              String defaultMenuFolderImgUrl, UrlConverter urlConverter) {
        String menuFolderImgUrl = menuFolder.getImgUrl();
        if (menuFolderImgUrl == null) {
            menuFolderImgUrl = defaultMenuFolderImgUrl;
        }

        String menuFolderIconImgUrl = urlConverter.getMenuFolderUrl(menuFolder.getIcon());

        List<Long> menuId = menuMenuFolders.stream()
                .map(menuMenuFolder -> menuMenuFolder.getMenu().getId())
                .toList();
        return UpdateMenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(menuFolderImgUrl)
                .menuFolderIconImgUrl(menuFolderIconImgUrl)
                .menuIds(menuId)
                .index(menuFolder.getIndex())
                .build();
    }
}
