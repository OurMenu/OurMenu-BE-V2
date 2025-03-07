package com.ourmenu.backend.domain.home.exception;

import static com.ourmenu.backend.global.exception.ErrorCode.DESERIALIZE_FAILURE;

import com.ourmenu.backend.global.exception.CustomException;

public class DeserializeFailureException extends CustomException {

    public DeserializeFailureException() {
        super(DESERIALIZE_FAILURE);
    }
}
