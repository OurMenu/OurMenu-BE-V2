package com.ourmenu.backend.domain.home.api;

import com.ourmenu.backend.domain.home.application.HomeService;
import com.ourmenu.backend.domain.home.dto.SaveAndGetQuestionRequest;
import com.ourmenu.backend.domain.home.dto.SaveAnswerRequest;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping
    public ApiResponse<SaveAndGetQuestionRequest> saveAndGetQuestion(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        SaveAndGetQuestionRequest response = homeService.saveQuestionAnswer(userDetails.getId());
        return ApiUtil.success(response);
    }

    @PostMapping
    public ApiResponse<Void> saveQuestionAnswer(@RequestBody SaveAnswerRequest request,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        homeService.updateQuestionAnswer(request, userDetails.getId());
        return ApiUtil.successOnly();
    }
}
