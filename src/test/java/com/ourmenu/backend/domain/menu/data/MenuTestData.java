package com.ourmenu.backend.domain.menu.data;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.store.domain.Store;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class MenuTestData {

    private EntityManager entityManager;
    private StoreTestData storeTestData;

    public MenuTestData(EntityManager entityManager, StoreTestData storeTestData) {
        this.entityManager = entityManager;
        this.storeTestData = storeTestData;
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

    @Transactional
    public List<Menu> createTestMenusWithStore(CustomUserDetails customUserDetails) {
        Store store = storeTestData.createTestStore();

        Menu menu1 = Menu.builder()
                .title("테스트 메뉴1 비비큐")
                .price(1000)
                .pin(MenuPin.BBQ)
                .memoTitle("테스트 메뉴1 메모 제목")
                .memoContent("테스트 메뉴1 메모 내용")
                .store(store)
                .isCrawled(Boolean.FALSE)
                .userId(customUserDetails.getId())
                .build();

        Menu menu2 = Menu.builder()
                .title("테스트 메뉴2 빵")
                .price(2000)
                .pin(MenuPin.BREAD)
                .memoTitle("테스트 메뉴2 메모 제목")
                .memoContent("테스트 메뉴2 메모 내용")
                .store(store)
                .isCrawled(Boolean.FALSE)
                .userId(customUserDetails.getId())
                .build();

        Menu menu3 = Menu.builder()
                .title("테스트 메뉴3 케익")
                .price(3000)
                .pin(MenuPin.CAKE)
                .memoTitle("테스트 메뉴3 메모 제목")
                .memoContent("테스트 메뉴3 메모 내용")
                .store(store)
                .isCrawled(Boolean.FALSE)
                .userId(customUserDetails.getId())
                .build();

        entityManager.persist(menu1);
        entityManager.persist(menu2);
        entityManager.persist(menu3);
        return List.of(menu1, menu2, menu3);
    }

    /**
     * 메뉴판 생성 및 메뉴들과 연결합니다.
     *
     * @param customUserDetails
     * @return
     */
    @Transactional
    public MenuFolder createMenuFolderWithMenu(CustomUserDetails customUserDetails) {
        MenuFolder menuFolder = createTestMenuFolder(customUserDetails);
        List<Menu> menus = createTestMenusWithStore(customUserDetails);
        menus.stream()
                .map(menu -> MenuMenuFolder.builder()
                        .menu(menu)
                        .folderId(menuFolder.getId())
                        .build())
                .forEach(entityManager::persist);
        return menuFolder;
    }
}
