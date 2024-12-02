package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SaveMenuFolderResponse {
    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;
    private Long userId;
}
