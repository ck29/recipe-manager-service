package com.aab.assignment.exception;

public class RecipeManagerException extends Exception{
    
    public RecipeManagerException() {
    }

    public RecipeManagerException(String errorMessage) {
        super(errorMessage);
    }
}
