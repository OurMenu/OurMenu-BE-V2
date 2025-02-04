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
    private String menuPinMapImgUrl;
    private String menuPinAddImgUrl;
    private String menuPinAddDisableImgUrl;

    public static SimpleMenuPinResponse of(MenuPin menuPin, String menuPinMapImgUrl, String menuPinAddImgUrl,
                                           String menuPinAddDisableImgUrl) {
        return SimpleMenuPinResponse.builder()
                .menuPin(menuPin)
                .menuPinMapImgUrl(menuPinMapImgUrl)
                .menuPinAddImgUrl(menuPinAddImgUrl)
                .menuPinAddDisableImgUrl(menuPinAddDisableImgUrl)
                .build();
    }
}
