package com.ourmenu.backend.domain.home.dto;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.domain.home.domain.Answer;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetHomeRecommendResponse {

    private String answerImgUrl;
    private List<GetRecommendMenuResponse> answerRecommendMenus;
    private String tagRecommendImgUrl;
    private List<GetRecommendMenuResponse> tagRecommendMenus;
    private String otherRecommendImgUrl;
    private List<GetRecommendMenuResponse> otherRecommendMenus;

    public static GetHomeRecommendResponse of(Answer answer,
                                              List<GetRecommendMenuResponse> answerRecommendMenus,
                                              Tag tag,
                                              List<GetRecommendMenuResponse> tagRecommendMenus,
                                              List<GetRecommendMenuResponse> otherRecommendMenus,
                                              UrlConverter urlConverter) {
        HomeImg homeImg = answer.getRandomHomeImg();
        String HomeImgUrl = urlConverter.getHomeImgUrl(homeImg);
        String tagRecommendImgUrl = urlConverter.getHomeRecommendTagImgUrl(tag);
        String otherRecommendImgUrl = tagRecommendImgUrl;

        return GetHomeRecommendResponse.builder()
                .answerImgUrl(HomeImgUrl)
                .answerRecommendMenus(answerRecommendMenus)
                .tagRecommendImgUrl(tagRecommendImgUrl)
                .tagRecommendMenus(tagRecommendMenus)
                .otherRecommendImgUrl(otherRecommendImgUrl)
                .otherRecommendMenus(otherRecommendMenus)
                .build();
    }
}
