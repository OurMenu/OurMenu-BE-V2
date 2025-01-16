package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MapService;
import com.ourmenu.backend.domain.menu.dto.MapSearchDto;
import com.ourmenu.backend.domain.menu.dto.MenuInfoOnMapDto;
import com.ourmenu.backend.domain.menu.dto.MenuOnMapDto;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/menus/maps")
public class MapController {

    private final MapService mapService;

    /**
     * 지도 조회 API (핀)
     * @param userDetails
     * @return
     */
    @GetMapping("")
    public ApiResponse<List<MenuOnMapDto>> findMenusOnMap(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MenuOnMapDto> response = mapService.findMenusOnMap(userDetails.getId());
        return ApiUtil.success(response);
    }

    /**
     * 지도 상세 조회 API
     * @param mapId
     * @param userDetails
     * @return
     */
    @GetMapping("/{mapId}")
    public ApiResponse<List<MenuInfoOnMapDto>> findMenuInfoOnMap(@PathVariable Long mapId, @AuthenticationPrincipal CustomUserDetails userDetails){
        List<MenuInfoOnMapDto> response = mapService.findMenuOnMap(mapId, userDetails.getId());
        return ApiUtil.success(response);
    }

    /**
     * 지도 화면 검색 API
     * @param title
     * @param userDetails
     * @return
     */
    @GetMapping("/search")
    public ApiResponse<List<MapSearchDto>> findSearchOnMap(@RequestParam String title, @AuthenticationPrincipal CustomUserDetails userDetails){
        List<MapSearchDto> response = mapService.findSearchResultOnMap(title, userDetails.getId());
        return ApiUtil.success(response);
    }


}
