package com.ourmenu.backend.domain.menu.dto;

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
    private List<String> menuTags;
    private List<String> menuImgUrls;
    private MenuFolderInfoOnMapDto menuFolderInfo;

    public static MenuInfoOnMapDto of(Menu menu, List<MenuTag> menuTags, List<MenuImg> menuImgs,
                                      MenuFolderInfoOnMapDto menuFolderInfo) {
        return MenuInfoOnMapDto.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuTags(menuTags.stream().map(
                        menuTag -> menuTag.getTag().getTagName()
                ).collect(Collectors.toList()))
                .menuImgUrls(menuImgs.stream().map(
                        menuImg -> menuImg.getImgUrl()
                ).collect(Collectors.toList()))
                .menuFolderInfo(menuFolderInfo)
                .build();
    }
}
