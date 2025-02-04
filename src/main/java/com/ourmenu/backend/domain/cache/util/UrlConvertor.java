package com.ourmenu.backend.domain.cache.util;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.tag.domain.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlConvertor {

    @Value("${spring.cloud.aws.credentials.default.bucket.url}")
    private String url;

    public String getMenuPinMapUrl(MenuPin pin) {
        return url + "/menu-pins/" + pin.getImgUrl() + "_map.svg";
    }

    public String getMenuPinAddUrl(MenuPin pin) {
        return url + "/menu-pins/" + pin.getImgUrl() + "_add.svg";
    }

    public String getMenuPinMapAddDiable(MenuPin pin) {
        return url + "/menu-pins/" + pin.getImgUrl() + "_map_disable.svg";
    }

    public String getMenuFolderUrl(MenuFolderIcon icon) {
        return url + "/menu-folder-icons/" + icon.getImgUrl() + ".svg";
    }

    public String getHomeImgUrl(HomeImg homeImg) {
        return url + "/home/" + homeImg.getImgUrl() + ".svg";
    }

    public String getOrangeTagImgUrl(Tag tag) {
        return url + "/tags/" + tag.getImgUrl() + "_orange.svg";
    }

    public String getWhiteTagImgUrl(Tag tag) {
        return url + "/tags/" + tag.getImgUrl() + "_white.svg";
    }
}