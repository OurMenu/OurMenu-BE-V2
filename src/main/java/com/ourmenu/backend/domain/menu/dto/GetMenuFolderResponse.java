package com.ourmenu.backend.domain.menu.dto;

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
public class GetMenuFolderResponse {

    private Long menuFolderId;
    private String menuFolderTitle;
    private String menuFolderUrl;
    private String menuFolderIcon;
    private List<Long> menuIds;
    private int index;

    public static GetMenuFolderResponse of(MenuFolder menuFolder, List<MenuMenuFolder> menuFolders) {
        List<Long> menuIds = menuFolders.stream()
                .map(MenuMenuFolder::getFolderId)
                .toList();
        return GetMenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(menuFolder.getImgUrl())
                .menuFolderIcon(menuFolder.getIcon())
                .menuIds(menuIds)
                .index(menuFolder.getIndex())
                .build();
    }
}
