package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MenuFolderService;
import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderResponse;
import com.ourmenu.backend.domain.user.application.UserService;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/menu-folders")
@RequiredArgsConstructor
public class MenuFolderController {

    private final MenuFolderService menuFolderService;
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<SaveMenuFolderResponse> saveMenuFolder(@RequestPart("data") SaveMenuFolderRequest request,
                                                              @RequestPart("menuFolderImg") MultipartFile menuFolderImg,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuFolderDto menuFolderDto = MenuFolderDto.of(request, menuFolderImg, userDetails.getId());
        SaveMenuFolderResponse response = menuFolderService.saveMenuFolder(menuFolderDto);
        return ApiUtil.success(response);
    }

    @PatchMapping(value = "/{menuFolderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UpdateMenuFolderResponse> updateMenuFolder(@PathVariable("menuFolderId") Long menuFolderId,
                                                                  @RequestPart("data") UpdateMenuFolderRequest request,
                                                                  @RequestPart(value = "menuFolderImg", required = false) MultipartFile menuFolderImg,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuFolderDto menuFolderDto = MenuFolderDto.of(request, menuFolderImg, userDetails.getId());
        UpdateMenuFolderResponse updateMenuFolderResponse = menuFolderService.updateMenuFolder(userDetails.getId(),
                menuFolderId, menuFolderDto);
        return ApiUtil.success(updateMenuFolderResponse);
    }

    @DeleteMapping("/{menuFolderId}")
    public ApiResponse<Void> deleteMenuFolder(@PathVariable("menuFolderId") Long menuFolderId,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        menuFolderService.deleteMenuFolder(userDetails.getId(), menuFolderId);
        return ApiUtil.successOnly();
    }

}
