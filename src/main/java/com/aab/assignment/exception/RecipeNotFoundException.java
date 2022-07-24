package com.aab.assignment.exception;

public class RecipeNotFoundException extends RecipeManagerException{
    public RecipeNotFoundException() {
    }

    public RecipeNotFoundException (String errorMessage) {
        super(errorMessage);
    }
}
