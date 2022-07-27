package com.aab.assignment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.facade.RecipeDataFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RecipeService {

    Logger log = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    private RecipeDataFacade facade;

    public void addRecipe(Recipe recipe) throws RecipeManagerException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Add request received.");
            String recipe_json = mapper.writeValueAsString(recipe);
            facade.createItem(recipe_json);
            log.info("Add request completed.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RecipeManagerException(e.getMessage());
        }
    }

    public void deleteRecipe(Recipe recipe) throws RecipeManagerException {
        if (recipe != null) {
            log.info("Delete request received.");
            Map<String, String> keys = new HashMap<>();
            keys.put("type", recipe.getType());
            keys.put("name", recipe.getName());
            facade.delete(keys);
            log.info("Delete request compeleted.");
        } else {
            throw new RecipeManagerException("Empty request cannot be processed.");
        }

    }

    public void updateRecipe(Map<String, Recipe> updateRequest) throws RecipeManagerException {
        if (updateRequest != null){
            log.info("Update request received.");
            Recipe existingRecipe = updateRequest.get("existing"); //TODO Validate existing from DB
            Recipe newRecipe = updateRequest.get("new");

            if(existingRecipe.equals(newRecipe)){
                throw new BadRequestException("Recipe already exists.");
            }
            this.deleteRecipe(existingRecipe);
            this.addRecipe(newRecipe);
            log.info("Delete request completed.");
        }else{
            throw new RecipeManagerException("Empty request cannot be processed.");
        }
    }


    public List<Map<String, Object>> getRecepies(Filter filter) throws RecipeManagerException{
        if(filter!=null){
           return facade.scan(filter);
        }else{
            throw new RecipeManagerException("Empty request cannot be processed.");
        }
    }

    public List<Map<String, Object>> getRecepies() throws RecipeManagerException{
        return facade.scan();
    }

}
