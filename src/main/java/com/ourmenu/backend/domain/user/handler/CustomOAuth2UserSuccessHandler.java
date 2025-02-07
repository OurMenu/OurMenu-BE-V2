package com.ourmenu.backend.domain.user.handler;

import com.ourmenu.backend.domain.user.dao.RefreshTokenRepository;
import com.ourmenu.backend.domain.user.dao.UserRepository;
import com.ourmenu.backend.domain.user.domain.RefreshToken;
import com.ourmenu.backend.domain.user.domain.SignInType;
import com.ourmenu.backend.domain.user.domain.User;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.global.response.util.ApiUtil;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        String accessToken = extractAccessToken(request);

        if (email == null || accessToken == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 정보가 없습니다.");
            return;
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user = existingUser.orElseGet(() -> registerKakaoUser(email));

        TokenDto tokenDto = jwtTokenProvider.createAllToken(user.getEmail());
        RefreshToken refreshToken = new RefreshToken(tokenDto.getRefreshToken(), user.getEmail());

        refreshTokenRepository.save(refreshToken);

        ApiUtil.sendSuccessResponse(response, ApiUtil.success(tokenDto));
    }

    private String extractAccessToken(HttpServletRequest request) {
        return request.getParameter("code");
    }

    private User registerKakaoUser(String email) {
        User newUser = User.builder()
                .email(email)
                .signInType(SignInType.KAKAO)
                .build();
        return userRepository.save(newUser);
    }
}
