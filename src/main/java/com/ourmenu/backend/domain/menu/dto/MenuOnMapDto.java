package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.store.domain.Map;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuOnMapDto {

    private Long mapId;
    private List<MenuPin> menuPins;
    private Double mapX;
    private Double mapY;

    public static MenuOnMapDto from(Map map, List<Menu> menus) {
        return MenuOnMapDto.builder()
                .mapId(map.getId())
                .menuPins(menus.stream()
                        .map(Menu::getPin)
                        .collect(Collectors.toList()))
                .mapX(map.getMapX())
                .mapY(map.getMapY())
                .build();
    }
}
