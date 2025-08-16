package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class SaveMenuRequest {

    List<MultipartFile> menuImgs;
    private String menuTitle;
    private int menuPrice;
    private MenuPin menuPin;
    private String menuMemoTitle;
    private String menuMemoContent;
    private List<Long> menuFolderIds;
    private String storeId;
    private Boolean isCrawled;
    private List<Tag> tags;

    /**
     * request 에 null 들어오는 경우에 대해서 request 초기화 swagger 를 위한 메서드
     */
    public void initList() {
        if (menuImgs == null) {
            menuImgs = new ArrayList<>();
        }
        if (menuFolderIds == null) {
            menuFolderIds = new ArrayList<>();
        }
        if (tags == null) {
            tags = new ArrayList<>();
        }
    }
}
