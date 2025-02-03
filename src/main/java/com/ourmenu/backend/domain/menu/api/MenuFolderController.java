package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MenuFolderService;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderIndexRequest;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메뉴판 API")
@RestController
@RequestMapping("/api/menu-folders")
@RequiredArgsConstructor
public class MenuFolderController {

    private final MenuFolderService menuFolderService;

    @Operation(summary = "메뉴판 등록", description = "메뉴판 리스트를 조회한다.")
    @GetMapping
    public ApiResponse<List<GetMenuFolderResponse>> getMenuFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetMenuFolderResponse> response = menuFolderService.findAllMenuFolder(userDetails.getId());
        return ApiUtil.success(response);
    }

    @Operation(summary = "메뉴판 저장", description = "메뉴판을 저장한다. 메뉴와 관계를 설정할 수 있다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<SaveMenuFolderResponse> saveMenuFolder(@ModelAttribute SaveMenuFolderRequest request,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuFolderDto menuFolderDto = MenuFolderDto.of(request, request.getMenuFolderImg(), userDetails.getId());
        SaveMenuFolderResponse response = menuFolderService.saveMenuFolder(menuFolderDto);
        return ApiUtil.success(response);
    }

    @Operation(summary = "메뉴판 수정", description = "메뉴판을 수정한다. 입력에 따라 수정 범위를 결정한다")
    @PatchMapping(value = "/{menuFolderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UpdateMenuFolderResponse> updateMenuFolder(@PathVariable("menuFolderId") Long menuFolderId,
                                                                  @ModelAttribute UpdateMenuFolderRequest request,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuFolderDto menuFolderDto = MenuFolderDto.of(request, request.getMenuFolderImg(), userDetails.getId());
        UpdateMenuFolderResponse updateMenuFolderResponse = menuFolderService.updateMenuFolder(userDetails.getId(),
                menuFolderId, menuFolderDto);
        return ApiUtil.success(updateMenuFolderResponse);
    }

    @Operation(summary = "메뉴판 인덱스 수정", description = "메뉴판 인덱스를 수정한다.")
    @PatchMapping("/{menuFolderId}/index")
    public ApiResponse<UpdateMenuFolderResponse> updateMenuFolderIndex(@PathVariable("menuFolderId") Long menuFolderId,
                                                                       @RequestBody UpdateMenuFolderIndexRequest request,
                                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        UpdateMenuFolderResponse updateMenuFolderResponse = menuFolderService.updateMenuFolderIndex(userDetails.getId(),
                menuFolderId, request.getIndex());
        return ApiUtil.success(updateMenuFolderResponse);
    }

    @Operation(summary = "메뉴판 삭제", description = "메뉴판 삭제 및 해당 메뉴판과 연관련 엔티티를 삭제한다")
    @DeleteMapping("/{menuFolderId}")
    public ApiResponse<Void> deleteMenuFolder(@PathVariable("menuFolderId") Long menuFolderId,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        menuFolderService.deleteMenuFolder(userDetails.getId(), menuFolderId);
        return ApiUtil.successOnly();
    }
}
