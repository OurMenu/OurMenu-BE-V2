package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class SaveMenuFolderRequest {

    private MultipartFile menuFolderImg;
    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;
}
