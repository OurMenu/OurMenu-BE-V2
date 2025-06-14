package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.store.util.AddressParser;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetSimpleMenuResponse {

    private Long menuId;
    private String menuTitle;
    private String storeTitle;
    private String storeAddress;
    private int menuPrice;
    private String menuImgUrl;
    private LocalDateTime createdAt;

    public static GetSimpleMenuResponse of(Menu menu, String menuImgUrl) {
        String storeAddress = AddressParser.parseAddressToCityDistrict(menu.getStore().getAddress());

        return GetSimpleMenuResponse.builder()
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .storeTitle(menu.getStore().getTitle())
                .storeAddress(storeAddress)
                .menuPrice(menu.getPrice())
                .menuImgUrl(menuImgUrl)
                .createdAt(menu.getCreatedAt())
                .build();
    }

    public static GetSimpleMenuResponse of(MenuSimpleDto menuSimpleDto, String menuImgUrl) {
        return GetSimpleMenuResponse.builder()
                .menuId(menuSimpleDto.getMenuId())
                .menuTitle(menuSimpleDto.getMenuTitle())
                .storeTitle(menuSimpleDto.getStoreTitle())
                .storeAddress(menuSimpleDto.getStoreAddress())
                .menuPrice(menuSimpleDto.getMenuPrice())
                .menuImgUrl(menuImgUrl)
                .createdAt(menuSimpleDto.getCreatedAt())
                .build();
    }
}
