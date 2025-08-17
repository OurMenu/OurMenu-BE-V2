package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetMenuFolderIconResponse {

    private MenuFolderIcon menuFolderIcon;
    private String menuFolderIconUrl;

    public static GetMenuFolderIconResponse from(SimpleMenuFolderIconResponse simpleMenuFolderIconResponse) {
        return GetMenuFolderIconResponse.builder()
                .menuFolderIcon(simpleMenuFolderIconResponse.getMenuFolderIcon())
                .menuFolderIconUrl(simpleMenuFolderIconResponse.getMenuFolderIconUrl())
                .build();
    }
}
