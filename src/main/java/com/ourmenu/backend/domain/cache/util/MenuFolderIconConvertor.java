package com.ourmenu.backend.domain.cache.util;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MenuFolderIconConvertor {

    @Value("${spring.cloud.aws.credentials.default.bucket.url}")
    private String url;

    public String getIcon(MenuPin icon) {
        return url + "/menu-pin/" + icon.getImgUrl();
    }
}