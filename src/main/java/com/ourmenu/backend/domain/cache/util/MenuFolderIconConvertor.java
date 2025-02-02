package com.ourmenu.backend.domain.cache.util;

import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MenuFolderIconConvertor {

    @Value("${spring.cloud.aws.credentials.default.bucket.url}")
    private String url;

    public String getMenuPinUrl(MenuPin pin) {
        return url + "/menu-pin/" + pin.getImgUrl();
    }

    public String getMenuFolderUrl(MenuFolderIcon icon) {
        return url + "/menu-folder-icon/" + icon.getImgUrl() + ".svg";
    }
}