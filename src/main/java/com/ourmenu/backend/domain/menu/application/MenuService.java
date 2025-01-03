package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuMenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.menu.dto.MenuDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.store.application.StoreService;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.tag.application.MenuTagService;
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
    private final AwsS3Service awsS3Service;

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

        menuDto.getMenuFolderIds().forEach(
                menuFolderId -> {
                    saveMenuMenuFolder(menuDto.getUserId(), menuFolderId, saveMenu);
                }
        );

        //태그 연관관계 생성
        menuDto.getTags().forEach(
                tag -> {
                    menuTagService.saveTag(saveMenu.getId(), tag);
                }
        );

        List<String> menuImgUrls = awsS3Service.uploadFilesAsync(menuDto.getMenuImgs());
        List<MenuImg> menuImgs = menuImgService.saveMenuImgs(saveMenu.getId(), menuImgUrls);
        return SaveMenuResponse.of(saveMenu, map.getStore(), map, menuImgs);
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
