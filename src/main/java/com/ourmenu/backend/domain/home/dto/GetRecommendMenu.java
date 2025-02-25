package com.ourmenu.backend.domain.home.dto;

import com.ourmenu.backend.domain.menu.domain.Menu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetRecommendMenu {

    private Long menuId;
    private String menuTitle;
    private int menuPrice;
    private String menuImgUrl;

    public static GetRecommendMenu from(Menu menu) {
        return GetRecommendMenu.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .build();
    }
}
