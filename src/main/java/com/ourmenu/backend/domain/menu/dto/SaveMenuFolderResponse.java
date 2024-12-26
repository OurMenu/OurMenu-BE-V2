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
public class SaveMenuFolderResponse {

    private Long menuFolderId;
    private String menuFolderTitle;
    private String menuFolderUrl;
    private String menuFolderIcon;
    private List<Long> menuIds;

    public static SaveMenuFolderResponse from(MenuFolder menuFolder){
        return SaveMenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(menuFolder.getImgUrl())
                .menuFolderIcon(menuFolder.getIcon())
                .build();
    }
}
