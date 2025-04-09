package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class GetMenuFolderResponse {

    private int menuCount;
    List<MenuFolderResponse> menuFolders;

    public static GetMenuFolderResponse of(int menuCount, List<MenuFolderResponse> menuFolders) {
        return GetMenuFolderResponse.builder()
                .menuCount(menuCount)
                .menuFolders(menuFolders)
                .build();
    }
}
