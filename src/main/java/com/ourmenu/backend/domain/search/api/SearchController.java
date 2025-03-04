package com.ourmenu.backend.domain.search.api;

import com.ourmenu.backend.domain.search.application.SearchService;
import com.ourmenu.backend.domain.search.dto.GetSearchHistoryResponse;
import com.ourmenu.backend.domain.search.dto.GetStoreResponse;
import com.ourmenu.backend.domain.search.dto.SearchCriterionDto;
import com.ourmenu.backend.domain.search.dto.SearchStoreResponse;
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

@Tag(name = "크롤링 검색 API")
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "식당 검색", description = "식당을 검색한다. 메뉴 이름과 가게 이름이 검색 범주에 포함된다."
            + "건국대학교 경도, 위도는 127.0759204, 37.5423265 이다")
    @GetMapping("/priored/stores/menus")
    public ApiResponse<List<SearchStoreResponse>> searchStore(@RequestParam(name = "query") String query,
                                                              @RequestParam(name = "mapX", defaultValue = "127.0759204") double mapX,
                                                              @RequestParam(name = "mapY", defaultValue = "37.5423265") double mapY) {
        SearchCriterionDto searchCriterionDto = SearchCriterionDto.of(query, mapX, mapY);
        List<SearchStoreResponse> response = searchService.searchStore(searchCriterionDto);
        return ApiUtil.success(response);
    }

    @Operation(summary = "식당 상세조회", description = "식당을 상세 조회한다. 일부 정보는 제한될 수 있다.")
    @GetMapping("/priored/stores/{storeId}")
    public ApiResponse<GetStoreResponse> getStore(@RequestParam(name = "is-crawled") boolean isCrawled,
                                                  @PathVariable(name = "storeId") String storeId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        GetStoreResponse response = searchService.getStore(userDetails.getId(), isCrawled, storeId);
        return ApiUtil.success(response);
    }

    @Operation(summary = "식당 검색 기록 조회", description = "식당 검색 기록을 조회한다.")
    @GetMapping("priored/users/{userId}/histories")
    public ApiResponse<List<GetSearchHistoryResponse>> getSearchHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetSearchHistoryResponse> response = searchService.getSearchHistory(userDetails.getId());
        return ApiUtil.success(response);
    }
}
