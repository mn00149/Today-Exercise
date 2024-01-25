package com.todayexercise.excepton.customExceptionClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RefresTokenException extends RuntimeException{

    public RefresTokenException(String message){
        super(message);
    }
}
