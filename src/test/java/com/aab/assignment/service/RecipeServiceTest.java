package com.aab.assignment.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.facade.RecipeDataFacade;
import com.aab.assignment.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {
    
    @InjectMocks
    private RecipeService service;

    @Mock
    private RecipeDataFacade facade;

    @BeforeEach
    public void setUp(){
        service = spy(RecipeService.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRecipe() throws JsonMappingException, JsonProcessingException {
        String req = "{\"instructions\":\"fry on pan.\",\"serves\":1,\"name\":\"omlet\",\"ingredients\":[\"egg\",\"onion\"],\"type\":\"non veg\"}";
        Recipe reqRecipe = JsonUtil.toObject(req, Recipe.class);
        try {
            service.addRecipe(reqRecipe);
            assert(true);
        } catch (RecipeManagerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            assert(false);
        }

    }

    @Test
    public void testAddRecipeWithError() {
        Recipe reqRecipe = null;
        try {
            service.addRecipe(reqRecipe);
            assert(false);
        } catch (RecipeManagerException e) {
            assert(true);
        }

    }

    @Test
    void testDeleteRecipe() throws JsonMappingException, JsonProcessingException {
        String req = "{\"instructions\":\"fry on pan.\",\"serves\":1,\"name\":\"omlet\",\"ingredients\":[\"egg\",\"onion\"],\"type\":\"non veg\"}";
        Recipe reqRecipe = JsonUtil.toObject(req, Recipe.class);
        try {
            service.deleteRecipe(reqRecipe);
            assert(true);
        } catch (RecipeManagerException e) {
            assert(false);
        }
    }

    @Test
    void testDeleteRecipeWithError(){
        Recipe reqRecipe = null;
        try {
            service.deleteRecipe(reqRecipe);
            assert(false);
        } catch (RecipeManagerException e) {
            assert(true);
        }
    }


    @Test
    void testGetRecepies() {

    }

    @Test
    void testGetRecepies2() {

    }

    @Test
    void testUpdateRecipe() throws JsonMappingException, JsonProcessingException {
        String existing_rcp = "{\"name\":\"veg salad\",\"type\":\"veg\",\"ingredients\":[\"tomato\",\"raddish\"],\"serves\":2,\"instructions\":\"cut and put in bowl.\"}";
        Recipe existing = JsonUtil.toObject(existing_rcp, Recipe.class);

        String new_rcp = "{\"name\":\"veg salad\",\"type\":\"veg\",\"ingredients\":[\"raddish\",\" tomato\"],\"serves\":3,\"instructions\":\"cut and put in bowl.\"}";
        Recipe newR = JsonUtil.toObject(new_rcp, Recipe.class);

        Map<String, Recipe> updateRequest = new HashMap<>();
        updateRequest.put("existing", existing);
        updateRequest.put("new", newR);
        
        try {
            service.updateRecipe(updateRequest);
            assert(true);
        } catch (RecipeManagerException e) {
            assert(false);
        } catch(Exception e){
            assert(false);
        }

    }
}
