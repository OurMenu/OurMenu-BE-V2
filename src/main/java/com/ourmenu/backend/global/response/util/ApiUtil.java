package com.ourmenu.backend.global.response.util;

import com.ourmenu.backend.global.response.ApiResponse;

public class ApiUtil {

    private ApiUtil() {
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }
}
