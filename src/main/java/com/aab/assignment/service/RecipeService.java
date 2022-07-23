package com.aab.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.facade.RecipeDataFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RecipeService {

    Logger log = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    private RecipeDataFacade facade;
    
    public void addRecipe(Recipe recipe) throws RecipeManagerException{
        ObjectMapper mapper = new ObjectMapper();
        try {
            String recipe_json = mapper.writeValueAsString(recipe);
            facade.createItem(recipe_json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RecipeManagerException(e.getMessage());
        }
    }
}
