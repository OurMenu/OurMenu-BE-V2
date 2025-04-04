package com.ourmenu.backend.domain.menu.data;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
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
        entityManager.flush();
        return menuFolder;
    }

    @Transactional
    public List<MenuFolder> createTestMenuFolderList(CustomUserDetails customUserDetails) {
        List<MenuFolder> menuFolderList = new ArrayList<>();
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
        return menuFolderList;
    }
}
