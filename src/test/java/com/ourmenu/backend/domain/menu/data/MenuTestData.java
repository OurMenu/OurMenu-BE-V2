package com.ourmenu.backend.domain.menu.data;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import jakarta.persistence.EntityManager;
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
                .imgUrl("테스트 메뉴 폴더 이미지 url")
                .icon(MenuFolderIcon.ANGRY)
                .index(1)
                .userId(customUserDetails.getId())
                .build();
        entityManager.persist(menuFolder);
        entityManager.flush();
        return menuFolder;
    }
}
