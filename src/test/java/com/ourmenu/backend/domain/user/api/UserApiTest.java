package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.dto.request.SignUpRequest;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.config.GlobalDataConfig;
import com.ourmenu.backend.global.data.GlobalUserTestData;
import com.ourmenu.backend.global.response.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;

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

    CustomUserDetails customUserDetails;

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


}
