package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class GetMenuFolderMenuResponse {

    private Long menuFolderId;
    private String menuFolderTitle;
    private String menuFolderImgUrl;
    private MenuFolderIcon menuFolderIcon;
    private List<MenuFolderMenuResponse> menus;

    public static GetMenuFolderMenuResponse of(MenuFolder menuFolder, String defaultMenuFolderImgUrl,
                                               List<MenuFolderMenuResponse> menus) {
        String menuFolderImgUrl = menuFolder.getImgUrl();
        if (menuFolderImgUrl == null) {
            menuFolderImgUrl = defaultMenuFolderImgUrl;
        }

        return GetMenuFolderMenuResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderImgUrl(menuFolderImgUrl)
                .menuFolderIcon(menuFolder.getIcon())
                .menus(menus)
                .build();
    }

}
