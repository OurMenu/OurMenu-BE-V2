package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.application.UrlConverterService;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.store.domain.Store;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SaveMenuResponse {

    private Long menuId;
    private String menuTitle;
    private int menuPrice;
    private String menuMemoTitle;
    private String menuMemoContent;
    private String menuPinImgUrl;
    private List<Long> menuFolderIds;
    private boolean isCrawled;
    private List<String> tagImgUrls;
    private String storeTitle;
    private String storeAddress;
    private Double storeMapX;
    private Double storeMapY;
    private List<String> menuImgUrls;

    public static SaveMenuResponse of(Menu menu, Store store, Map map, List<MenuImg> menuImgs,
                                      List<MenuMenuFolder> menuMenuFolders, List<Tag> tags,
                                      UrlConverterService urlConverterService) {
        String menuPinImgUrl = urlConverterService.getMenuPinAddUrl(menu.getPin());
        List<Long> menuFolderIds = menuMenuFolders.stream()
                .map(MenuMenuFolder::getFolderId)
                .toList();
        List<String> tagImgUrls = tags.stream()
                .map(urlConverterService::getOrangeTagImgUrl)
                .toList();
        List<String> menuImgUrls = menuImgs.stream()
                .map(MenuImg::getImgUrl)
                .toList();
        return SaveMenuResponse.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuMemoTitle(menu.getMemoTitle())
                .menuMemoContent(menu.getMemoContent())
                .menuPinImgUrl(menuPinImgUrl)
                .menuFolderIds(menuFolderIds)
                .isCrawled(menu.getIsCrawled())
                .tagImgUrls(tagImgUrls)
                .storeTitle(store.getTitle())
                .storeAddress(store.getAddress())
                .storeMapX(map.getLocation().getX())
                .storeMapY(map.getLocation().getY())
                .menuImgUrls(menuImgUrls)
                .build();
    }
}
