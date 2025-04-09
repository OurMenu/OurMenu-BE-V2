package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
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
    private String menuFolderUrl;
    private MenuFolderIcon menuFolderIcon;
    private List<Long> menuIds;
    private int index;

    public static MenuFolderResponse of(MenuFolder menuFolder, List<MenuMenuFolder> menuFolders,
                                        String defaultMenuFolderImgUrl) {
        String menuFolderImgUrl = menuFolder.getImgUrl();
        if (menuFolderImgUrl == null) {
            menuFolderImgUrl = defaultMenuFolderImgUrl;
        }

        List<Long> menuIds = menuFolders.stream()
                .map(menuMenuFolder -> menuMenuFolder.getMenu().getId())
                .toList();
        return MenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(menuFolderImgUrl)
                .menuFolderIcon(menuFolder.getIcon())
                .menuIds(menuIds)
                .index(menuFolder.getIndex())
                .build();
    }
}
