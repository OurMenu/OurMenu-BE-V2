package com.ourmenu.backend.domain.home.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetHomeRecommendResponse {

    private List<GetRecommendMenu> answerRecommendMenus;
    private List<GetRecommendMenu> tagRecommendMenus;
    private List<GetRecommendMenu> otherRecommendMenus;

    public static GetHomeRecommendResponse of(List<GetRecommendMenu> answerRecommendMenus,
                                              List<GetRecommendMenu> tagRecommendMenus,
                                              List<GetRecommendMenu> otherRecommendMenus) {
        return GetHomeRecommendResponse.builder()
                .answerRecommendMenus(answerRecommendMenus)
                .tagRecommendMenus(tagRecommendMenus)
                .otherRecommendMenus(otherRecommendMenus)
                .build();
    }
}
