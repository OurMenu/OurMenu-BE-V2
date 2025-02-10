package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class SimpleHomeImgResponse {

    private HomeImg homeImg;
    private String homeImgUrl;

    public static SimpleHomeImgResponse of(HomeImg homeImg, String homeImgUrl) {
        return SimpleHomeImgResponse.builder()
                .homeImg(homeImg)
                .homeImgUrl(homeImgUrl)
                .build();
    }
}
