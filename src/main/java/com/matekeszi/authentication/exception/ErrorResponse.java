package com.matekeszi.authentication.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    public String errorMessage;
}
