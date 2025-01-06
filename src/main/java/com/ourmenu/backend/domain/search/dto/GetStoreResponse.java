package com.ourmenu.backend.domain.search.dto;

import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
import com.ourmenu.backend.domain.search.util.UrlUtil;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetStoreResponse {

    private String storeId;
    private String storeTitle;
    private String storeAddress;
    private List<String> storeImgs;
    private List<GetStoreMenuResponse> menus;
    private Double storeMapX;
    private Double storeMapY;

    public static GetStoreResponse from(SearchableStore searchableStore) {
        List<GetStoreMenuResponse> getStoreMenuResponses = searchableStore.getStoreMenus().stream()
                .map(GetStoreMenuResponse::from)
                .toList();
        List<String> imgUrls = searchableStore.getStoreImgs().stream()
                .map(UrlUtil::parseStoreImgUrl)
                .toList();
        return GetStoreResponse.builder()
                .storeId(searchableStore.getStoreId())
                .storeTitle(searchableStore.getTitle())
                .storeAddress(searchableStore.getAddress())
                .storeImgs(imgUrls)
                .menus(getStoreMenuResponses)
                .storeMapX(searchableStore.getMapX())
                .storeMapY(searchableStore.getMapY())
                .build();
    }

    public static GetStoreResponse from(NotFoundStore notFoundStore) {
        return GetStoreResponse.builder()
                .storeId(notFoundStore.getStoreId())
                .storeTitle(notFoundStore.getTitle())
                .storeAddress(notFoundStore.getAddress())
                .storeMapX(notFoundStore.getMapX())
                .storeMapY(notFoundStore.getMapY())
                .build();
    }
}
