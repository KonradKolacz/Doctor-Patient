package com.example.zajecia30.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FutureDateException extends RuntimeException{

    public String getMessage() {
        return "Date must be from future";
    }
}
