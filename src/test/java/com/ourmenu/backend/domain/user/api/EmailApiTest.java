package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.dto.request.PostEmailRequest;
import com.ourmenu.backend.domain.user.dto.request.SignInRequest;
import com.ourmenu.backend.domain.user.dto.request.VerifyEmailRequest;
import com.ourmenu.backend.domain.user.dto.response.EmailResponse;
import com.ourmenu.backend.domain.user.dto.response.TemporaryPasswordResponse;
import com.ourmenu.backend.domain.user.exception.NotMatchPasswordException;
import com.ourmenu.backend.global.DatabaseCleaner;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.config.GlobalDataConfig;
import com.ourmenu.backend.global.data.GlobalUserTestData;
import com.ourmenu.backend.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Import({GlobalDataConfig.class, TestConfig.class})
@DisplayName("이메일 통합 테스트")
public class EmailApiTest {

    @Autowired
    EmailController emailController;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @Autowired
    UserController userController;

    @Autowired
    GlobalUserTestData userTestData;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
    }

    @Test
    public void 이메일_인증을_요청할_수_있다() {
        //given
        PostEmailRequest request = new PostEmailRequest("test123@naver.com");

        //when
        ApiResponse<EmailResponse> response = emailController.sendConfirmCode(request);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
    }
    
    @Test
    public void 이메일_인증_코드를_검증한다() {
        //given
        PostEmailRequest emailRequest = new PostEmailRequest("test123@naver.com");
        ApiResponse<EmailResponse> emailResponse = emailController.sendConfirmCode(emailRequest);
        VerifyEmailRequest request = new VerifyEmailRequest("test123@naver.com",
                emailResponse.getResponse().getCode());

        //when
        ApiResponse<Void> response = emailController.verifyEmail(request);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    public void 임시_비밀번호를_발급받을_수_있다() {
        //given
        CustomUserDetails testEmailUser = userTestData.createTestEmailUser();
        PostEmailRequest request = new PostEmailRequest("testEmailUser@naver.com");
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);

        //when
        ApiResponse<TemporaryPasswordResponse> response = emailController.sendTemporaryPassword(request);

        //then
        Assertions.assertThat(response.isSuccess()).isEqualTo(true);
        Assertions.assertThatCode(() -> userController.signIn
                (new SignInRequest(testEmailUser.getEmail(), response.getResponse().getTemporaryPassword(), "EMAIL")
                ,httpServletResponse))
                .doesNotThrowAnyException();
    }
}
