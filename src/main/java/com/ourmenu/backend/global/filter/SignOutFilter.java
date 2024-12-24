package com.ourmenu.backend.global.filter;

import com.ourmenu.backend.domain.user.dao.RefreshTokenRepository;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SignOutFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/api/sign-out".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String email = jwtTokenProvider.getEmailFromToken(token);

                refreshTokenRepository.findRefreshTokenByEmail(email)
                        .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));

                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
