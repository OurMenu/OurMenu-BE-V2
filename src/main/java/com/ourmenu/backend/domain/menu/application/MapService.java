package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuImgRepository;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.dto.MapSearchDto;
import com.ourmenu.backend.domain.menu.dto.MenuFolderInfoOnMapDto;
import com.ourmenu.backend.domain.menu.dto.MenuInfoOnMapDto;
import com.ourmenu.backend.domain.menu.dto.MenuOnMapDto;
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

import java.time.LocalDateTime;
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
     * @param userId
     * @return
     */
    public List<MenuOnMapDto> findMenusOnMap(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Menu> menus = menuRepository.findMenusByUserId(userId);
        
        java.util.Map<Map, List<Menu>> menuMaps = menus.stream()
                .filter(menu -> menu.getStore().getMap() != null)
                .collect(Collectors.groupingBy(menu -> menu.getStore().getMap()));

        List<MenuOnMapDto> response = menuMaps.entrySet().stream()
                .map(entry -> MenuOnMapDto.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return response;
    }

    public List<MenuInfoOnMapDto> findMenuOnMap(Long mapId, Long userId) {
        Map map = mapRepository.findMapById(mapId)
                .orElseThrow(RuntimeException::new);    //예외처리 수정 필요

        List<Store> stores = map.getStores();
        List<Menu> menus = new ArrayList<>();
        List<MenuInfoOnMapDto> response = new ArrayList<>();

        for (Store store : stores) {
            menus = menuRepository.findMenusByStoreIdAndUserId(store.getId(), userId);
        }

        for (Menu menu : menus) {
            response.add(getMenuInfo(menu));
        }

        return response;
    }

    public List<MapSearchDto> findSearchResultOnMap(String title, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Menu> menus = menuRepository.findMenusByUserId(userId);
        List<MapSearchDto> response = menus.stream()
                .filter(m -> m.getStore().getTitle().contains(title)
                        || m.getTitle().contains(title))
                .map(MapSearchDto::from)
                .toList();

        OwnedMenuSearch ownedMenuSearch = OwnedMenuSearch.builder()
                .menuTitle(title)
                .userId(userId)
                .searchAt(LocalDateTime.now())
                .build();

        ownedMenuSearchRepository.save(ownedMenuSearch);

        return response;
    }

    public List<MapSearchDto> findSearchHistoryOnMap(Long userId) {
        Pageable pageable = PageRequest.of(0, 10);

        Page<OwnedMenuSearch> searchHistoryPage = ownedMenuSearchRepository
                .findByUserIdOrderBySearchAtDesc(userId, pageable);

        List<MapSearchDto> response = searchHistoryPage.stream()
                .map(MapSearchDto::from)
                .collect(Collectors.toList());

        return response;
    }

    public MenuInfoOnMapDto findMenuByMenuIdOnMap(Long menuId, Long userId) {
        Menu menu = menuRepository.findByIdAndUserId(menuId, userId);

        MenuInfoOnMapDto response = getInfo(menu);
        return response;
    }


    public List<MenuInfoOnMapDto> findMenuByStoreIdOnMap(Long storeId, Long userId) {
        List<Menu> menus = menuRepository.findByUserIdAndStoreId(userId, storeId);
        List<MenuInfoOnMapDto> response = new ArrayList<>();

        for (Menu menu : menus) {
            response.add(getMenuInfo(menu));
        }

        return response;
    }

    private MenuInfoOnMapDto getMenuInfo(Menu menu) {
        List<MenuTag> menuTags = menuTagRepository.findMenuTagsByMenuId(menu.getId());
        List<MenuImg> menuImgs = menuImgRepository.findAllByMenuId(menu.getId());
        List<MenuFolder> menuFolders = menuFolderRepository.findMenuFoldersByMenuId(menu.getId());

        MenuFolder latestMenuFolder = menuFolders.stream()
                .max(Comparator.comparing(MenuFolder::getCreatedAt)) // createdAt으로 정렬
                .orElseThrow(RuntimeException::new);        //예외처리 수정 필요

        MenuFolderInfoOnMapDto menuFolderInfo = MenuFolderInfoOnMapDto.of(latestMenuFolder, menuFolders.size());

        MenuInfoOnMapDto response = MenuInfoOnMapDto.of(menu, menuTags, menuImgs, menuFolderInfo);
        return response;
    }
}
