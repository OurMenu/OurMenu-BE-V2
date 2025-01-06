package com.ourmenu.backend.domain.search.api;

import com.ourmenu.backend.domain.search.application.SearchService;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/priored/stores/menus")
    public ApiResponse<Void> searchStore(@RequestParam(name = "query") String query,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        searchService.searchStore(userDetails.getId(), query);
        return ApiUtil.successOnly();
    }
}
