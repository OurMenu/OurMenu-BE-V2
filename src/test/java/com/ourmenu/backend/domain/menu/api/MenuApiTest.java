package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.menu.config.MenuTestConfig;
import com.ourmenu.backend.domain.menu.data.MenuTestData;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.SortOrder;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderMenuResponse;
import com.ourmenu.backend.domain.menu.dto.GetSimpleMenuResponse;
import com.ourmenu.backend.domain.menu.dto.SaveMenuRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.menu.exception.NotFoundMenuException;
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

    CustomUserDetails testCustomUserDetails;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        testCustomUserDetails = userTestData.createTestEmailUser();
    }

    @Test
    void 메뉴판_정보_및_메뉴판에_속한_메뉴들을_조회_할_수_있다() {
        //given
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

    @Test
    void 메뉴를_등록_할_수_있다() {
        //given
        String menuMemoTitle = "비비큐";
        SaveMenuRequest request = new SaveMenuRequest(null, "테스트 메뉴", 1000, MenuPin.BBQ, menuMemoTitle,
                "맛있다", null, "31060661", true, null);

        //when
        ApiResponse<SaveMenuResponse> response = menuController.saveMenu(request,
                testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(response.getResponse().getMenuPin()).isEqualTo(MenuPin.BBQ);
        Assertions.assertThat(response.getResponse().getMenuMemoTitle()).isEqualTo(menuMemoTitle);
    }

    @Test
    void 메뉴를_전체_조회_할_수_있다() {
        //given
        List<Menu> preStoredMenus = menuTestData.createTestMenusWithStore(testCustomUserDetails);

        //when
        ApiResponse<List<GetSimpleMenuResponse>> response = menuController.getMenus(null, null, null, 0, 10,
                SortOrder.CREATED_AT_DESC, testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(response.getResponse().size()).isEqualTo(preStoredMenus.size());
    }

    @Test
    void 메뉴를_삭제할_수_있다() {
        //given
        Menu testMenu = menuTestData.createTestMenu(testCustomUserDetails);

        //when
        ApiResponse<Void> response = menuController.deleteMenu(testMenu.getId(), testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThatThrownBy(() -> menuController.getMenu(testMenu.getId(), testCustomUserDetails))
                .isInstanceOf(NotFoundMenuException.class);
    }
}
