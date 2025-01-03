package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.store.domain.Store;
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
    private int menuPin;
    //private List<Integer> menuFolderIds;
    private boolean isCrawled;
    //private List<String> tags;
    private String storeTitle;
    private String storeAddress;
    private Double mapX;
    private Double mapY;
    private List<String> menuImgUrls;

    public static SaveMenuResponse of(Menu menu, Store store, Map map, List<MenuImg> menuImgs) {
        List<String> menuImgUrls = menuImgs.stream().map(MenuImg::getImgUrl).toList();
        return SaveMenuResponse.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuMemoTitle(menu.getMemoTitle())
                .menuMemoContent(menu.getMemoContent())
                .menuPin(menu.getPin())
                .isCrawled(menu.getIsCrawled())
                .storeTitle(store.getTitle())
                .storeAddress(store.getAddress())
                .mapX(map.getMapX())
                .mapY(map.getMapY())
                .menuImgUrls(menuImgUrls)
                .build();
    }
}
