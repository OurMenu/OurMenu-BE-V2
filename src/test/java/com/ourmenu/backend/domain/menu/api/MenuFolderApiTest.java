package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.menu.config.MenuTestConfig;
import com.ourmenu.backend.domain.menu.data.MenuTestData;
import com.ourmenu.backend.domain.menu.data.UserTestData;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderIndexRequest;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.response.ApiResponse;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({TestConfig.class, MenuTestConfig.class})
@DisplayName("메뉴 폴더 통합 테스트")
public class MenuFolderApiTest {

    @Autowired
    MenuFolderController menuFolderController;

    @Autowired
    UserTestData userTestData;

    @Autowired
    MenuTestData menuTestData;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
    }

    @Test
    void 테스트_유저를_생성_할_수_있다() {
        //given

        //when
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();

        //then
        Assertions.assertThat(testCustomUserDetails.getId()).isEqualTo(1L);
    }

    @Test
    void 메뉴판을_저장_할_수_있다() {
        //given
        String menuFolderTitle = "메뉴판 제목";
        SaveMenuFolderRequest saveMenuFolderRequest = new SaveMenuFolderRequest(null, menuFolderTitle,
                MenuFolderIcon.ANGRY,
                Collections.emptyList());
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();

        //when
        ApiResponse<SaveMenuFolderResponse> response = menuFolderController.saveMenuFolder(
                saveMenuFolderRequest, testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getMenuFolderTitle()).isEqualTo(menuFolderTitle);
    }

    @Test
    void 메뉴판_저장시_시간순으로_순서가_정해진다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        MenuFolder testMenuFolder = menuTestData.createTestMenuFolder(testCustomUserDetails);

        String menuFolderTitle = "새로운 메뉴판 제목";
        SaveMenuFolderRequest saveMenuFolderRequest = new SaveMenuFolderRequest(null, menuFolderTitle,
                MenuFolderIcon.ANGRY,
                Collections.emptyList());
        ApiResponse<SaveMenuFolderResponse> saveMenuFolderResponseApiResponse = menuFolderController.saveMenuFolder(
                saveMenuFolderRequest, testCustomUserDetails);

        //when
        ApiResponse<GetMenuFolderResponse> response = menuFolderController.getMenuFolder(testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getMenuFolders().get(0).getIndex())
                .isEqualTo(saveMenuFolderResponseApiResponse.getResponse().getIndex());
        Assertions.assertThat(response.getResponse().getMenuFolders().get(1).getIndex())
                .isEqualTo(testMenuFolder.getIndex());
    }

    @Test
    void 메뉴판들을_조회_할_수_있다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        MenuFolder testMenuFolder = menuTestData.createTestMenuFolder(testCustomUserDetails);

        //when
        ApiResponse<GetMenuFolderResponse> response = menuFolderController.getMenuFolder(testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getMenuFolders().size()).isEqualTo(1);
        Assertions.assertThat(response.getResponse().getMenuFolders().get(0).getMenuFolderTitle())
                .isEqualTo(testMenuFolder.getTitle());
    }

    @Test
    void 메뉴판들을_조회시_유저_메뉴_전체_개수_를_확인_할_수_있다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        MenuFolder testMenuFolder = menuTestData.createTestMenuFolder(testCustomUserDetails);
        menuTestData.createTestMenu(testCustomUserDetails);

        //when
        ApiResponse<GetMenuFolderResponse> response = menuFolderController.getMenuFolder(testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getMenuFolders().size()).isEqualTo(1);
        Assertions.assertThat(response.getResponse().getMenuCount()).isEqualTo(1);
        Assertions.assertThat(response.getResponse().getMenuFolders().get(0).getMenuFolderTitle())
                .isEqualTo(testMenuFolder.getTitle());
    }

    @Test
    void 메뉴_폴더를_변경_할_수_있다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        MenuFolder testMenuFolder = menuTestData.createTestMenuFolder(testCustomUserDetails);

        String modifiedMenuFolderName = "수정된 메뉴폴더 이름";
        UpdateMenuFolderRequest updateMenuFolderRequest = new UpdateMenuFolderRequest(null, false,
                modifiedMenuFolderName, null, null);

        //when
        ApiResponse<UpdateMenuFolderResponse> updateMenuFolderResponseApiResponse = menuFolderController.updateMenuFolder(
                testMenuFolder.getId(), updateMenuFolderRequest, testCustomUserDetails);

        //then
        Assertions.assertThat(updateMenuFolderResponseApiResponse.isSuccess()).isEqualTo(true);
        Assertions.assertThat(updateMenuFolderResponseApiResponse.getResponse().getMenuFolderTitle())
                .isEqualTo(modifiedMenuFolderName);
        Assertions.assertThat(updateMenuFolderResponseApiResponse.getResponse().getMenuFolderIcon())
                .isEqualTo(testMenuFolder.getIcon());

    }

    @Test
    void 메뉴_폴더_순서를_변경_할_수_있다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        menuTestData.createTestMenuFolders(testCustomUserDetails);

        //when
        ApiResponse<GetMenuFolderResponse> preResponse = menuFolderController.getMenuFolder(
                testCustomUserDetails);
        UpdateMenuFolderIndexRequest updateMenuFolderIndexRequest = new UpdateMenuFolderIndexRequest(3);
        ApiResponse<UpdateMenuFolderResponse> updateMenuFolderResponseApiResponse = menuFolderController.updateMenuFolderIndex(
                1L, updateMenuFolderIndexRequest, testCustomUserDetails);
        ApiResponse<GetMenuFolderResponse> response = menuFolderController.getMenuFolder(testCustomUserDetails);

        //then
        Assertions.assertThat(updateMenuFolderResponseApiResponse.isSuccess()).isEqualTo(true);
        Assertions.assertThat(preResponse.getResponse().getMenuFolders().get(0).getMenuFolderTitle())
                .isEqualTo(response.getResponse().getMenuFolders().get(1).getMenuFolderTitle());
        Assertions.assertThat(preResponse.getResponse().getMenuFolders().get(1).getMenuFolderTitle())
                .isEqualTo(response.getResponse().getMenuFolders().get(2).getMenuFolderTitle());
        Assertions.assertThat(preResponse.getResponse().getMenuFolders().get(2).getMenuFolderTitle())
                .isEqualTo(response.getResponse().getMenuFolders().get(0).getMenuFolderTitle());
    }

    @Test
    void 메뉴_폴더를_삭제_할_수_있다() {
        //given
        CustomUserDetails testCustomUserDetails = userTestData.createTestEmailUser();
        MenuFolder testMenuFolder = menuTestData.createTestMenuFolder(testCustomUserDetails);

        //when
        menuFolderController.deleteMenuFolder(testMenuFolder.getId(), testCustomUserDetails);
        ApiResponse<GetMenuFolderResponse> response = menuFolderController.getMenuFolder(testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getMenuFolders().size()).isEqualTo(0);
    }
}
