package com.ourmenu.backend.domain.home.application;

import com.ourmenu.backend.domain.cache.application.UrlConverterService;
import com.ourmenu.backend.domain.home.dao.HomeQuestionAnswerRepository;
import com.ourmenu.backend.domain.home.domain.Answer;
import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import com.ourmenu.backend.domain.home.dto.GetHomeRecommendResponse;
import com.ourmenu.backend.domain.home.dto.GetRecommendMenuResponse;
import com.ourmenu.backend.domain.home.dto.SaveAndGetQuestionRequest;
import com.ourmenu.backend.domain.home.dto.SaveAnswerRequest;
import com.ourmenu.backend.domain.home.dto.TagRandomRecommendDto;
import com.ourmenu.backend.domain.home.exception.NotFoundQuestionException;
import com.ourmenu.backend.domain.home.exception.RecreateQuestionException;
import com.ourmenu.backend.domain.menu.application.MenuService;
import com.ourmenu.backend.domain.tag.domain.Tag;
import com.ourmenu.backend.domain.user.application.MealTimeService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeQuestionAnswerRepository homeQuestionAnswerRepository;
    private final RecommendMenuCacheService recommendMenuCacheService;
    private final MenuService menuService;
    private final MealTimeService mealTimeService;
    private final UrlConverterService urlConverterService;

    /**
     * 홈 질문 응답 값을 저장 및 추천 메뉴를 캐싱한다. 질문에 관련 없는 응답이면 에러를 반환한다.
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

        setRecommendMenus(userId, homeQuestionAnswer.getAnswer());
    }

    /**
     * 홈 질문을 갱신, 저장한다. 이미 질문이 있는 경우 갱신하고, 그렇지 않으면 생성한다.
     *
     * @param userId
     * @return
     */
    @Transactional
    public SaveAndGetQuestionRequest updateQuestion(Long userId) {
        Optional<HomeQuestionAnswer> optionalHomeQuestionAnswer = homeQuestionAnswerRepository.findByUserId(userId);
        Question randomQuestion = Question.getRandomQuestion();

        if (optionalHomeQuestionAnswer.isEmpty()) {
            HomeQuestionAnswer homeQuestionAnswer = HomeQuestionAnswer.builder()
                    .question(randomQuestion)
                    .userId(userId)
                    .build();
            HomeQuestionAnswer saveHomeQuestionAnswer = homeQuestionAnswerRepository.save(homeQuestionAnswer);
            return SaveAndGetQuestionRequest.from(saveHomeQuestionAnswer, urlConverterService);
        }

        HomeQuestionAnswer homeQuestionAnswer = optionalHomeQuestionAnswer.get();
        homeQuestionAnswer.update(randomQuestion);
        return SaveAndGetQuestionRequest.from(homeQuestionAnswer, urlConverterService);
    }

    /**
     * 추천 메뉴 조회 및 저장한다. 홈메뉴 추천 캐시가 없다면 추가한다.
     *
     * @param userId
     * @return
     */
    public GetHomeRecommendResponse getRecommendMenus(Long userId) {
        HomeQuestionAnswer homeQuestionAnswer = findByUserId(userId);
        Answer answer = homeQuestionAnswer.getAnswer();

        List<GetRecommendMenuResponse> questionRecommendMenus = getQuestionRecommendMenus(userId);
        TagRandomRecommendDto tagRandomRecommendDto = getTagRecommendMenus();
        List<GetRecommendMenuResponse> randomRecommendMenus = getRandomRecommendMenu();

        return GetHomeRecommendResponse.of(answer, questionRecommendMenus, tagRandomRecommendDto.getTag(),
                tagRandomRecommendDto.getGetRecommendMenuResponses(), randomRecommendMenus, urlConverterService);
    }

    /**
     * 홈 질문 추천 메뉴 조회
     *
     * @param userId
     * @return
     * @throws RecreateQuestionException 질문에 대한 응답을 생성안한 경우, 질문 과 응답 갱신이 필요한 경우
     */
    private List<GetRecommendMenuResponse> getQuestionRecommendMenus(Long userId) {
        List<GetRecommendMenuResponse> getRecommendMenuResponses = recommendMenuCacheService.getStoreResponse(userId);

        if (getRecommendMenuResponses == null) {
            throw new RecreateQuestionException();
        }
        return getRecommendMenuResponses;
    }

    /**
     * 홈 질문 추천 메뉴 저장 최대 5개를 저장한다.
     *
     * @param userId
     */
    private void setRecommendMenus(Long userId, Answer answer) {
        Tag tag = answer.getTag();
        List<GetRecommendMenuResponse> recommendMenu = menuService.findRecommendMenu(userId, tag, PageRequest.of(0, 5));
        long nextUpdateMinute = mealTimeService.getNextUpdateMinute(userId);
        recommendMenuCacheService.cacheStoreResponse(userId, recommendMenu, nextUpdateMinute);
    }

    /**
     * 랜덤 태그 추천 메뉴를 최대 7개 조회한다.
     *
     * @return
     */
    private TagRandomRecommendDto getTagRecommendMenus() {
        Tag randomTag = Tag.getRandomTag();
        List<GetRecommendMenuResponse> tagRecommendMenus = menuService.findTagRecommendMenu(randomTag,
                PageRequest.of(0, 7));
        return TagRandomRecommendDto.of(randomTag, tagRecommendMenus);
    }

    /**
     * 랜덤 추천 메뉴 최대 7개를 조회한다.
     *
     * @return
     */
    private List<GetRecommendMenuResponse> getRandomRecommendMenu() {
        return menuService.findRandomRecommendMenu(7);
    }

    /**
     * 질문 응답 값 조회
     *
     * @param userId
     * @return
     */
    private HomeQuestionAnswer findByUserId(Long userId) {
        Optional<HomeQuestionAnswer> optionalHomeQuestionAnswer = homeQuestionAnswerRepository.findByUserId(userId);
        if (optionalHomeQuestionAnswer.isEmpty()) {
            throw new NotFoundQuestionException();
        }
        return optionalHomeQuestionAnswer.get();
    }
}
