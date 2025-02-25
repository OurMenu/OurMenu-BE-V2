package com.ourmenu.backend.domain.home.application;

import com.ourmenu.backend.domain.home.dao.HomeQuestionAnswerRepository;
import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import com.ourmenu.backend.domain.home.dto.GetHomeRecommendResponse;
import com.ourmenu.backend.domain.home.dto.GetRecommendMenu;
import com.ourmenu.backend.domain.home.dto.SaveAndGetQuestionRequest;
import com.ourmenu.backend.domain.home.dto.SaveAnswerRequest;
import com.ourmenu.backend.domain.home.exception.NotFoundQuestionException;
import com.ourmenu.backend.domain.menu.application.MenuService;
import com.ourmenu.backend.domain.user.application.MealTimeService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeQuestionAnswerRepository homeQuestionAnswerRepository;
    private final RecommendMenuCacheService recommendMenuCacheService;
    private final MenuService menuService;
    private final MealTimeService mealTimeService;

    /**
     * 홈 질문 응답 값을 저장한다. 질문에 관련 없는 응답이면 에러를 반환한다.
     *
     * @param request
     */
    @Transactional
    public void updateQuestionAnswer(SaveAnswerRequest request, Long userId) {

        Optional<HomeQuestionAnswer> optionalHomeQuestionAnswer = homeQuestionAnswerRepository.findByUserId(userId);

        if (optionalHomeQuestionAnswer.isEmpty()) {
            throw new NotFoundQuestionException();
        }

        HomeQuestionAnswer homeQuestionAnswer = optionalHomeQuestionAnswer.get();
        homeQuestionAnswer.getQuestion().validateQuestionAnswer(request.getAnswer());
        homeQuestionAnswer.update(request.getAnswer());
    }

    /**
     * 홈 질문을 갱신, 저장한다. 이미 질문이 있는 경우 갱신하고, 그렇지 않으면 생성한다.
     *
     * @param userId
     * @return
     */
    @Transactional
    public SaveAndGetQuestionRequest updateQuestionAnswer(Long userId) {
        Optional<HomeQuestionAnswer> optionalHomeQuestionAnswer = homeQuestionAnswerRepository.findByUserId(userId);
        Question randomQuestion = Question.getRandomQuestion();

        if (optionalHomeQuestionAnswer.isEmpty()) {
            HomeQuestionAnswer homeQuestionAnswer = HomeQuestionAnswer.builder()
                    .question(randomQuestion)
                    .userId(userId)
                    .build();
            HomeQuestionAnswer saveHomeQuestionAnswer = homeQuestionAnswerRepository.save(homeQuestionAnswer);
            return SaveAndGetQuestionRequest.from(saveHomeQuestionAnswer);
        }

        HomeQuestionAnswer homeQuestionAnswer = optionalHomeQuestionAnswer.get();
        homeQuestionAnswer.update(randomQuestion);
        return SaveAndGetQuestionRequest.from(homeQuestionAnswer);
    }


    /**
     * 추천 메뉴 조회 및 저장한다. 홈메뉴 추천 캐시가 없다면 추가한다.
     *
     * @param userId
     * @return
     */
    public GetHomeRecommendResponse getRecommendMenus(Long userId) {
        List<GetRecommendMenu> getHomeRecommendMenus = recommendMenuCacheService.getStoreResponse(userId);

        if (getHomeRecommendMenus == null) {
            getHomeRecommendMenus = menuService.findRecommendMenu(userId);
            long nextUpdateMinute = mealTimeService.getNextUpdateMinute(userId);
            recommendMenuCacheService.cacheStoreResponse(userId, getHomeRecommendMenus, nextUpdateMinute);
        }

        return GetHomeRecommendResponse.of(getHomeRecommendMenus, null, null);
    }
}
