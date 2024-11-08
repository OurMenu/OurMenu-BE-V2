package com.ourmenu.backend.global.filter;


import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.getHeaderToken(request, "Access");
        String refreshToken = jwtTokenProvider.getHeaderToken(request, "Refresh");

        if(accessToken != null) {
            if(jwtTokenProvider.tokenValidation(accessToken)){
                setAuthentication(jwtTokenProvider.getEmailFromToken(accessToken));
            }
            else if (refreshToken != null) {
                boolean isRefreshToken = jwtTokenProvider.refreshTokenValidation(refreshToken);
                if (isRefreshToken) {
                    String loginId = jwtTokenProvider.getEmailFromToken(refreshToken);
                    String newAccessToken = jwtTokenProvider.createToken(loginId, "Access");

                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    setAuthentication(jwtTokenProvider.getEmailFromToken(newAccessToken));
                }
            }
        }

        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtTokenProvider.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
