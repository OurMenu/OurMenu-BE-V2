package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class SimpleMenuFolderIconResponse {

    private MenuFolderIcon menuFolderIcon;
    private String menuFolderIconUrl;

    public static SimpleMenuFolderIconResponse of(MenuFolderIcon menuFolderIcon, String menuFolderIconUrl) {
        return SimpleMenuFolderIconResponse.builder()
                .menuFolderIcon(menuFolderIcon)
                .menuFolderIconUrl(menuFolderIconUrl)
                .build();
    }
}
