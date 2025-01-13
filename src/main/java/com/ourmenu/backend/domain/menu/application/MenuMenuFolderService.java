package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.application.validator.MenuFolderValidator;
import com.ourmenu.backend.domain.menu.dao.MenuMenuFolderRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuMenuFolderService {

    private final MenuMenuFolderRepository menuMenuFolderRepository;
    private final MenuFolderValidator menuFolderValidator;

    /**
     * 메뉴 - 메뉴판 연관관계 설정(벌크) 내부적으로 saveMenuMenuFolder 호출
     *
     * @param menuFolderIds
     * @param userId
     * @param menu
     * @return
     */
    public List<MenuMenuFolder> saveMenuMenuFolders(List<Long> menuFolderIds, Long userId, Menu menu) {
        return menuFolderIds.stream().map(
                menuFolderId -> saveMenuMenuFolder(userId, menuFolderId, menu)
        ).toList();
    }

    @Transactional
    public void deleteMenuMenuFolders(Menu menu) {
        menuMenuFolderRepository.deleteAllByMenu(menu);
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
}
