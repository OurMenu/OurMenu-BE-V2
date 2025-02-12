package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuImgRepository;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.dto.*;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.search.dao.OwnedMenuSearchRepository;
import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import com.ourmenu.backend.domain.store.dao.MapRepository;
import com.ourmenu.backend.domain.store.dao.StoreRepository;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.store.domain.Store;
import com.ourmenu.backend.domain.tag.dao.MenuTagRepository;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import com.ourmenu.backend.domain.user.dao.UserRepository;
import com.ourmenu.backend.domain.user.domain.User;
import com.ourmenu.backend.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MapRepository mapRepository;
    private final MenuTagRepository menuTagRepository;
    private final MenuImgRepository menuImgRepository;
    private final MenuFolderRepository menuFolderRepository;
    private final OwnedMenuSearchRepository ownedMenuSearchRepository;

    /**
     * 유저의 Menu를 가져와 mapId가 같은 Menu들을 Map 형식으로 그룹핑 후 response 반환
     *
     * @param userId
     * @return
     */
    public List<MenuOnMapDto> findMenusOnMap(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Menu> menus = menuRepository.findMenusByUserId(userId);
        
        java.util.Map<Map, List<Menu>> menuMaps = menus.stream()
                .filter(menu -> menu.getStore().getMap() != null)
                .collect(Collectors.groupingBy(menu -> menu.getStore().getMap()));

        return menuMaps.entrySet().stream()
                .map(entry -> MenuOnMapDto.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param mapId
     * @param userId
     * @return
     */
    public List<MenuInfoOnMapDto> findMenuOnMap(Long mapId, Long userId) {
        Map map = mapRepository.findMapById(mapId)
                .orElseThrow(NotFoundMapException::new);

        return map.getStores().stream()
                .flatMap(store -> menuRepository.findMenusByStoreIdAndUserId(store.getId(), userId).stream())
                .map(this::getMenuInfo)
                .collect(Collectors.toList());
    }

    /**
     * 검색한 이름을 포함하는 메뉴들을 반환
     *
     * @param title
     * @param userId
     * @return
     */

    public List<MapSearchDto> findSearchResultOnMap(String title, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Menu> menus = menuRepository.findMenusByUserId(userId);

        return menus.stream()
                .filter(m -> m.getStore().getTitle().contains(title)
                        || m.getTitle().contains(title))
                .map(MapSearchDto::from)
                .toList();
    }

    /**
     * 유저의 지도 화면에서의 검색 기록을 최신순으로 반환
     *
     * @param userId
     * @return
     */

    public List<MapSearchHistoryDto> findSearchHistoryOnMap(Long userId) {
        Pageable pageable = PageRequest.of(0, 10);

        Page<OwnedMenuSearch> searchHistoryPage = ownedMenuSearchRepository
                .findByUserIdOrderByModifiedAtDesc(userId, pageable);

        return searchHistoryPage.stream()
                .map(MapSearchHistoryDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 유저가 가지고 있는 메뉴를 지도 화면에서 상세 조회
     *
     * @param menuId
     * @param userId
     * @return
     */

    @Transactional
    public MenuInfoOnMapDto findMenuByMenuIdOnMap(Long menuId, Long userId) {
        Menu menu = menuRepository.findByIdAndUserId(menuId, userId);
        saveOwnedMenuSearchHistory(userId, menu);
        return getMenuInfo(menu);
    }

    /**
     * 조회하고자 하는 메뉴 정보 불러오기
     *
     * @param menu
     * @return
     */

    private MenuInfoOnMapDto getMenuInfo(Menu menu) {
        List<MenuTag> menuTags = menuTagRepository.findMenuTagsByMenuId(menu.getId());
        List<MenuImg> menuImgs = menuImgRepository.findAllByMenuId(menu.getId());
        List<MenuFolder> menuFolders = menuFolderRepository.findMenuFoldersByMenuId(menu.getId());

        MenuFolder latestMenuFolder = menuFolders.stream()
                .max(Comparator.comparing(MenuFolder::getCreatedAt)) // createdAt으로 정렬
                .orElseThrow(RuntimeException::new);        //예외처리 수정 필요

        MenuFolderInfoOnMapDto menuFolderInfo = MenuFolderInfoOnMapDto.of(latestMenuFolder, menuFolders.size());

        return MenuInfoOnMapDto.of(menu, menuTags, menuImgs, menuFolderInfo);
    }

    /**
     * 유저의 검색 기록 저장
     *
     * @param userId
     * @param menu
     */
    private void saveOwnedMenuSearchHistory(Long userId, Menu menu) {
        OwnedMenuSearch ownedMenuSearch = OwnedMenuSearch.builder()
                .menuTitle(menu.getTitle())
                .storeTitle(menu.getStore().getTitle())
                .storeAddress(menu.getStore().getAddress())
                .userId(userId)
                .build();

        ownedMenuSearchRepository.save(ownedMenuSearch);
    }
}
