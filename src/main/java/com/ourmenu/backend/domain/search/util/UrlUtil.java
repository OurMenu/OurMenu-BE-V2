package com.ourmenu.backend.domain.search.util;

public class UrlUtil {

    private UrlUtil() {

    }

    public static String parseStoreImgUrl(String imgUrl) {
        if (imgUrl.startsWith("//")) {
            return imgUrl.substring(2);
        }
        return imgUrl;
    }
}
