package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuMenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.menu.dto.SaveMenuRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.store.application.StoreService;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.tag.application.MenuTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMenuFolderRepository menuMenuFolderRepository;
    private final MenuFolderService menuFolderService;
    private final MenuTagService menuTagService;
    private final StoreService storeService;

    @Transactional
    public SaveMenuResponse saveMenu(Long userId, SaveMenuRequest request) {
        Menu menu = Menu.builder()
                .title(request.getMenuTitle())
                .price(request.getMenuPrice())
                .pin(request.getMenuPin())
                .memoTitle(request.getMenuMemoTitle())
                .memoContent(request.getMenuMemoContent())
                .isCrawled(request.isCrawled())
                .build();
        Menu saveMenu = menuRepository.save(menu);

        //메뉴판 연관관계 생성
        request.getMenuFolderIds().forEach(
                menuFolderId -> {
                    saveMenuMenuFolder(userId, menuFolderId, saveMenu);
                });

        //태그 연관관계 생성
        request.getTags().forEach(
                tag -> {
                    menuTagService.saveTag(saveMenu.getId(), tag);
                }
        );
        Map map = storeService.saveStoreAndMap(request.getStoreTitle(), request.getStoreAddress(),
                request.getMapX(),
                request.getMapY());
        return SaveMenuResponse.of(saveMenu, map.getStore(), map);
    }

    /**
     * 메뉴 - 메뉴판 연관관계 설정
     *
     * @param userId
     * @param menuFolderId
     * @param menu
     * @return 저장된 메뉴-메뉴판 조인테이블 엔티티
     */
    private MenuMenuFolder saveMenuMenuFolder(Long userId, Long menuFolderId, Menu menu) {
        menuFolderService.validateExistMenuFolder(userId, menuFolderId);
        MenuMenuFolder menuMenuFolder = MenuMenuFolder.builder()
                .menu(menu)
                .folderId(menuFolderId)
                .build();
        return menuMenuFolderRepository.save(menuMenuFolder);
    }


}
