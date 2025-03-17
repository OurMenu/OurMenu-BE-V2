package com.ourmenu.backend.domain.search.dto;

import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.domain.SearchableStore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class SimpleSearchDto {

    private String storeId;
    private boolean isCrawled;
    private String storeTitle;
    private String storeAddress;
    private Double mapX;
    private Double mapY;

    public static SimpleSearchDto of(SearchableStore searchableStore, boolean isCrawled) {
        Double mapX = searchableStore.getLocation().getCoordinates().get(0);
        Double mapY = searchableStore.getLocation().getCoordinates().get(1);
        return SimpleSearchDto.builder()
                .storeId(searchableStore.getStoreId())
                .isCrawled(isCrawled)
                .storeTitle(searchableStore.getTitle())
                .storeAddress(searchableStore.getAddress())
                .mapX(mapX)
                .mapY(mapY)
                .build();
    }

    public static SimpleSearchDto of(NotFoundStore notFoundStore, boolean isCrawled) {
        return SimpleSearchDto.builder()
                .storeId(notFoundStore.getStoreId())
                .isCrawled(isCrawled)
                .storeTitle(notFoundStore.getTitle())
                .storeAddress(notFoundStore.getAddress())
                .mapX(notFoundStore.getMapX())
                .mapY(notFoundStore.getMapY())
                .build();
    }
}
