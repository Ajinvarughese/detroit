package com.Detroit.detroit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN) // 403
public class SuspendedUserException extends RuntimeException {
    public SuspendedUserException(String message) {super(message);}
}
