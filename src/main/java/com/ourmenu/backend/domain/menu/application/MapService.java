package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.cache.application.UrlConverterService;
import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuImgRepository;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.dto.MapSearchDto;
import com.ourmenu.backend.domain.menu.dto.MapSearchHistoryDto;
import com.ourmenu.backend.domain.menu.dto.MenuFolderInfoOnMapDto;
import com.ourmenu.backend.domain.menu.dto.MenuInfoOnMapDto;
import com.ourmenu.backend.domain.menu.dto.MenuOnMapDto;
import com.ourmenu.backend.domain.menu.exception.NotFoundMapException;
import com.ourmenu.backend.domain.menu.exception.NotFoundMenuException;
import com.ourmenu.backend.domain.search.dao.OwnedMenuSearchRepository;
import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import com.ourmenu.backend.domain.store.dao.MapRepository;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.tag.dao.MenuTagRepository;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final MenuRepository menuRepository;
    private final MapRepository mapRepository;
    private final MenuTagRepository menuTagRepository;
    private final MenuImgRepository menuImgRepository;
    private final MenuFolderRepository menuFolderRepository;
    private final OwnedMenuSearchRepository ownedMenuSearchRepository;
    private final UrlConverterService urlConverterService;

    /**
     * 유저가 보유한 메뉴들을 가져와 위치가 같은 메뉴들은 그룹핑하여 조회
     *
     * @param userId
     * @return
     */
    public List<MenuOnMapDto> findMenusOnMap(Long userId) {
        List<Menu> menus = menuRepository.findMenusByUserId(userId);

        java.util.Map<Map, List<Menu>> menuMaps = menus.stream()
                .filter(menu -> menu.getStore().getMap() != null)
                .collect(Collectors.groupingBy(menu -> menu.getStore().getMap()));

        return menuMaps.entrySet().stream()
                .map(entry -> MenuOnMapDto.from(
                        entry.getKey(), entry.getValue(), urlConverterService))
                .collect(Collectors.toList());
    }

    /**
     * 지도 화면에서의 같은 위치에 존재하는 메뉴들 조회
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
     * 검색한 이름을 포함하는 메뉴 조회
     *
     * @param title
     * @param userId
     * @return
     */
    public List<MapSearchDto> findSearchResultOnMap(String title, double mapX, double mapY, Long userId) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point userLocation = geometryFactory.createPoint(new Coordinate(mapX, mapY));

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Menu> menusByUserIdOrderByDistance =
                menuRepository.findByUserIdTitleContainingOrderByDistance(userId, title, userLocation, pageRequest);

        return menusByUserIdOrderByDistance.stream()
                .map(MapSearchDto::from)
                .toList();
    }

    /**
     * 유저의 지도 화면에서의 검색 기록을 최신순으로 조회
     *
     * @param userId
     * @return
     */
    public List<MapSearchHistoryDto> findSearchHistoryOnMap(Long userId) {
        Pageable pageable = PageRequest.of(
                0, 10, Sort.by(Sort.Direction.DESC, "modifiedAt")
        );

        List<OwnedMenuSearch> searchHistoryPage = ownedMenuSearchRepository
                .findByUserId(userId, pageable);

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
        Menu menu = menuRepository.findByIdAndUserId(menuId, userId)
                .orElseThrow(NotFoundMenuException::new);

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

        MenuFolderInfoOnMapDto menuFolderInfo = MenuFolderInfoOnMapDto.empty();

        if (!menuFolders.isEmpty()) {
            MenuFolder latestMenuFolder = menuFolders.stream()
                    .max(Comparator.comparing(MenuFolder::getCreatedAt)).get();
            menuFolderInfo = MenuFolderInfoOnMapDto.of(latestMenuFolder, menuFolders.size(), urlConverterService);
        }

        return MenuInfoOnMapDto.of(menu, menuTags, menuImgs, menuFolderInfo, urlConverterService);
    }

    /**
     * 유저의 검색 기록 저장
     *
     * @param userId
     * @param menu
     */
    private void saveOwnedMenuSearchHistory(Long userId, Menu menu) {
        OwnedMenuSearch ownedMenuSearch = OwnedMenuSearch.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .storeTitle(menu.getStore().getTitle())
                .storeAddress(menu.getStore().getAddress())
                .userId(userId)
                .build();

        ownedMenuSearchRepository.save(ownedMenuSearch);
    }
}
