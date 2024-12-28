package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.MenuFolder;
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

    public static GetMenuFolderResponse from(MenuFolder menuFolder) {
        return GetMenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(menuFolder.getImgUrl())
                .menuFolderIcon(menuFolder.getIcon())
                .index(menuFolder.getIndex())
                .build();
    }
}
