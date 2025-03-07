package com.ourmenu.backend.domain.home.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.dto.MenuSimpleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRecommendMenuResponse {

    private Long menuId;
    private String menuTitle;
    private int menuPrice;
    private String menuImgUrl;

    public static GetRecommendMenuResponse of(Menu menu, String imgUrl) {
        return GetRecommendMenuResponse.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .menuPrice(menu.getPrice())
                .menuImgUrl(imgUrl)
                .build();
    }

    public static GetRecommendMenuResponse of(MenuSimpleDto menuSimpleDto, String imgUrl) {
        return GetRecommendMenuResponse.builder()
                .menuId(menuSimpleDto.getMenuId())
                .menuTitle(menuSimpleDto.getMenuTitle())
                .menuPrice(menuSimpleDto.getMenuPrice())
                .menuImgUrl(imgUrl)
                .build();
    }
}
