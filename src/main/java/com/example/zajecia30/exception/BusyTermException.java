package com.example.zajecia30.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusyTermException extends RuntimeException{
    public String getMessage() {
        return "Term is busy";
    }
}
