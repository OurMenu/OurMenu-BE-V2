package com.ourmenu.backend.domain.cache.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class GetCacheInfoResponse {

    private List<SimpleMenuFolderIconResponse> menuFolderIcons;
    private List<SimpleMenuPinResponse> menuPins;
    private List<SimpleHomeImgResponse> homeImgs;

    private List<SimpleTagImgResponse> tags;

    public static GetCacheInfoResponse of(List<SimpleMenuFolderIconResponse> simpleMenuFolderIconResponses,
                                          List<SimpleMenuPinResponse> simpleMenuPinResponses,
                                          List<SimpleHomeImgResponse> homeImgs,
                                          List<SimpleTagImgResponse> tags
    ) {
        return GetCacheInfoResponse.builder()
                .menuFolderIcons(simpleMenuFolderIconResponses)
                .menuPins(simpleMenuPinResponses)
                .homeImgs(homeImgs)
                .tags(tags)
                .build();
    }
}
