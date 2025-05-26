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
public class SaveMenuFolderResponse {

    private Long menuFolderId;
    private String menuFolderTitle;
    private String menuFolderUrl;
    private String menuFolderIconImgUrl;
    private List<Long> menuIds;
    private int index;

    public static SaveMenuFolderResponse of(MenuFolder menuFolder, List<Long> menuIds,
                                            String defaultMenuFolderImgUrl, UrlConverter urlConverter) {
        String menuFolderImgUrl = menuFolder.getImgUrl();
        String menuFolderIconImgUrl = urlConverter.getMenuFolderUrl(menuFolder.getIcon());
        if (menuFolderImgUrl == null) {
            menuFolderImgUrl = defaultMenuFolderImgUrl;
        }

        return SaveMenuFolderResponse.builder()
                .menuFolderId(menuFolder.getId())
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(menuFolderImgUrl)
                .menuFolderIconImgUrl(menuFolderIconImgUrl)
                .menuIds(menuIds)
                .index(menuFolder.getIndex())
                .build();
    }
}
