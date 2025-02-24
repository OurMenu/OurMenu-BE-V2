package com.ourmenu.backend.domain.home.domain;

import static com.ourmenu.backend.domain.home.domain.Answer.DISLIKE;
import static com.ourmenu.backend.domain.home.domain.Answer.LIKE;
import static com.ourmenu.backend.domain.home.domain.Answer.MOUNTAIN;
import static com.ourmenu.backend.domain.home.domain.Answer.RAINY;
import static com.ourmenu.backend.domain.home.domain.Answer.SEA;
import static com.ourmenu.backend.domain.home.domain.Answer.SUMMER;
import static com.ourmenu.backend.domain.home.domain.Answer.SUNNY;
import static com.ourmenu.backend.domain.home.domain.Answer.SWEET;
import static com.ourmenu.backend.domain.home.domain.Answer.WINTER;

import java.util.Random;
import lombok.Getter;

@Getter
public enum Question {

    FEEL("오늘 기분은 어떠신가요?", LIKE, DISLIKE),
    WEATHER("오늘 날씨는 어떤가요?", SUNNY, RAINY),
    STRESS("스트레스 받을 때는 어떤 음식을 드시나요?", SWEET, RAINY),
    TRIP("어디로 떠나고 싶은가요?", SEA, MOUNTAIN),
    SEASON("어느 계절을 더 좋아하세요?", SUMMER, WINTER);

    private String question;
    private Answer answer1;
    private Answer answer2;

    Question(String question, Answer answer1, Answer answer2) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public static Question getRandomQuestion() {
        Question[] questions = Question.values();
        Random random = new Random();
        return questions[random.nextInt(questions.length)];
    }
}
