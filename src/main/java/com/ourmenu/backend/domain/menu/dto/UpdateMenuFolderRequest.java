package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
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
    private MenuFolderIcon menuFolderIcon;
    private List<Long> menuIds;

}
