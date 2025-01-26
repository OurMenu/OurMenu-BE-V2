package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.Menu;
import java.time.LocalDateTime;
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
    private String storeTitle;
    private String storeAddress;
    private int menuPrice;
    private String menuImgUrl;
    private LocalDateTime createdAt;

    public static GetMenuResponse of(Menu menu, String menuImgUrl) {
        return GetMenuResponse.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .storeTitle(menu.getStore().getTitle())
                .storeAddress(menu.getStore().getAddress())
                .menuPrice(menu.getPrice())
                .menuImgUrl(menuImgUrl)
                .createdAt(menu.getCreatedAt())
                .build();
    }
}
