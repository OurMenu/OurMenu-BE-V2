package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.response.ApiResponse;
import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MenuFolderApiTest {

    @Autowired
    MenuFolderController menuFolderController;

    @Autowired
    EntityManager entityManager;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
    }

    @Test
    void 메뉴판을_저장_할_수_있다() {

        //given
        String menuFolderTitle = "메뉴판 제목";
        SaveMenuFolderRequest saveMenuFolderRequest = new SaveMenuFolderRequest(null, menuFolderTitle,
                MenuFolderIcon.ANGRY,
                Collections.emptyList());
        CustomUserDetails customUserDetails = new CustomUserDetails(1L, "email@naver.com", "password1!");

        //when
        ApiResponse<SaveMenuFolderResponse> response = menuFolderController.saveMenuFolder(
                saveMenuFolderRequest, customUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getMenuFolderTitle()).isEqualTo(menuFolderTitle);
    }

    @Test
    @Transactional
    void 메뉴판을_조회_할_수_있다() {
        //given
        String menuFolderTitle = "메뉴판 제목";
        SaveMenuFolderRequest saveMenuFolderRequest = new SaveMenuFolderRequest(null, menuFolderTitle,
                MenuFolderIcon.ANGRY,
                Collections.emptyList());
        CustomUserDetails customUserDetails = new CustomUserDetails(1L, "email@naver.com", "password1!");
        menuFolderController.saveMenuFolder(saveMenuFolderRequest, customUserDetails);
        entityManager.flush();

        //when
        ApiResponse<List<GetMenuFolderResponse>> response = menuFolderController.getMenuFolder(customUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().size()).isEqualTo(1);
        Assertions.assertThat(response.getResponse().get(0).getMenuFolderTitle()).isEqualTo(menuFolderTitle);
    }
}
