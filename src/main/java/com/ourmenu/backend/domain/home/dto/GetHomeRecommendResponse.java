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
    private List<GetRecommendMenu> answerRecommendMenus;
    private Tag tag;
    private List<GetRecommendMenu> tagRecommendMenus;
    private List<GetRecommendMenu> otherRecommendMenus;

    public static GetHomeRecommendResponse of(Answer answer,
                                              List<GetRecommendMenu> answerRecommendMenus,
                                              Tag tag,
                                              List<GetRecommendMenu> tagRecommendMenus,
                                              List<GetRecommendMenu> otherRecommendMenus) {
        return GetHomeRecommendResponse.builder()
                .answer(answer)
                .answerRecommendMenus(answerRecommendMenus)
                .tag(tag)
                .tagRecommendMenus(tagRecommendMenus)
                .otherRecommendMenus(otherRecommendMenus)
                .build();
    }
}
