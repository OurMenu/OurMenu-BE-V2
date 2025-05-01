package com.ourmenu.backend.domain.home.api;

import com.ourmenu.backend.domain.home.config.HomeTestConfig;
import com.ourmenu.backend.domain.home.data.HomeTestData;
import com.ourmenu.backend.domain.home.data.UserTestData;
import com.ourmenu.backend.domain.home.domain.Answer;
import com.ourmenu.backend.domain.home.dto.GetHomeRecommendResponse;
import com.ourmenu.backend.domain.home.dto.SaveAndGetQuestionRequest;
import com.ourmenu.backend.domain.home.dto.SaveAnswerRequest;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.response.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({TestConfig.class, HomeTestConfig.class})
@DisplayName("홈 통합 테스트")
public class HomeApiTest {

    @Autowired
    HomeController homeController;

    @Autowired
    HomeTestData homeTestData;

    @Autowired
    UserTestData userTestData;

    @Autowired
    DatabaseCleaner databaseCleaner;

    CustomUserDetails testCustomUserDetails;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        testCustomUserDetails = userTestData.createTestEmailUserWithMealTime();
    }

    @Test
    void 온보딩_질문을_갱신_또는_생성_할_수_있다() {
        //given

        //when
        ApiResponse<SaveAndGetQuestionRequest> response = homeController.saveAndGetQuestion(
                testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void 온보딩_질문_답을_저장_할_수_있다() {
        //given
        homeTestData.createTestQuestion(testCustomUserDetails.getId());

        //when
        SaveAnswerRequest request = new SaveAnswerRequest(Answer.LIKE);
        ApiResponse<Void> response = homeController.saveQuestionAnswer(request, testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void 올바르지_않는_온보딩_질문_답에_대해서_예외를_발생시킨다() {
        //given
        homeTestData.createTestQuestion(testCustomUserDetails.getId());

        //when
        SaveAnswerRequest request = new SaveAnswerRequest(Answer.SEA);

        //then
        Assertions.assertThatThrownBy(() -> homeController.saveQuestionAnswer(request, testCustomUserDetails))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 온보딩_질문_응답에_대한_추천_메뉴를_구성한다() {
        //given
        homeTestData.createTestHomeMenu(testCustomUserDetails);

        //when
        SaveAnswerRequest request = new SaveAnswerRequest(Answer.LIKE);
        homeController.saveQuestionAnswer(request, testCustomUserDetails);
        ApiResponse<GetHomeRecommendResponse> response = homeController.getHomeMenus(testCustomUserDetails);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getAnswer()).isEqualTo(Answer.LIKE);
        Assertions.assertThat(response.getResponse().getAnswerRecommendMenus().size()).isEqualTo(2);
    }

}
