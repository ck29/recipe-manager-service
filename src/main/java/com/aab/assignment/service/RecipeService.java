package com.aab.assignment.service;

import org.springframework.stereotype.Service;

import com.aab.assignment.domain.Recipe;

@Service
public class RecipeService {
    
    public void addRecipe(Recipe recipe) {
        System.out.println(recipe);
    }
}
