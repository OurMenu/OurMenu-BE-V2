package com.ourmenu.backend.global.filter;

import com.ourmenu.backend.domain.user.exception.TokenExpiredException;
import com.ourmenu.backend.global.exception.ErrorCode;
import com.ourmenu.backend.global.exception.ErrorResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            if(e instanceof TokenExpiredException) {
                ApiUtil.sendResponse(response, ApiUtil.error(ErrorResponse.of(ErrorCode.TOKEN_EXPIRED)));
            }
            else {
                ApiUtil.sendResponse(response, ApiUtil.error(ErrorResponse.of(ErrorCode.INVALID_TOKEN)));
            }
        }
    }

}