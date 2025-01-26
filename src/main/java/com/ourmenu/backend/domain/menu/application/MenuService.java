package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderMenuResponse;
import com.ourmenu.backend.domain.menu.dto.GetMenuResponse;
import com.ourmenu.backend.domain.menu.dto.GetSimpleMenuResponse;
import com.ourmenu.backend.domain.menu.dto.MenuDto;
import com.ourmenu.backend.domain.menu.dto.MenuFilterDto;
import com.ourmenu.backend.domain.menu.dto.MenuSimpleDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.menu.exception.ForbiddenMenuException;
import com.ourmenu.backend.domain.menu.exception.NotFoundMenuException;
import com.ourmenu.backend.domain.store.application.StoreService;
import com.ourmenu.backend.domain.store.domain.Store;
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
    private final MenuMenuFolderService menuMenuFolderService;
    private final MenuTagService menuTagService;
    private final StoreService storeService;
    private final MenuImgService menuImgService;
    private final MenuFolderService menuFolderService;

    /**
     * 메뉴 저장(메뉴 사진, 메뉴판, 태그 의존 엔티티 생성
     *
     * @param menuDto
     * @return
     */
    @Transactional
    public SaveMenuResponse saveMenu(MenuDto menuDto) {

        Store store = storeService.saveStoreAndMap(menuDto.getStoreTitle(), menuDto.getStoreAddress(),
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
                .store(store)
                .build();
        Menu saveMenu = menuRepository.save(menu);

        //메뉴판 연관관계 생성
        List<MenuMenuFolder> saveMenuMenuFolders = menuMenuFolderService.saveMenuMenuFolders(menuDto.getMenuFolderIds(),
                menuDto.getUserId(),
                saveMenu);

        //태그 연관관계 생성
        List<Tag> saveTag = saveTags(menuDto.getTags(), saveMenu.getId());

        //s3 업로드및 이미지 연관관계 생성
        List<MenuImg> menuImgs = menuImgService.saveMenuImgs(saveMenu.getId(), menuDto.getMenuImgs());
        return SaveMenuResponse.of(saveMenu, store, store.getMap(), menuImgs, saveMenuMenuFolders, saveTag);
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
        menuRepository.delete(menu);
        menuMenuFolderService.deleteMenuMenuFolders(menu);
        menuImgService.deleteMenuImgs(menuId);
        menuTagService.deleteMenuTag(menuId);
        storeService.deleteStore(menu.getStore());
    }

    /**
     * 유저 메뉴판 메뉴 조회
     *
     * @param userId
     * @param menuFolderId
     * @return
     */
    @Transactional
    public List<GetMenuFolderMenuResponse> findMenusByMenuFolder(Long userId, Long menuFolderId,
                                                                 MenuFilterDto menuFilterDto) {
        Long tagSize = (long) menuFilterDto.getTags().size();
        List<String> tagStrings = menuFilterDto.getTags().stream()
                .map(Tag::toString)
                .toList();
        List<MenuSimpleDto> findMenuSimpleDto = menuRepository.findByTagNameAndPriceRange(userId, menuFolderId,
                tagStrings, tagSize, menuFilterDto.getMinPrice(), menuFilterDto.getMaxPrice());
        return findMenuSimpleDto.stream()
                .map(menuSimpleDto -> {
                    String imgUrl = menuImgService.findUniqueImg(menuSimpleDto.getMenuId());
                    return GetMenuFolderMenuResponse.of(menuSimpleDto, imgUrl);
                })
                .toList();
    }

    @Transactional
    public List<GetSimpleMenuResponse> findMenusByPageAndSort(Long userId, MenuFilterDto menuFilterDto) {
        return menuRepository.findByUserId(userId, menuFilterDto.getPageable()).stream()
                .map(menu -> {
                    String imgUrl = menuImgService.findUniqueImg(menu.getId());
                    return GetSimpleMenuResponse.of(menu, imgUrl);
                })
                .toList();
    }

    /**
     * 메뉴판 상세 정보 조회
     *
     * @param userId
     * @param menuId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMenuResponse findMenu(Long userId, Long menuId) {
        Menu menu = menuRepository.findByIdWithStore(userId, menuId)
                .orElseThrow(NotFoundMenuException::new);
        List<String> imgUrls = menuImgService.findImgUrls(menuId);
        List<String> tagNames = menuTagService.findTagNames(menuId);
        List<MenuFolder> menuFolders = menuFolderService.findAllByMenuId(menuId);
        return GetMenuResponse.of(menu, imgUrls, tagNames, menuFolders);
    }

    private List<Tag> saveTags(List<Tag> tags, Long menuId) {
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
}
