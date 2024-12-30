package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveMenuRequest {

    private String menuTitle;
    private int menuPrice;
    private int menuPin;
    private String menuMemoTitle;
    private String menuMemoContent;
    private List<Long> menuFolderIds;
    private boolean isCrawled;
    private List<String> tags;
    private String storeTitle;
    private String storeAddress;
    private Double mapX;
    private Double mapY;
}
