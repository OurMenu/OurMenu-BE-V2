package com.ourmenu.backend.domain.menu.data;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class MenuTestData {

    private EntityManager entityManager;

    public MenuTestData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public MenuFolder createTestMenuFolder(CustomUserDetails customUserDetails) {
        MenuFolder menuFolder = MenuFolder.builder()
                .title("테스트 메뉴 폴더")
                .imgUrl(null)
                .icon(MenuFolderIcon.ANGRY)
                .index(1)
                .userId(customUserDetails.getId())
                .build();
        entityManager.persist(menuFolder);
        return menuFolder;
    }

    @Transactional
    public List<MenuFolder> createTestMenuFolderList(CustomUserDetails customUserDetails) {
        MenuFolder menuFolder1 = MenuFolder.builder()
                .title("테스트 메뉴 폴더1")
                .imgUrl(null)
                .icon(MenuFolderIcon.ANGRY)
                .index(1)
                .userId(customUserDetails.getId())
                .build();
        MenuFolder menuFolder2 = MenuFolder.builder()
                .title("테스트 메뉴 폴더2")
                .imgUrl(null)
                .icon(MenuFolderIcon.BAEKSUK)
                .index(2)
                .userId(customUserDetails.getId())
                .build();
        MenuFolder menuFolder3 = MenuFolder.builder()
                .title("테스트 메뉴 폴더3")
                .imgUrl(null)
                .icon(MenuFolderIcon.BASKET)
                .index(3)
                .userId(customUserDetails.getId())
                .build();
        entityManager.persist(menuFolder1);
        entityManager.persist(menuFolder2);
        entityManager.persist(menuFolder3);
        return List.of(menuFolder1, menuFolder2, menuFolder3);
    }

    @Transactional
    public Menu createTestMenu(CustomUserDetails customUserDetails) {
        Menu menu = Menu.builder()
                .title("테스트 메뉴 비비큐")
                .price(1000)
                .pin(MenuPin.BBQ)
                .memoTitle("테스트 메뉴 메모 제목")
                .memoContent("테스트 메뉴 메모 내용")
                .isCrawled(Boolean.FALSE)
                .userId(customUserDetails.getId())
                .build();

        entityManager.persist(menu);
        return menu;
    }
}
