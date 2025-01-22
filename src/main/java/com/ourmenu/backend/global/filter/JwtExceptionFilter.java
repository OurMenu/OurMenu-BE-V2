package com.ourmenu.backend.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourmenu.backend.domain.user.exception.TokenExpiredExcpetion;
import com.ourmenu.backend.global.exception.ErrorCode;
import com.ourmenu.backend.global.exception.ErrorResponse;
import com.ourmenu.backend.global.response.ApiResponse;
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
            if(e.equals(TokenExpiredExcpetion.class)) {
                setResponse(response, ApiUtil.error(ErrorResponse.of(ErrorCode.TOKEN_EXPIRED)));
            }
            else {
                setResponse(response, ApiUtil.error(ErrorResponse.of(ErrorCode.INVALID_TOKEN)));
            }
        }
    }

    private void setResponse(HttpServletResponse response, ApiResponse<?> errorMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(errorMessage);
        response.getWriter().print(result);
    }

}