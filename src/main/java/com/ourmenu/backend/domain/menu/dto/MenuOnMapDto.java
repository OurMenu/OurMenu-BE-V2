package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.cache.util.MenuPinConverter;
import com.ourmenu.backend.domain.cache.application.UrlConverterService;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.store.domain.Map;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuOnMapDto {

    private Long mapId;
    private String menuPinImgUrl;
    private String menuPinDisableImgUrl;
    private Double mapX;
    private Double mapY;

    public static MenuOnMapDto from(Map map, List<Menu> menus, UrlConverterService urlConverterService) {

        List<MenuPin> menuPins = menus.stream()
                .map(Menu::getPin)
                .toList();
        MenuPin menuPin = MenuPinConverter.of(menuPins);
        String menuPinImgUrl = urlConverterService.getMenuPinMapUrl(menuPin);
        String menuPinDisableImgUrl = urlConverterService.getMenuPinMapAddDisable(menuPin);

        return MenuOnMapDto.builder()
                .mapId(map.getId())
                .menuPinImgUrl(menuPinImgUrl)
                .menuPinDisableImgUrl(menuPinDisableImgUrl)
                .mapX(map.getLocation().getX())
                .mapY(map.getLocation().getY())
                .build();
    }
}
