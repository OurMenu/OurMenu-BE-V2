package com.ourmenu.backend.domain.home.application;

import com.ourmenu.backend.domain.home.dao.HomeQuestionAnswerRepository;
import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import com.ourmenu.backend.domain.home.dto.SaveAndGetQuestionRequest;
import com.ourmenu.backend.domain.home.dto.SaveAnswerRequest;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeQuestionAnswerRepository homeQuestionAnswerRepository;

    /**
     * 홈 질문 응답 값을 갱신, 저장한다.
     *
     * @param request
     */
    @Transactional
    public void updateQuestionAnswer(SaveAnswerRequest request, Long userId) {

        Optional<HomeQuestionAnswer> optionalHomeQuestionAnswer = homeQuestionAnswerRepository.findByUserId(userId);

        if (optionalHomeQuestionAnswer.isPresent()) {
            deleteHomeAnswerMenu(optionalHomeQuestionAnswer.get());
        }
        HomeQuestionAnswer homeAnswer = HomeQuestionAnswer.builder()
                .answer(request.getAnswer())
                .userId(userId)
                .build();
        homeQuestionAnswerRepository.save(homeAnswer);
    }

    /**
     * 홈 질문을 생성 및 저장한다.
     *
     * @param userId
     * @return
     */
    @Transactional
    public SaveAndGetQuestionRequest saveQuestionAnswer(Long userId) {
        Question randomQuestion = Question.getRandomQuestion();
        HomeQuestionAnswer homeQuestionAnswer = HomeQuestionAnswer.builder()
                .question(randomQuestion)
                .build();
        HomeQuestionAnswer saveHomeQuestionAnswer = homeQuestionAnswerRepository.save(homeQuestionAnswer);
        return SaveAndGetQuestionRequest.from(saveHomeQuestionAnswer);
    }

    private void deleteHomeAnswerMenu(HomeQuestionAnswer homeQuestionAnswer) {

    }
}
