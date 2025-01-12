package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveMenuRequest {

    private String menuTitle;
    private int menuPrice;
    private String menuPin;
    private String menuMemoTitle;
    private String menuMemoContent;
    private List<Long> menuFolderIds;
    private boolean isCrawled;
    private List<Tag> tags;
    private String storeTitle;
    private String storeAddress;
    private Double mapX;
    private Double mapY;
}
