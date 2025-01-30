package com.ourmenu.backend.domain.user.handler;

import com.ourmenu.backend.domain.user.dao.RefreshTokenRepository;
import com.ourmenu.backend.domain.user.domain.RefreshToken;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");

        boolean isExist = Boolean.TRUE.equals(oAuth2User.getAttribute("exist"));
        String role = oAuth2User.getAuthorities().stream().
                findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        if (isExist) {
            TokenDto tokenDto = jwtTokenProvider.createAllToken(email);
            String accessToken = tokenDto.getAccessToken();
            RefreshToken refreshToken = new RefreshToken(tokenDto.getRefreshToken(), email);
            refreshTokenRepository.save(refreshToken);

            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/loginSuccess")
                    .queryParam("accessToken", accessToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
           getRedirectStrategy().sendRedirect(request, response, targetUrl);
           return;
        }

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/loginSuccess")
                .queryParam("email", (String) oAuth2User.getAttribute("email"))
                .queryParam("provider", provider)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}