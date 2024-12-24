package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.application.UserService;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.dto.*;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * @apiNote 회원가입 API
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    private ApiResponse<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
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
    private ApiResponse<SignInResponse> signIn(@Valid @RequestBody SignInRequest request, HttpServletResponse response){
        SignInResponse signInResponse = userService.signIn(request, response);
        return ApiUtil.success(signInResponse);
    }

    @PatchMapping("/password")
    private ApiResponse<String> changePassword(@Valid @RequestBody PasswordRequest request, @AuthenticationPrincipal CustomUserDetails userDetails){
        String response = userService.changePassword(request, userDetails);
        return ApiUtil.success(response);
    }

    @PatchMapping("/meal-time")
    private ApiResponse<String> changeMealTime(@Valid @RequestBody MealTimeRequest request, @AuthenticationPrincipal CustomUserDetails userDetails){
        String response = userService.changeMealTime(request, userDetails);
        return ApiUtil.success(response);
    }

    @GetMapping("")
    private ApiResponse<UserDto> getUserInfo(@Valid @AuthenticationPrincipal CustomUserDetails userDetails){
        UserDto response = userService.getUserInfo(userDetails);
        return ApiUtil.success(response);
    }

}
