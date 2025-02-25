package com.ourmenu.backend.domain.home.api;

import com.ourmenu.backend.domain.home.application.HomeService;
import com.ourmenu.backend.domain.home.dto.GetHomeRecommendResponse;
import com.ourmenu.backend.domain.home.dto.SaveAndGetQuestionRequest;
import com.ourmenu.backend.domain.home.dto.SaveAnswerRequest;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "질문 생성", description = "새로운 질문을 생성한다.")
    @PostMapping("/questions")
    public ApiResponse<SaveAndGetQuestionRequest> saveAndGetQuestion(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        SaveAndGetQuestionRequest response = homeService.updateQuestionAnswer(userDetails.getId());
        return ApiUtil.success(response);
    }

    @Operation(summary = "질문 응답 저장", description = "질문에 대한 응답을 저장한다.")
    @PostMapping("/questions/answers")
    public ApiResponse<Void> saveQuestionAnswer(@RequestBody SaveAnswerRequest request,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        homeService.updateQuestionAnswer(request, userDetails.getId());
        return ApiUtil.successOnly();
    }

    @Operation(summary = "홈 추천 메뉴 조회", description = "추천 메뉴를 조회한다.")
    @GetMapping
    public ApiResponse<GetHomeRecommendResponse> getHomeMenus(@AuthenticationPrincipal CustomUserDetails userDetails) {

        GetHomeRecommendResponse response = homeService.getRecommendMenus(userDetails.getId());
        return ApiUtil.success(response);
    }
}
