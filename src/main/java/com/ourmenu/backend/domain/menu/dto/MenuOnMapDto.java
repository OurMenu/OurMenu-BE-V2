package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.cache.util.MenuPinConverter;
import com.ourmenu.backend.domain.cache.util.UrlConverter;
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

    public static MenuOnMapDto from(Map map, List<Menu> menus, UrlConverter urlConverter) {

        List<MenuPin> menuPins = menus.stream()
                .map(Menu::getPin)
                .toList();
        MenuPin menuPin = MenuPinConverter.of(menuPins);
        String menuPinImgUrl = urlConverter.getMenuPinMapUrl(menuPin);
        String menuPinDisableImgUrl = urlConverter.getMenuPinMapAddDisable(menuPin);

        return MenuOnMapDto.builder()
                .mapId(map.getId())
                .menuPinImgUrl(menuPinImgUrl)
                .menuPinDisableImgUrl(menuPinDisableImgUrl)
                .mapX(map.getLocation().getX())
                .mapY(map.getLocation().getY())
                .build();
    }
}
