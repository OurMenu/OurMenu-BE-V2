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
    private boolean isCrawled;

    public static GetStoreResponse from(SearchableStore searchableStore) {
        List<GetStoreMenuResponse> getStoreMenuResponses = searchableStore.getStoreMenus().stream()
                .map(GetStoreMenuResponse::from)
                .toList();
        List<String> imgUrls = searchableStore.getStoreImgs().stream()
                .map(UrlUtil::parseStoreImgUrl)
                .limit(3)
                .toList();
        Double mapX = searchableStore.getLocation().getCoordinates().get(0);
        Double mapY = searchableStore.getLocation().getCoordinates().get(1);
        return GetStoreResponse.builder()
                .storeId(searchableStore.getStoreId())
                .storeTitle(searchableStore.getTitle())
                .storeAddress(searchableStore.getAddress())
                .storeImgs(imgUrls)
                .menus(getStoreMenuResponses)
                .storeMapX(mapX)
                .storeMapY(mapY)
                .isCrawled(true)
                .build();
    }

    public static GetStoreResponse from(NotFoundStore notFoundStore) {
        return GetStoreResponse.builder()
                .storeId(notFoundStore.getStoreId())
                .storeTitle(notFoundStore.getTitle())
                .storeAddress(notFoundStore.getAddress())
                .storeMapX(notFoundStore.getMapX())
                .storeMapY(notFoundStore.getMapY())
                .isCrawled(false)
                .build();
    }
}
