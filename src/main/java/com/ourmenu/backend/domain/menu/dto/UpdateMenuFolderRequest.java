package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class UpdateMenuFolderRequest {

    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;

}
