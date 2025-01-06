package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MenuService;
import com.ourmenu.backend.domain.menu.dto.MenuDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<SaveMenuResponse> saveMenu(@RequestPart("data") SaveMenuRequest request,
                                                  @RequestPart(value = "menuFolderImgs", required = false) List<MultipartFile> menuFolderImgs,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuDto menuDto = MenuDto.of(request, menuFolderImgs, userDetails);
        SaveMenuResponse response = menuService.saveMenu(menuDto);
        return ApiUtil.success(response);
    }

    @DeleteMapping("/{menuId}")
    public ApiResponse<Void> deleteMenu(@PathVariable("menuId") Long menuId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        menuService.deleteMenu(userDetails.getId(), menuId);
        return ApiUtil.successOnly();
    }

}
