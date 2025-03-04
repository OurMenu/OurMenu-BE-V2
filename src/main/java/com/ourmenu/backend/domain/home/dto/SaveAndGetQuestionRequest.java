package com.ourmenu.backend.domain.home.dto;

import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SaveAndGetQuestionRequest {

    private Question question;

    public static SaveAndGetQuestionRequest from(HomeQuestionAnswer homeQuestionAnswer) {
        return SaveAndGetQuestionRequest.builder()
                .question(homeQuestionAnswer.getQuestion())
                .build();
    }
}
