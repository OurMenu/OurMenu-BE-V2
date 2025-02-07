package com.ourmenu.backend.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class SaveMenuRequest {

    List<MultipartFile> menuFolderImgs;
    private String menuTitle;
    private int menuPrice;
    private MenuPin menuPin;
    private String menuMemoTitle;
    private String menuMemoContent;
    private List<Long> menuFolderIds;
    private String storeId;
    @JsonProperty("isCrawled")
    private boolean isCrawled;
    private List<Tag> tags;
}
