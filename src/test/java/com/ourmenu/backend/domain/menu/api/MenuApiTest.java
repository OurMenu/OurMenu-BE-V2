package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.config.MenuTestConfig;
import com.ourmenu.backend.domain.menu.data.MenuTestData;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.SortOrder;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderMenuResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.config.GlobalDataConfig;
import com.ourmenu.backend.global.data.GlobalUserTestData;
import com.ourmenu.backend.global.response.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({GlobalDataConfig.class, TestConfig.class, MenuTestConfig.class})
@DisplayName("메뉴 통합 테스트")
public class MenuApiTest {

    @Autowired
    MenuController menuController;

    @Autowired
    GlobalUserTestData userTestData;

    @Autowired
    MenuTestData menuTestData;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
    }

    @Test
    void 메뉴판_정보_및_메뉴판에_속한_메뉴들을_조회_할_수_있다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        MenuFolder testMenuFolder = menuTestData.createMenuFolderWithMenu(testCustomUserDetails);

        //when
        ApiResponse<GetMenuFolderMenuResponse> response = menuController.getMenuFolderMenus(
                testMenuFolder.getId(), SortOrder.TITLE_ASC, testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(response.getResponse().getMenuFolderIcon()).isEqualTo(testMenuFolder.getIcon());
        Assertions.assertThat(response.getResponse().getMenuFolderTitle()).isEqualTo(testMenuFolder.getTitle());
        Assertions.assertThat(response.getResponse().getMenus().size()).isEqualTo(3);
    }
}
