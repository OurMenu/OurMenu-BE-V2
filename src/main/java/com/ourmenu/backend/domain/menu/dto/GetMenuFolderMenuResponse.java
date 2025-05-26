package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.util.UrlConverter;
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
    private String menuFolderIconImgUrl;
    private List<MenuFolderMenuResponse> menus;

    public static GetMenuFolderMenuResponse of(MenuFolder menuFolder, String defaultMenuFolderImgUrl,
                                               List<MenuFolderMenuResponse> menus, UrlConverter urlConverter) {
        String menuFolderImgUrl = menuFolder.getImgUrl();
        if (menuFolderImgUrl == null) {
            menuFolderImgUrl = defaultMenuFolderImgUrl;
        }

        String menuFolderIconImgUrl = urlConverter.getMenuFolderUrl(menuFolder.getIcon());

        return GetMenuFolderMenuResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderImgUrl(menuFolderImgUrl)
                .menuFolderIconImgUrl(menuFolderIconImgUrl)
                .menus(menus)
                .build();
    }

}
