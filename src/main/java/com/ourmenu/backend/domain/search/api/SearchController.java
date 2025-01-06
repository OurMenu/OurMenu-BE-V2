package com.ourmenu.backend.domain.search.api;

import com.ourmenu.backend.domain.search.application.SearchService;
import com.ourmenu.backend.domain.search.dto.SearchStoreResponse;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<List<SearchStoreResponse>> searchStore(@RequestParam(name = "query") String query) {
        List<SearchStoreResponse> response = searchService.searchStore(query);
        return ApiUtil.success(response);
    }
}
