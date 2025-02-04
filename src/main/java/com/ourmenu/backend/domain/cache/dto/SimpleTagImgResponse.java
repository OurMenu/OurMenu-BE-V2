package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class SimpleTagImgResponse {

    private Tag tag;
    private String orangeTagImgUrl;
    private String whiteTagImgUrl;

    public static SimpleTagImgResponse of(Tag tag, String orangeTagImgUrl, String whiteTagImgUrl) {
        return SimpleTagImgResponse.builder()
                .tag(tag)
                .orangeTagImgUrl(orangeTagImgUrl)
                .whiteTagImgUrl(whiteTagImgUrl)
                .build();
    }
}
