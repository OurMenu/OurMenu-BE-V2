package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuInfoOnMapDto {

    private Long menuId;
    private String menuTitle;
    private Integer menuPrice;
    private String menuPinImgUrl;
    private List<String> menuTagImgUrls;
    private List<String> menuImgUrls;
    private MenuFolderInfoOnMapDto menuFolderInfo;
    private Long mapId;
    private Double mapX;
    private Double mapY;

    public static MenuInfoOnMapDto of(Menu menu, List<MenuTag> menuTags, List<MenuImg> menuImgs,
                                      MenuFolderInfoOnMapDto menuFolderInfo, UrlConverter urlConverter) {
        String menuPinImgUrl = urlConverter.getMenuPinMapUrl(menu.getPin());
        List<String> menuTagImgUrls = menuTags.stream()
                .map(MenuTag::getTag)
                .map(urlConverter::getOrangeTagImgUrl)
                .toList();

        return MenuInfoOnMapDto.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuPinImgUrl(menuPinImgUrl)
                .menuTagImgUrls(menuTagImgUrls)
                .menuImgUrls(menuImgs.stream().map(
                        menuImg -> menuImg.getImgUrl()
                ).collect(Collectors.toList()))
                .menuFolderInfo(menuFolderInfo)
                .mapId(menu.getStore().getMap().getId())
                .mapX(menu.getStore().getMap().getLocation().getX())
                .mapY(menu.getStore().getMap().getLocation().getY())
                .build();
    }
}
