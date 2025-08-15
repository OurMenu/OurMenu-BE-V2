package com.ourmenu.backend.domain.cache.application;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.cache.dto.GetCacheInfoResponse;
import com.ourmenu.backend.domain.cache.dto.GetMenuFolderIconResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleHomeImgResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleMenuFolderIconResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleMenuPinResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleTagImgResponse;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final UrlConverterService urlConvertor;

    public GetCacheInfoResponse getCacheInfo() {
        List<SimpleMenuFolderIconResponse> menuFolderIconInfo = getMenuFolderIconInfo();
        List<SimpleMenuPinResponse> menuPinInfo = getMenuPinInfo();
        List<SimpleHomeImgResponse> homeImgInfo = getHomeImgInfo();
        List<SimpleTagImgResponse> tagInfo = getTagImgInfo();

        return GetCacheInfoResponse.of(menuFolderIconInfo, menuPinInfo, homeImgInfo, tagInfo);
    }


    public List<GetMenuFolderIconResponse> getMenuFolderIcon() {
        List<SimpleMenuFolderIconResponse> menuFolderIconInfo = getMenuFolderIconInfo();

        return menuFolderIconInfo.stream()
                .map(GetMenuFolderIconResponse::from)
                .toList();
    }

    private List<SimpleMenuFolderIconResponse> getMenuFolderIconInfo() {
        return Arrays.stream(MenuFolderIcon.values())
                .map(menuFolderIcon -> {
                    String menuFolderIconUrl = urlConvertor.getMenuFolderImgUrl(menuFolderIcon);
                    return SimpleMenuFolderIconResponse.of(menuFolderIcon, menuFolderIconUrl);
                })
                .toList();
    }

    private List<SimpleMenuPinResponse> getMenuPinInfo() {
        return Arrays.stream(MenuPin.values())
                .map(menuPin -> {
                    String menuPinMapUrl = urlConvertor.getMenuPinMapUrl(menuPin);
                    String menuPinAddUrl = urlConvertor.getMenuPinAddUrl(menuPin);
                    String menuPinMapAddDiable = urlConvertor.getMenuPinMapAddDisable(menuPin);
                    return SimpleMenuPinResponse.of(menuPin, menuPinMapUrl, menuPinAddUrl,
                            menuPinMapAddDiable);
                })
                .toList();
    }

    private List<SimpleHomeImgResponse> getHomeImgInfo() {
        return Arrays.stream(HomeImg.values())
                .map(homeImg -> {
                            String homeImgUrl = urlConvertor.getHomeImgUrl(homeImg);
                            return SimpleHomeImgResponse.of(homeImg, homeImgUrl);
                        }
                )
                .toList();
    }

    private List<SimpleTagImgResponse> getTagImgInfo() {
        return Arrays.stream(Tag.values())
                .map(tag -> {
                            String orangeTagImgUrl = urlConvertor.getOrangeTagImgUrl(tag);
                            String whiteTagImgUrl = urlConvertor.getWhiteTagImgUrl(tag);
                            return SimpleTagImgResponse.of(tag, orangeTagImgUrl, whiteTagImgUrl);
                        }
                )
                .toList();
    }
}
