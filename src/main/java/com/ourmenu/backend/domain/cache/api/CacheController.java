package com.ourmenu.backend.domain.cache.api;

import com.ourmenu.backend.domain.cache.application.CacheService;
import com.ourmenu.backend.domain.cache.dto.GetCacheInfoResponse;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("api/cache-data")
    public ApiResponse<GetCacheInfoResponse> getCacheInfo() {
        GetCacheInfoResponse response = cacheService.getCacheInfo();

        return ApiUtil.success(response);
    }
}
