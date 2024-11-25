package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.application.EmailService;
import com.ourmenu.backend.domain.user.application.UserService;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.dto.*;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final EmailService emailService;

    /**
     * @apiNote 회원가입 API
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    private ApiResponse<String> signUp(@RequestBody SignUpRequest signUpRequest){
        String response = userService.signUp(signUpRequest);
        return ApiUtil.success(response);
    }

    /**
     * @apiNote 로그인 API
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/signin")
    private ApiResponse<SignInResponse> signIn(@RequestBody SignInRequest request, HttpServletResponse response){
        SignInResponse signInResponse = userService.signIn(request, response);
        return ApiUtil.success(signInResponse);
    }

    @PostMapping("/emails")
    private ApiResponse<EmailResponse> sendConfirmCode(@RequestBody EmailRequest request){
        EmailResponse response = emailService.sendCodeToEmail(request);
        return ApiUtil.success(response);
    }

    @PostMapping("/emails/confirm-code")
    private ApiResponse<String> verifyEmail(@RequestBody VerifyEmailRequest request){
        String response = emailService.verifyConfirmCode(request);
        return ApiUtil.success(response);
    }

    @PatchMapping("/password")
    private ApiResponse<String> changePassword(@RequestBody PasswordRequest request, @AuthenticationPrincipal CustomUserDetails userDetails){
        String response = userService.changePassword(request, userDetails);
        return ApiUtil.success(response);
    }

}
