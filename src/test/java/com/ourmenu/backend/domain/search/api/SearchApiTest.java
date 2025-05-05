package com.ourmenu.backend.domain.search.api;

import com.ourmenu.backend.domain.menu.config.MenuTestConfig;
import com.ourmenu.backend.domain.search.dto.GetSearchHistoryResponse;
import com.ourmenu.backend.domain.search.dto.GetStoreResponse;
import com.ourmenu.backend.domain.search.dto.SearchStoreResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.config.GlobalDataConfig;
import com.ourmenu.backend.global.data.GlobalUserTestData;
import com.ourmenu.backend.global.response.ApiResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({GlobalDataConfig.class, TestConfig.class, MenuTestConfig.class})
@DisplayName("검색 통합 테스트")
public class SearchApiTest {

    @Autowired
    SearchController searchController;

    @Autowired
    GlobalUserTestData userTestData;

    @Autowired
    DatabaseCleaner databaseCleaner;

    CustomUserDetails testCustomUserDetails;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        testCustomUserDetails = userTestData.createTestEmailUser();
    }

    @Test
    void 식당을_최대_5개_검색_할_수_있디() {
        //given
        String searchWord = "고기";

        //when
        ApiResponse<List<SearchStoreResponse>> response = searchController.searchStore(searchWord, 127.0759204,
                37.5423265);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(response.getResponse().size()).isBetween(1, 5);
    }

    @Test
    void 검색한_식당을_상세조회_할_수_있다() {
        //given
        String searchWord = "고기";
        String storeId = getStoreIds(searchWord).stream().findFirst().orElseThrow(() -> new RuntimeException("테스트 실패"));

        //when
        ApiResponse<GetStoreResponse> response = searchController.getStore(true, storeId, testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void 검색_기록을_조회_할_수_있다() {
        //given
        String searchWord = "고기";
        String storeId = getStoreIds(searchWord).stream().findFirst().orElseThrow(() -> new RuntimeException("테스트 실패"));
        searchController.getStore(true, storeId, testCustomUserDetails);

        //when
        ApiResponse<List<GetSearchHistoryResponse>> response = searchController.getSearchHistory(
                testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(response.getResponse().size()).isEqualTo(1);
    }

    private List<String> getStoreIds(String query) {
        ApiResponse<List<SearchStoreResponse>> response = searchController.searchStore(query, 127.0759204,
                37.5423265);
        return response.getResponse().stream()
                .map(SearchStoreResponse::getStoreId)
                .toList();
    }
}
