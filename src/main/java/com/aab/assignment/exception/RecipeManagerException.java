package com.aab.assignment.exception;

public class RecipeManagerException extends RuntimeException{
    
    public RecipeManagerException() {
    }

    public RecipeManagerException(String errorMessage) {
        super(errorMessage);
    }
}
