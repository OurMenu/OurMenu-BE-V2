package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.application.EmailService;
import com.ourmenu.backend.domain.user.dto.EmailRequest;
import com.ourmenu.backend.domain.user.dto.EmailResponse;
import com.ourmenu.backend.domain.user.dto.VerifyEmailRequest;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class EmailController {

    private final EmailService emailService;

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
}
