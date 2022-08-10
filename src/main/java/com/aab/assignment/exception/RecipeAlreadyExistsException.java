package com.aab.assignment.exception;

public class RecipeAlreadyExistsException extends RuntimeException{

    public RecipeAlreadyExistsException() {
    }

    public RecipeAlreadyExistsException(String message) {
        super(message);
    }
}
