package com.ourmenu.backend.domain.cache.dto;

import com.ourmenu.backend.domain.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetTagResponse {

    private Tag tag;
    private String orangeTagImgUrl;
    private String whiteTagImgUrl;

    public static GetTagResponse from(SimpleTagImgResponse simpleTagImgResponse) {
        return GetTagResponse.builder()
                .tag(simpleTagImgResponse.getTag())
                .orangeTagImgUrl(simpleTagImgResponse.getOrangeTagImgUrl())
                .whiteTagImgUrl(simpleTagImgResponse.getWhiteTagImgUrl())
                .build();
    }
}
