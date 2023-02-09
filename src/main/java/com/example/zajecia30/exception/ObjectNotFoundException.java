package com.example.zajecia30.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ObjectNotFoundException extends RuntimeException{

    private long id;
    private String name;

    public String getMessage() {
        return name + " with id: " + id + " not found";
    }
}
