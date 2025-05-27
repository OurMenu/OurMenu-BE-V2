package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetMenuResponse {

    private Long menuId;
    private String menuTitle;
    private int menuPrice;
    private String menuPinImgUrl;
    private String storeTitle;
    private String storeAddress;
    private List<String> tagImgUrls;
    private List<String> menuImgUrls;

    private List<SimpleMenuFolder> menuFolders;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class SimpleMenuFolder {
        private Long menuFolderId;
        private String menuFolderTitle;
        private String menuFolderIconImgUrl;
    }

    public static GetMenuResponse of(Menu menu, List<String> imgUrls, List<Tag> tags,
                                     List<MenuFolder> menuFolders, UrlConverter urlConverter) {
        String menuPinImgUrl = urlConverter.getMenuPinMapUrl(menu.getPin());

        List<SimpleMenuFolder> simpleMenuFolders = menuFolders.stream()
                .map(menuFolder -> {
                    String menuFolderIconImgUrl = urlConverter.getMenuFolderImgUrl(menuFolder.getIcon());
                    return SimpleMenuFolder.builder()
                            .menuFolderId(menuFolder.getId())
                            .menuFolderTitle(menuFolder.getTitle())
                            .menuFolderIconImgUrl(menuFolderIconImgUrl)
                            .build();
                })
                .toList();
        List<String> tagImgUrls = tags.stream()
                .map(urlConverter::getOrangeTagImgUrl)
                .toList();

        return GetMenuResponse.builder().
                menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuPinImgUrl(menuPinImgUrl)
                .storeAddress(menu.getStore().getAddress())
                .storeTitle(menu.getStore().getTitle())
                .tagImgUrls(tagImgUrls)
                .menuImgUrls(imgUrls)
                .menuFolders(simpleMenuFolders)
                .build();
    }
}


