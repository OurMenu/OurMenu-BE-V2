package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.application.EmailService;
import com.ourmenu.backend.domain.user.dto.request.EmailRequest;
import com.ourmenu.backend.domain.user.dto.response.EmailResponse;
import com.ourmenu.backend.domain.user.dto.request.VerifyEmailRequest;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("")
    private ApiResponse<EmailResponse> sendConfirmCode(@Valid @RequestBody EmailRequest request){
        EmailResponse response = emailService.sendCodeToEmail(request);
        return ApiUtil.success(response);
    }

    @PostMapping("/confirm-code")
    private ApiResponse<Void> verifyEmail(@Valid @RequestBody VerifyEmailRequest request){
        emailService.verifyConfirmCode(request);
        return ApiUtil.successOnly();
    }
}
