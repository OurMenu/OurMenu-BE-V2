package com.ourmenu.backend.domain.cash.util;

import com.ourmenu.backend.domain.cash.domain.MenuFolderIcon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MenuFolderIconConvertor {

    @Value("${spring.cloud.aws.credentials.default.bucket.url}")
    private String url;

    public String getIcon(MenuFolderIcon icon) {
        return url + "/menu-pin/" + icon.getImgUrl();
    }
}