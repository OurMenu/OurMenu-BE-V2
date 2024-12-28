package com.ourmenu.backend.domain.menu.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class MenuFolderDto {

    private MultipartFile menuFolderImg;
    private String menuFolderTitle;
    private String menuFolderIcon;
    private List<Long> menuIds;
    private Long userId;

    public static MenuFolderDto of(SaveMenuFolderRequest saveMenuFolderRequest,
                                   MultipartFile menuFolderImg, Long userId) {
        return MenuFolderDto.builder()
                .menuFolderImg(menuFolderImg)
                .menuFolderTitle(saveMenuFolderRequest.getMenuFolderTitle())
                .menuFolderIcon(saveMenuFolderRequest.getMenuFolderIcon())
                .menuIds(saveMenuFolderRequest.getMenuIds())
                .userId(userId)
                .build();
    }

    public static MenuFolderDto of(UpdateMenuFolderRequest updateMenuFolderRequest,
                                   MultipartFile menuFolderImg, Long userId) {
        return MenuFolderDto.builder()
                .menuFolderImg(menuFolderImg)
                .menuFolderTitle(updateMenuFolderRequest.getMenuFolderTitle())
                .menuFolderIcon(updateMenuFolderRequest.getMenuFolderIcon())
                .menuIds(updateMenuFolderRequest.getMenuIds())
                .userId(userId)
                .build();
    }
}
