package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.user.domain.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MenuFolderDto {

    private MultipartFile menuFolderImg;
    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;
    private Long userId;

    @Builder
    public MenuFolderDto(SaveMenuFolderRequest saveMenuFolderRequest, MultipartFile menuFolderImg, User user) {
        this.menuFolderImg = menuFolderImg;
        this.menuFolderTitle = saveMenuFolderRequest.getMenuFolderTitle();
        this.menuFolderIcon = saveMenuFolderRequest.getMenuFolderIcon();
        this.menuIds = saveMenuFolderRequest.getMenuIds();
        this.userId = user.getId();
    }
}
