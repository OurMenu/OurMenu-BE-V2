package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.dto.request.PostEmailRequest;
import com.ourmenu.backend.domain.user.dto.request.SignInRequest;
import com.ourmenu.backend.domain.user.dto.request.SignUpRequest;
import com.ourmenu.backend.domain.user.dto.request.UpdateMealTimeRequest;
import com.ourmenu.backend.domain.user.dto.request.UpdatePasswordRequest;
import com.ourmenu.backend.domain.user.dto.response.KakaoExistenceResponse;
import com.ourmenu.backend.domain.user.dto.response.ReissueRequest;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.domain.user.dto.response.UserDto;
import com.ourmenu.backend.domain.user.exception.NotFoundUserException;
import com.ourmenu.backend.domain.user.exception.NotMatchTokenException;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.config.GlobalDataConfig;
import com.ourmenu.backend.global.data.GlobalUserTestData;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Import({GlobalDataConfig.class, TestConfig.class})
@DisplayName("유저 통합 테스트")
public class UserApiTest {

    @Autowired
    UserController userController;

    @Autowired
    GlobalUserTestData userTestData;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
    }

    @Test
    public void 회원_가입을_통해_유저를_생성할_수_있다() {
        //given
        ArrayList<Integer> mealTime = new ArrayList<>();
        mealTime.add(12);
        mealTime.add(16);
        SignUpRequest signUpRequest = new SignUpRequest("test123@gmail.com",
                "password123",
                mealTime,
                "EMAIL");

        //when
        ApiResponse<TokenDto> response = userController.signUp(signUpRequest);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    public void 로그인을_할_수_있다() {
        //given
        CustomUserDetails testEmailUser = userTestData.createTestEmailUser();
        SignInRequest request = new SignInRequest(
                "testEmailUser@naver.com",
                "password1!",
                "EMAIL"
        );
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        //when
        ApiResponse<TokenDto> signInResponse = userController.signIn(request, response);

        //then
        Assertions.assertThat(signInResponse.isSuccess()).isEqualTo(true);
    }

    @Test
    public void 카카오_계정을_검증할_수_있다() {
        //given
        CustomUserDetails testKakaoUser = userTestData.createTestKakaoUser();
        PostEmailRequest request = new PostEmailRequest("testKakaoUser@naver.com");

        //when
        ApiResponse<KakaoExistenceResponse> response = userController.checkKakaoUserExists(request);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
    }
    
    @Test
    public void 식사시간_정보를_변경할_수_있다() {
        //given
        CustomUserDetails testEmailUserWithMealTime = userTestData.createTestEmailUserWithMealTime();
        ArrayList<Integer> changeMealTime = new ArrayList<>();
        changeMealTime.add(10);
        changeMealTime.add(16);
        UpdateMealTimeRequest request = new UpdateMealTimeRequest(changeMealTime);

        //when
        ApiResponse<Void> response = userController.changeMealTime(request, testEmailUserWithMealTime);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
    }
    
    @Test
    public void 비밀번호를_변경할_수_있다() {
        //given
        CustomUserDetails testEmailUser = userTestData.createTestEmailUser();
        UpdatePasswordRequest request = new UpdatePasswordRequest(
                "password1!",
                "password2@"
        );
        
        //when
        ApiResponse<Void> response = userController.changePassword(request, testEmailUser);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    public void 로그아웃_할_수_있다() {
        //given
        CustomUserDetails testEmailUser = userTestData.createTestEmailUser();
        SignInRequest request = new SignInRequest(
                "testEmailUser@naver.com",
                "password1!",
                "EMAIL"
        );
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        ApiResponse<TokenDto> signInResponse = userController.signIn(request, httpServletResponse);
        httpServletRequest.addHeader("Authorization",
                "Bearer " + signInResponse.getResponse().getRefreshToken());

        //when
        ApiResponse<Void> response = userController.signOut(httpServletRequest, testEmailUser);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThatThrownBy(() -> userController.reissueToken
                        (new ReissueRequest(signInResponse.getResponse().getRefreshToken())))
                .isInstanceOf(NotMatchTokenException.class);
    }

    @Test
    public void 본인의_유저_정보를_조회할_수_있다() {
        //given
        CustomUserDetails testEmailUser = userTestData.createTestEmailUserWithMealTime();
        MealTime mealTime = MealTime.builder()
                .userId(testEmailUser.getId())
                .mealTime(LocalTime.NOON)
                .build();

        //when
        ApiResponse<UserDto> response = userController.getUserInfo(testEmailUser);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getEmail()).isEqualTo("testEmailUser@naver.com");
        Assertions.assertThat(response.getResponse().getSignInType()).isEqualTo("EMAIL");
        Assertions.assertThat(response.getResponse().getMealTime()).contains(mealTime.getMealTime());
    }

    @Test
    public void 본인의_유저_정보를_삭제할_수_있다() {
        //given
        CustomUserDetails testEmailUser = userTestData.createTestEmailUser();

        //when
        ApiResponse<Void> response = userController.deleteUser(testEmailUser);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThatThrownBy(() -> userController.getUserInfo(testEmailUser))
                .isInstanceOf(NotFoundUserException.class);
    }


    @Test
    public void 토큰을_재발급_받을_수_있다() {
        //given
        List<Integer> mealTime = List.of(12, 16);
        SignUpRequest signUpRequest = new SignUpRequest("test123@gmail.com",
                "password123",
                mealTime,
                "EMAIL");
        ApiResponse<TokenDto> signUpResponse = userController.signUp(signUpRequest);
        ReissueRequest request = new ReissueRequest(signUpResponse.getResponse().getRefreshToken());

        //when
        ApiResponse<TokenDto> response = userController.reissueToken(request);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThat(response.getResponse().getRefreshToken()).isNotEqualTo(request.getRefreshToken());
    }
}
