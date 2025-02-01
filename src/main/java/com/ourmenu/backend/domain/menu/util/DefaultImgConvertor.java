package com.ourmenu.backend.domain.menu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultImgConvertor {

    @Value("${spring.cloud.aws.credentials.default.bucket.url}")
    private String url;

    public String getDefaultMenuFolderImgUrl() {
        return url + "/default_menu_folder_img.svg";
    }

    public String getDefaultMenuImgUrl() {
        return url + "/default_menu_img.svg";
    }
}
