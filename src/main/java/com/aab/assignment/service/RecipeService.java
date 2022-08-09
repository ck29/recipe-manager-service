package com.aab.assignment.service;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.facade.RecipeDataFacade;
import com.aab.assignment.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    Logger log = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    private RecipeDataFacade facade;

    public Map<String, Object> addRecipe(Recipe recipe) throws RecipeManagerException {
        if (recipe != null) {
            try {
                log.info("Add request received.");
                String recipe_json = JsonUtil.toJson(recipe);
                facade.createItem(recipe_json);
                log.info("Add request completed.");
                return getRecipe(recipe.getName());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RecipeManagerException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Empty request cannot be processed.");
        }

    }

    public void deleteRecipe(String name) throws RecipeManagerException {
        if (StringUtils.isNotEmpty(name)) {
            log.info("Delete request received.");
            Map<String, String> keys = new HashMap<>();
            keys.put("name", name);
            facade.delete(keys);
            log.info("Delete request compeleted.");
        } else {
            throw new BadRequestException("Empty request cannot be processed.");
        }

    }

    public Map<String, Object> updateRecipe(Recipe updateRequest, String recipeName) throws RecipeManagerException {
        if (updateRequest != null && StringUtils.isNotEmpty(recipeName)) {
            log.info("Update request received.");
            log.debug("Getting existing recipie");
            Map<String, Object> existing = getRecipe(recipeName);

            if(!existing.isEmpty()) {
                Recipe existingRecipe = (new ObjectMapper()).convertValue(getRecipe(recipeName), Recipe.class);
                Recipe newRecipe = updateRequest;

                if (existingRecipe.equals(newRecipe)) {
                    log.debug("New recipe is same as existing.");
                    throw new BadRequestException("Nothing to update.");
                }

                this.deleteRecipe(existingRecipe.getName());
                this.addRecipe(newRecipe);
                log.info("Update request completed.");
                return this.getRecipe(newRecipe.getName());
            }else{
                throw new BadRequestException("Invalid recipe to update.");
            }
        } else {
            throw new BadRequestException("Empty request cannot be processed.");
        }
    }

    public List<Map<String, Object>> getRecepies(Map<String, String> filter) throws BadRequestException, RecipeManagerException {

        if (!filter.isEmpty()) {
            if(isValidFilter(filter)) {
                return facade.scan(filter);
            }else{
              throw new BadRequestException("Invalid filter parameter.");
            }
        }else{
            return facade.scan();
        }
    }

    private boolean isValidFilter(Map<String, String> filter) {
        return Filter.IsValidFilterList(filter);
    }

    public Map<String, Object> getRecipe(String name) throws BadRequestException, RecipeManagerException {
        if (StringUtils.isNotEmpty(name)) {
            Map<String, String> filter = new HashMap<>();
            filter.put("name", name);
            List<Map<String, Object>> scanList = facade.scan(filter);
            if(scanList.isEmpty()){
                return new HashMap<>();
            }else{
                return scanList.get(0);
            }
        } else {
            throw new BadRequestException("Invalid recipe");
        }
    }
}
