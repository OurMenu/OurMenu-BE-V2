package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MenuFolderService;
import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.user.application.UserService;
import com.ourmenu.backend.domain.user.domain.UserDetailsImpl;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MenuFolderDto menuFolderDto = MenuFolderDto.builder()
                .menuFolderImg(menuFolderImg)
                .saveMenuFolderRequest(request)
                .user(userDetails.getUser())
                .build();
        SaveMenuFolderResponse response = menuFolderService.saveMenu(menuFolderDto);
        return ApiUtil.success(response);
    }
}
