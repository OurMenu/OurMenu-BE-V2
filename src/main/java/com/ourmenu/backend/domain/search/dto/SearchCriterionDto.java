package com.ourmenu.backend.domain.search.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 식당, 메뉴 검색사용되는 Dto
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SearchCriterionDto {

    private String query;
    private double mapX;
    private double mapY;

    public static SearchCriterionDto of(String query, double mapX, double mapY) {
        return SearchCriterionDto.builder()
                .query(query)
                .mapX(mapX)
                .mapY(mapY)
                .build();
    }
}
