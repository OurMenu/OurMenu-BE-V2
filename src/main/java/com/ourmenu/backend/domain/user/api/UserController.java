package com.ourmenu.backend.domain.user.api;

import com.ourmenu.backend.domain.user.application.UserService;
import com.ourmenu.backend.domain.user.dto.SignInRequest;
import com.ourmenu.backend.domain.user.dto.SignInResponse;
import com.ourmenu.backend.domain.user.dto.SignUpRequest;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    /**
     * @apiNote 회원가입 API
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    private ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        String response = userService.signUp(signUpRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * @apiNote 로그인 API
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/signin")
    private ResponseEntity<?> signUp(@RequestBody SignInRequest request, HttpServletResponse response){
        SignInResponse signInResponse = userService.signIn(request, response);
        return ResponseEntity.ok(signInResponse);
    }

}
