package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveMenuFolderRequest {

    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;
}
