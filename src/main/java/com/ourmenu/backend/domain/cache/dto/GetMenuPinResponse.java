package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetMenuPinResponse {

    private MenuPin menuPin;
    private String menuPinAddImgUrl;
    private String menuPinAddDisableImgUrl;

    public static GetMenuPinResponse from(SimpleMenuPinResponse simpleMenuPinResponse) {
        return GetMenuPinResponse.builder()
                .menuPin(simpleMenuPinResponse.getMenuPin())
                .menuPinAddImgUrl(simpleMenuPinResponse.getMenuPinAddImgUrl())
                .menuPinAddDisableImgUrl(simpleMenuPinResponse.getMenuPinAddDisableImgUrl())
                .build();
    }
}
