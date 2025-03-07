package com.ourmenu.backend.domain.home.dto;


import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TagRandomRecommendDto {

    Tag tag;
    List<GetRecommendMenuResponse> getRecommendMenuResponses;

    public static TagRandomRecommendDto of(Tag tag, List<GetRecommendMenuResponse> getRecommendMenuResponses) {
        return TagRandomRecommendDto.builder()
                .tag(tag)
                .getRecommendMenuResponses(getRecommendMenuResponses)
                .build();
    }
}
