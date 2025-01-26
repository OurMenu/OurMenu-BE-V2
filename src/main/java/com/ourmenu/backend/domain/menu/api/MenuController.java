package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MenuService;
import com.ourmenu.backend.domain.menu.domain.SortOrder;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderMenuResponse;
import com.ourmenu.backend.domain.menu.dto.GetMenuResponse;
import com.ourmenu.backend.domain.menu.dto.MenuDto;
import com.ourmenu.backend.domain.menu.dto.MenuFilterDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.search.application.SearchService;
import com.ourmenu.backend.domain.search.dto.SimpleSearchDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메뉴 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final SearchService searchService;

    @Operation(summary = "메뉴 등록", description = "메뉴를 등록한다. 메뉴판 관계를 설정할 수 있다.")
    @PostMapping(path = "/menu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<SaveMenuResponse> saveMenu(@ModelAttribute SaveMenuRequest request,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        SimpleSearchDto simpleSearchDto = searchService.getSearchDto(request.isCrawled(), request.getStoreId());
        MenuDto menuDto = MenuDto.of(request, request.getMenuFolderImgs(), userDetails, simpleSearchDto);
        SaveMenuResponse response = menuService.saveMenu(menuDto);
        return ApiUtil.success(response);
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴 및 해당 메뉴 엔티티와 관련된 엔티티를 삭제한다.")
    @DeleteMapping("/menu/{menuId}")
    public ApiResponse<Void> deleteMenu(@PathVariable("menuId") Long menuId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        menuService.deleteMenu(userDetails.getId(), menuId);
        return ApiUtil.successOnly();
    }

    @Operation(summary = "메뉴판 메뉴 리스트 조회", description = "메뉴판의 메뉴 리스트를 조회한다. 필터를 사용할 수 있다")
    @GetMapping("/menu-folders/{menuFolderId}/menus")
    public ApiResponse<List<GetMenuFolderMenuResponse>> getMenuFolderMenus(
            @PathVariable("menuFolderId") Long menuFolderId,
            @RequestParam("tags") List<com.ourmenu.backend.domain.tag.domain.Tag> tags,
            @RequestParam(value = "minPrice", required = false) Long minPrice,
            @RequestParam(value = "maxPrice", required = false) Long maxPrice,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuFilterDto menuFilterDto = MenuFilterDto.of(tags, minPrice, maxPrice);
        List<GetMenuFolderMenuResponse> response = menuService.findMenusByMenuFolder(userDetails.getId(),
                menuFolderId, menuFilterDto);
        return ApiUtil.success(response);
    }

    @Operation(summary = "메뉴 리스트 조회", description = "메뉴 리스트를 조회한다. 필터를 사용할 수 있다")
    @GetMapping("/menus")
    public ApiResponse<List<GetMenuResponse>> getMenus(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sortOrder") SortOrder sortOrder,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuFilterDto menuFilterDto = MenuFilterDto.of(page, size, sortOrder);
        List<GetMenuResponse> response = menuService.findMenusByPageAndSort(userDetails.getId(),
                menuFilterDto);
        return ApiUtil.success(response);
    }
}
