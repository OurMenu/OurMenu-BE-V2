package com.ourmenu.backend.domain.cache.api;

import com.ourmenu.backend.domain.cache.application.CacheService;
import com.ourmenu.backend.domain.cache.dto.GetCacheInfoResponse;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
