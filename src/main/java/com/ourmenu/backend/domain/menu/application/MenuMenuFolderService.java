package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.application.validator.MenuFolderValidator;
import com.ourmenu.backend.domain.menu.application.validator.MenuValidator;
import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuMenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuMenuFolderService {

    private final MenuMenuFolderRepository menuMenuFolderRepository;
    private final MenuRepository menuRepository;
    private final MenuFolderRepository menuFolderRepository;
    private final MenuFolderValidator menuFolderValidator;
    private final MenuValidator menuValidator;

    /**
     * 메뉴 - 메뉴판 연관관계 설정(벌크) 내부적으로 saveMenuMenuFolder 호출
     *
     * @param menuFolderIds
     * @param userId
     * @param menu
     * @return
     */
    @Transactional
    public List<MenuMenuFolder> saveMenuMenuFolders(List<Long> menuFolderIds, Long userId, Menu menu) {
        return menuFolderIds.stream().map(
                menuFolderId -> saveMenuMenuFolder(userId, menuFolderId, menu)
        ).toList();
    }

    /**
     * 메뉴 - 메뉴판 연관관계 설정(벌크) 내부적으로 saveMenuMenuFolder 호출
     *
     * @param menuIds
     * @param userId
     * @param menuFolder
     * @return
     */
    @Transactional
    public List<MenuMenuFolder> saveMenuMenuFolders(List<Long> menuIds, Long userId, MenuFolder menuFolder) {
        return menuIds.stream().map(
                menuId -> saveMenuMenuFolder(userId, menuId, menuFolder)
        ).toList();
    }

    @Transactional
    public void deleteMenuMenuFolders(Menu menu) {
        menuMenuFolderRepository.deleteAllByMenu(menu);
    }

    /**
     * 연관관계를 재구성 (기존 삭제)
     *
     * @param userId
     * @param menuFolderId
     * @param menuIds      변경될 메뉴(size=0 이면 기존 모두 삭제)
     */
    @Transactional
    public List<MenuMenuFolder> updateMenuMenuFolder(Long userId, Long menuFolderId, List<Long> menuIds) {
        menuIds.forEach(menuId -> menuValidator.validateExistMenu(userId, menuId));
        menuMenuFolderRepository.deleteByFolderId(menuFolderId);
        MenuFolder menuFolder = menuFolderRepository.findById(menuFolderId).get();
        return saveMenuMenuFolders(menuIds, userId, menuFolder);
    }

    /**
     * 메뉴판에 해당하는 조인 컬럼 반환
     *
     * @param menuFolderId
     * @return
     */
    @Transactional
    public List<MenuMenuFolder> findAllByMenuFolderId(Long menuFolderId) {
        return menuMenuFolderRepository.findAllByFolderId(menuFolderId);
    }

    /**
     * 메뉴에 해당하는 조인 컬럼 반환
     *
     * @param menu
     * @return
     */
    @Transactional
    public List<MenuMenuFolder> findALlByMenuId(Menu menu) {
        return menuMenuFolderRepository.findAllByMenu(menu);
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
        menuFolderValidator.validateExistMenuFolder(userId, menuFolderId);
        MenuMenuFolder menuMenuFolder = MenuMenuFolder.builder()
                .menu(menu)
                .folderId(menuFolderId)
                .build();
        return menuMenuFolderRepository.save(menuMenuFolder);
    }

    /**
     * 메뉴 - 메뉴판 연관관계 설정
     *
     * @param userId
     * @param menuId
     * @param menuFolder
     * @return 저장된 메뉴-메뉴판 조인테이블 엔티티
     */
    private MenuMenuFolder saveMenuMenuFolder(Long userId, Long menuId, MenuFolder menuFolder) {
        menuValidator.validateExistMenu(userId, menuId);
        Menu menu = menuRepository.findById(menuId).get();

        MenuMenuFolder menuMenuFolder = MenuMenuFolder.builder()
                .menu(menu)
                .folderId(menuFolder.getId())
                .build();
        return menuMenuFolderRepository.save(menuMenuFolder);
    }
}
