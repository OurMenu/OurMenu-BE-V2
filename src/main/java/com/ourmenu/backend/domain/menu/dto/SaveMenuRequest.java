package com.ourmenu.backend.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ourmenu.backend.domain.tag.domain.Tag;
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
    private String storeId;
    @JsonProperty("isCrawled")
    private boolean isCrawled;
    private List<Tag> tags;
}
