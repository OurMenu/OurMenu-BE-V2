package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuMenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.menu.dto.MenuDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.menu.exception.ForbiddenMenuException;
import com.ourmenu.backend.domain.menu.exception.NotFoundMenuException;
import com.ourmenu.backend.domain.store.application.StoreService;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.tag.application.MenuTagService;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
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
    private final MenuImgService menuImgService;

    /**
     * 메뉴 저장(메뉴 사진, 메뉴판, 태그 의존 엔티티 생성
     *
     * @param menuDto
     * @return
     */
    @Transactional
    public SaveMenuResponse saveMenu(MenuDto menuDto) {

        Map map = storeService.saveStoreAndMap(menuDto.getStoreTitle(), menuDto.getStoreAddress(),
                menuDto.getMapX(),
                menuDto.getMapY());

        Menu menu = Menu.builder()
                .title(menuDto.getMenuTitle())
                .price(menuDto.getMenuPrice())
                .pin(menuDto.getMenuPin())
                .memoTitle(menuDto.getMenuMemoTitle())
                .memoContent(menuDto.getMenuMemoContent())
                .isCrawled(menuDto.isCrawled())
                .userId(menuDto.getUserId())
                .store(map.getStore())
                .build();
        Menu saveMenu = menuRepository.save(menu);

        //메뉴판 연관관계 생성
        List<MenuMenuFolder> saveMenuMenuFolders = saveMenuMenuFolders(menuDto.getMenuFolderIds(), menuDto.getUserId(),
                saveMenu);

        //태그 연관관계 생성
        List<Tag> saveTag = saveTags(menuDto.getTags(), saveMenu.getId());

        //s3 업로드및 이미지 연관관계 생성
        List<MenuImg> menuImgs = menuImgService.saveMenuImgs(saveMenu.getId(), menuDto.getMenuImgs());
        return SaveMenuResponse.of(saveMenu, map.getStore(), map, menuImgs, saveMenuMenuFolders, saveTag);
    }

    /**
     * 메뉴 삭제 및 연관관계 엔티티 모두 삭제
     *
     * @param userId
     * @param menuId
     */
    @Transactional
    public void deleteMenu(Long userId, Long menuId) {
        Menu menu = findOne(userId, menuId);
        deleteMenuMenuFolders(menu);
        menuImgService.deleteMenuImgs(menuId);
        menuRepository.delete(menu);
        menuTagService.deleteMenuTag(menuId);
    }

    /**
     * 메뉴 - 메뉴판 연관관계 설정(벌크) 내부적으로 saveMenuMenuFolder 호출
     *
     * @param menuFolderIds
     * @param userId
     * @param menu
     * @return
     */
    private List<MenuMenuFolder> saveMenuMenuFolders(List<Long> menuFolderIds, Long userId, Menu menu) {
        return menuFolderIds.stream().map(
                menuFolderId -> saveMenuMenuFolder(userId, menuFolderId, menu)
        ).toList();
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

    private List<Tag> saveTags(List<String> tags, Long menuId) {
        return tags.stream().map(
                tag -> menuTagService.saveTag(menuId, tag)
        ).toList();
    }

    private Menu findOne(Long userId, Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        if (!menu.getUserId().equals(userId)) {
            throw new ForbiddenMenuException();
        }
        return menu;
    }

    private void deleteMenuMenuFolders(Menu menu) {
        menuMenuFolderRepository.deleteAllByMenu(menu);
    }
}
