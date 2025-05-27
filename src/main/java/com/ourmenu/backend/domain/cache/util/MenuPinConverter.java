package com.ourmenu.backend.domain.cache.util;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import java.util.List;

public class MenuPinConverter {

    public static MenuPin of(List<MenuPin> menuPins) {
        if (menuPins.size() == 1) {
            return menuPins.get(0);
        }
        if (menuPins.size() == 2) {
            return MenuPin.TWO;
        }
        if (menuPins.size() == 3) {
            return MenuPin.THREE;
        }
        if (menuPins.size() == 4) {
            return MenuPin.FOUR;
        }
        return MenuPin.FIVE;
    }
}
