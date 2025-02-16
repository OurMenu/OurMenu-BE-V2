package com.ourmenu.backend.global.response.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourmenu.backend.global.exception.ErrorResponse;
import com.ourmenu.backend.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ApiUtil {

    private ApiUtil() {
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> error(ErrorResponse errorResponse) {
        return new ApiResponse<>(false, null, errorResponse);
    }

    public static ApiResponse<Void> successOnly() {
        return new ApiResponse<>(true, null, null);
    }

    public static void sendResponse(HttpServletResponse response, ApiResponse<?> successMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(successMessage);
        response.getWriter().print(result);
    }
}
