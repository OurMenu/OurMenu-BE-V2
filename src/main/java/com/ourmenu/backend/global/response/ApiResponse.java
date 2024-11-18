package com.ourmenu.backend.global.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ourmenu.backend.global.exception.ErrorResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse<T> {

    private final boolean isSuccess;

    @JsonProperty(value = "response")
    private final T response;

    @JsonProperty(value = "errorResponse")
    private final ErrorResponse errorResponse;
}
