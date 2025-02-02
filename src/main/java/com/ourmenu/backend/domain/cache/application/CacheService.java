package com.ourmenu.backend.domain.cache.application;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.cache.dto.GetCacheInfoResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleHomeImgResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleMenuFolderIconResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleMenuPinResponse;
import com.ourmenu.backend.domain.cache.util.UrlConvertor;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final UrlConvertor urlConvertor;

    public GetCacheInfoResponse getCacheInfo() {
        List<SimpleMenuFolderIconResponse> menuFolderIconInfo = getMenuFolderIconInfo();
        List<SimpleMenuPinResponse> menuPinInfo = getMenuPinInfo();
        List<SimpleHomeImgResponse> homeImgInfo = getHomeImgInfo();

        return GetCacheInfoResponse.of(menuFolderIconInfo, menuPinInfo, homeImgInfo);
    }

    private List<SimpleMenuFolderIconResponse> getMenuFolderIconInfo() {
        return Arrays.stream(MenuFolderIcon.values())
                .map(menuFolderIcon -> {
                    String menuFolderIconUrl = urlConvertor.getMenuFolderUrl(menuFolderIcon);
                    return SimpleMenuFolderIconResponse.of(menuFolderIcon, menuFolderIconUrl);
                })
                .toList();
    }

    private List<SimpleMenuPinResponse> getMenuPinInfo() {
        return Arrays.stream(MenuPin.values())
                .map(menuPin -> {
                    String menuPinUrl = urlConvertor.getMenuPinUrl(menuPin);
                    return SimpleMenuPinResponse.of(menuPin, menuPinUrl);
                })
                .toList();
    }

    private List<SimpleHomeImgResponse> getHomeImgInfo() {
        return Arrays.stream(HomeImg.values())
                .map(homeImg -> {
                    String homeImgUrl = urlConvertor.getHomeImgUrl(homeImg);
                    return SimpleHomeImgResponse.of(homeImg, homeImgUrl);
                })
                .toList();
    }
}
