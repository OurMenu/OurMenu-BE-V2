package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class SaveMenuFolderRequest {

    private MultipartFile menuFolderImg;
    private String menuFolderTitle;
    private MenuFolderIcon menuFolderIcon;
    private List<Long> menuIds;

    /**
     * request 에 null 들어오는 경우에 대해서 request 초기화 swagger 를 위한 메서드
     */
    public void initList() {
        if (menuIds == null) {
            menuIds = new ArrayList<>();
        }
    }
}
