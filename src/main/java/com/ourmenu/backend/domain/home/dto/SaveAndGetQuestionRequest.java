package com.ourmenu.backend.domain.home.dto;

import com.ourmenu.backend.domain.cache.application.UrlConverterService;
import com.ourmenu.backend.domain.home.domain.Answer;
import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SaveAndGetQuestionRequest {

    private String question;
    private List<AnswerDto> answers;

    public static SaveAndGetQuestionRequest from(HomeQuestionAnswer homeQuestionAnswer,
                                                 UrlConverterService urlConverterService) {
        Question question = homeQuestionAnswer.getQuestion();

        Answer answer1 = question.getAnswer1();
        AnswerDto answerDto1 = AnswerDto.builder()
                .answer(answer1)
                .answerImgUrl(urlConverterService.getAnswerImgUrl(answer1))
                .build();

        Answer answer2 = question.getAnswer2();
        AnswerDto answerDto2 = AnswerDto.builder()
                .answer(answer2)
                .answerImgUrl(urlConverterService.getAnswerImgUrl(answer2))
                .build();

        return SaveAndGetQuestionRequest.builder()
                .question(question.getQuestion())
                .answers(List.of(answerDto1, answerDto2))
                .build();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class AnswerDto {
        private Answer answer;
        private String answerImgUrl;

    }
}
