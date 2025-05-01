package com.ourmenu.backend.domain.home.data;

import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

public class HomeTestData {

    EntityManager entityManager;

    public HomeTestData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public HomeQuestionAnswer createQuestion(Long userId) {
        HomeQuestionAnswer homeQuestionAnswer = HomeQuestionAnswer.builder()
                .question(Question.FEEL)
                .userId(userId)
                .build();
        entityManager.persist(homeQuestionAnswer);
        return homeQuestionAnswer;
    }
}
