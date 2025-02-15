package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import com.ourmenu.backend.domain.tag.domain.Tag;
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
    private MenuPin menuPin;
    private List<Tag> menuTags;
    private List<String> menuImgUrls;
    private MenuFolderInfoOnMapDto menuFolderInfo;
    private Long mapId;
    private Double mapX;
    private Double mapY;

    public static MenuInfoOnMapDto of(Menu menu, List<MenuTag> menuTags, List<MenuImg> menuImgs,
                                      MenuFolderInfoOnMapDto menuFolderInfo) {
        return MenuInfoOnMapDto.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuPin(menu.getPin())
                .menuTags(menuTags.stream().map(
                        MenuTag::getTag
                ).collect(Collectors.toList()))
                .menuImgUrls(menuImgs.stream().map(
                        menuImg -> menuImg.getImgUrl()
                ).collect(Collectors.toList()))
                .menuFolderInfo(menuFolderInfo)
                .mapId(menu.getStore().getMap().getId())
                .mapX(menu.getStore().getMap().getMapX())
                .mapY(menu.getStore().getMap().getMapY())
                .build();
    }
}
