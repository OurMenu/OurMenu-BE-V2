package com.ourmenu.backend.domain.home.dto;

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

    private Answer answer;
    private List<GetRecommendMenuResponse> answerRecommendMenus;
    private Tag tag;
    private List<GetRecommendMenuResponse> tagRecommendMenus;
    private List<GetRecommendMenuResponse> otherRecommendMenus;

    public static GetHomeRecommendResponse of(Answer answer,
                                              List<GetRecommendMenuResponse> answerRecommendMenus,
                                              Tag tag,
                                              List<GetRecommendMenuResponse> tagRecommendMenus,
                                              List<GetRecommendMenuResponse> otherRecommendMenus) {
        return GetHomeRecommendResponse.builder()
                .answer(answer)
                .answerRecommendMenus(answerRecommendMenus)
                .tag(tag)
                .tagRecommendMenus(tagRecommendMenus)
                .otherRecommendMenus(otherRecommendMenus)
                .build();
    }
}
