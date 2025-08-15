package com.ourmenu.backend.domain.cache.api;

import com.ourmenu.backend.domain.cache.application.CacheService;
import com.ourmenu.backend.domain.cache.dto.GetCacheInfoResponse;
import com.ourmenu.backend.domain.cache.dto.GetMenuFolderIconResponse;
import com.ourmenu.backend.domain.cache.dto.GetMenuPinResponse;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "캐시 API")
@RestController
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("api/cache-data")
    public ApiResponse<GetCacheInfoResponse> getCacheInfo() {
        GetCacheInfoResponse response = cacheService.getCacheInfo();

        return ApiUtil.success(response);
    }

    @GetMapping("api/cache-data/menuFolderIcons")
    public ApiResponse<List<GetMenuFolderIconResponse>> getMenuFolderIconsResponse() {
        List<GetMenuFolderIconResponse> response = cacheService.getMenuFolderIcons();

        return ApiUtil.success(response);
    }

    @GetMapping("api/cache-data/menuPins")
    public ApiResponse<List<GetMenuPinResponse>> getMenuPinResponse() {
        List<GetMenuPinResponse> response = cacheService.getMenuPins();

        return ApiUtil.success(response);
    }
}
