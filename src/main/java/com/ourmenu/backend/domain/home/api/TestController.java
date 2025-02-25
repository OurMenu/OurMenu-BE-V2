package com.ourmenu.backend.domain.home.api;

import com.ourmenu.backend.domain.home.application.StoreCacheService;
import com.ourmenu.backend.domain.home.dto.GetRecommendMenu;
import com.ourmenu.backend.domain.menu.application.MenuService;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private final StoreCacheService storeCacheService;
    private final MenuService menuService;

    @GetMapping("/test/{userId}")
    public ApiResponse<List<GetRecommendMenu>> getStore(@PathVariable Long userId) {
        // 캐시에서 데이터 조회
        List<GetRecommendMenu> getRecommendMenus = storeCacheService.getStoreResponse(userId);

        // 캐시가 없다면 DB 등에서 데이터를 가져오는 로직을 추가할 수 있음
        if (getRecommendMenus == null) {
            getRecommendMenus = menuService.findRecommendMenu(userId);
            storeCacheService.cacheStoreResponse(userId, getRecommendMenus);
        }

        return ApiUtil.success(getRecommendMenus);
    }

}
