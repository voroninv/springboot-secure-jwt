package com.base.jwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("User already exists.");
    }
}