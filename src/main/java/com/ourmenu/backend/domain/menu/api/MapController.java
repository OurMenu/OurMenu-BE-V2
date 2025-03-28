package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MapService;
import com.ourmenu.backend.domain.menu.dto.MapSearchDto;
import com.ourmenu.backend.domain.menu.dto.MapSearchHistoryDto;
import com.ourmenu.backend.domain.menu.dto.MenuInfoOnMapDto;
import com.ourmenu.backend.domain.menu.dto.MenuOnMapDto;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지도 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/menus")
public class MapController {

    private final MapService mapService;

    @Operation(summary = "지도 조회", description = "핀을 위한 지도 기반 메뉴 리스트를 조회한다.")
    @GetMapping("/maps")
    public ApiResponse<List<MenuOnMapDto>> findMenusOnMap(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MenuOnMapDto> response = mapService.findMenusOnMap(userDetails.getId());
        return ApiUtil.success(response);
    }

    @Operation(summary = "지도 상세 조회", description = "지도 위치에 해당하는 메뉴 리스트를 상세 조회한다.")
    @GetMapping("/{mapId}/maps")
    public ApiResponse<List<MenuInfoOnMapDto>> findMenuInfoByMapId(@PathVariable Long mapId,
                                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MenuInfoOnMapDto> response = mapService.findMenuOnMap(mapId, userDetails.getId());
        return ApiUtil.success(response);
    }

    @Operation(summary = "지도 검색", description = "지도에서 메뉴를 검색한다. 메뉴 이름과 가게 이름이 검색 범주에 포함된다.")
    @GetMapping("/maps/search")
    public ApiResponse<List<MapSearchDto>> findSearchOnMap(@RequestParam String title,
                                                           @RequestParam(defaultValue = "127.0759204") double mapX,
                                                           @RequestParam(defaultValue = "37.5423265") double mapY,
                                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MapSearchDto> response = mapService.findSearchResultOnMap(title, mapX, mapY, userDetails.getId());
        return ApiUtil.success(response);
    }

    @Operation(summary = "지도 메뉴 상세조회", description = "지도에서 메뉴를 상세 조회한다.")
    @GetMapping("/maps/{menuId}/search")
    public ApiResponse<MenuInfoOnMapDto> findMenuInfoByMenuId(@PathVariable Long menuId,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        MenuInfoOnMapDto response = mapService.findMenuByMenuIdOnMap(menuId, userDetails.getId());
        return ApiUtil.success(response);
    }

    @Operation(summary = "지도 검색 기록 조회", description = "지도에서 식당 검색 기록을 조회한다.")
    @GetMapping("/maps/search-history")
    public ApiResponse<List<MapSearchHistoryDto>> findSearchHistoryOnMap(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MapSearchHistoryDto> response = mapService.findSearchHistoryOnMap(userDetails.getId());
        return ApiUtil.success(response);
    }
}
