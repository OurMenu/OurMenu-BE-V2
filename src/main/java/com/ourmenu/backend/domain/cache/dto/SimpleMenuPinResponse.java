package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class SimpleMenuPinResponse {

    private MenuPin menuPin;
    private String menuPinUrl;

    public static SimpleMenuPinResponse of(MenuPin menuPin, String menuPinUrl) {
        return SimpleMenuPinResponse.builder()
                .menuPin(menuPin)
                .menuPinUrl(menuPinUrl)
                .build();
    }
}
