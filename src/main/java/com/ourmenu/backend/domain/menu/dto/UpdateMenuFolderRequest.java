package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class UpdateMenuFolderRequest {

    private MultipartFile menuFolderImg;
    private Boolean isImageModified;
    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;

}
