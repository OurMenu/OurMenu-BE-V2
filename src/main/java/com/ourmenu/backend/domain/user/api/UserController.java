package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.application.MealTimeService;
import com.ourmenu.backend.domain.user.application.UserService;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.dto.request.EmailSignInRequest;
import com.ourmenu.backend.domain.user.dto.request.EmailSignUpRequest;
import com.ourmenu.backend.domain.user.dto.request.MealTimeRequest;
import com.ourmenu.backend.domain.user.dto.request.PasswordRequest;
import com.ourmenu.backend.domain.user.dto.response.ReissueRequest;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.domain.user.dto.response.UserDto;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MealTimeService mealTimeService;

    @Operation(summary = "이메일 회원가입", description = "이메일 회원가입한다")
    @PostMapping("/sign-up")
    private ApiResponse<Void> signUp(@Valid @RequestBody EmailSignUpRequest emailSignUpRequest) {
        userService.signUp(emailSignUpRequest);
        return ApiUtil.successOnly();
    }

    @Operation(summary = "이메일 로그인", description = "이메일 로그인한다.")
    @PostMapping("/sign-in")
    private ApiResponse<TokenDto> signIn(@Valid @RequestBody EmailSignInRequest request, HttpServletResponse response) {
        TokenDto tokenDto = userService.signIn(request, response);
        return ApiUtil.success(tokenDto);
    }

    @Operation(summary = "패스워드 변경", description = "패스워드를 변경한다.")
    @PatchMapping("/password")
    private ApiResponse<Void> changePassword(@Valid @RequestBody PasswordRequest request,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.changePassword(request, userDetails);
        return ApiUtil.successOnly();
    }

    @Operation(summary = "식사 시간 변경", description = "식사 시간을 변경한다.")
    @PatchMapping("/meal-time")
    private ApiResponse<Void> changeMealTime(@Valid @RequestBody MealTimeRequest request,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        mealTimeService.changeMealTime(request, userDetails);
        return ApiUtil.successOnly();
    }

    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회한다")
    @GetMapping("")
    private ApiResponse<UserDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDto response = userService.getUserInfo(userDetails);
        return ApiUtil.success(response);
    }

    @Operation(summary = "로그 아웃", description = "로그아웃한다.")
    @PostMapping("/sign-out")
    private ApiResponse<Void> signOut(HttpServletRequest request,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.signOut(request, userDetails.getId());
        return ApiUtil.successOnly();
    }

    @Operation(summary = "토큰 갱신", description = "refresh 토큰을 갱신한다.")
    @PostMapping("/reissue-token")
    private ApiResponse<TokenDto> reissueToken(@Valid @RequestBody ReissueRequest reissueRequest) {
        TokenDto response = userService.reissueToken(reissueRequest);
        return ApiUtil.success(response);
    }
}
