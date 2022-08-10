package com.aab.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

import java.util.*;

import com.amazonaws.util.json.Jackson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.BadRequestException;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRecipe() throws JsonMappingException, JsonProcessingException {
        String req = "{\"instructions\":\"fry on pan.\",\"serves\":1,\"name\":\"omlet\",\"ingredients\":[\"egg\",\"onion\"],\"type\":\"non veg\"}";
        Recipe reqRecipe = JsonUtil.toObject(req, Recipe.class);
        doNothing().when(facade).createItem(req);
        try {
            service.addRecipe(reqRecipe);
            assert(true);
        } catch (RecipeManagerException e) {
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
        } catch (BadRequestException e) {
            assert(true);
        }

    }

    @Test
    void testDeleteRecipe() throws JsonMappingException, JsonProcessingException {
        String req = "{\"instructions\":\"fry on pan.\",\"serves\":1,\"name\":\"omlet\",\"ingredients\":[\"egg\",\"onion\"],\"type\":\"non veg\"}";
        Recipe reqRecipe = JsonUtil.toObject(req, Recipe.class);
        try {
            service.deleteRecipe("omlet");
            assert(true);
        } catch (RecipeManagerException e) {
            assert(false);
        }
    }

    @Test
    void testDeleteRecipeWithError(){
        Recipe reqRecipe = null;
        try {
            service.deleteRecipe("");
            assert(false);
        } catch (BadRequestException e) {
            assert(true);
        }
    }


    @Test
    void testGetRecepies() throws JsonMappingException, JsonProcessingException {
        String recipeList = "{\"instructions\":\"fry on pan.\",\"serves\":1,\"name\":\"omlet\",\"ingredients\":[\"egg\",\"onion\"],\"type\":\"non veg\"}";
        Map<String, Object> r =  JsonUtil.toObject(recipeList, HashMap.class);

        List<Map<String, Object>> rList = new ArrayList<>();
        rList.add(r);
        Map<String, String> filterMap1 = new HashMap<String, String>(){{
        }};
        try {
            when(facade.scan()).thenReturn(rList);
            List<Map<String, Object>> sResponse = service.getRecepies(filterMap1);
            assertEquals(1, sResponse.size());
        } catch (RecipeManagerException e) {
            e.printStackTrace();
        }
        Map<String, String> filterMap2 = new HashMap<String, String>(){{
            put("type","veg");
        }};

        try {
            when(facade.scan((Map<String, String>)notNull())).thenReturn(rList);
            List<Map<String, Object>> sResponse = service.getRecepies(filterMap2);
            assertEquals(1, sResponse.size());
        } catch (RecipeManagerException e) {
            e.printStackTrace();
        }
    }

    
    @Test
    void testUpdateRecipe() throws JsonMappingException, JsonProcessingException {

        String new_rcp = "{\"name\":\"veg salad\",\"type\":\"veg\",\"ingredients\":[\"raddish\",\" tomato\"],\"serves\":3,\"instructions\":\"cut and put in bowl.\"}";
        Recipe newR = JsonUtil.toObject(new_rcp, Recipe.class);

        Map<String, Object> r = new HashMap<String, Object>(){{
            put("name", "veg salad");
            put("serves", 2);
            put("ingredients", Arrays.asList("raddish", "tomato"));
            put("type","veg");
            put("instructions", "cut and put");
        }};
        List<Map<String, Object>> rList = new ArrayList<>();
        rList.add(r);

        Map<String, String> filter = new HashMap<String, String>(){{
            put("name","veg salad");
        }};
        doReturn(rList).when(facade).scan(filter);
        try {
            service.updateRecipe(newR,"veg salad");
            assert(true);
        } catch (RecipeManagerException e) {
            assert(false);
        } catch(Exception e){
            assert(false);
        }

    }

    @Test
    void testUpdateRecipeSameRecipe() throws JsonMappingException, JsonProcessingException {
        String new_rcp = "{\"name\":\"veg salad\",\"type\":\"veg\",\"ingredients\":[\"raddish\",\"tomato\"],\"serves\":3,\"instructions\":\"cut and put in bowl.\"}";
        Recipe newR = JsonUtil.toObject(new_rcp, Recipe.class);

        Map<String, Object> r = new HashMap<String, Object>(){{
            put("name", "veg salad");
            put("serves", 3);
            put("ingredients", Arrays.asList("raddish", "tomato"));
            put("type","veg");
            put("instructions", "cut and put in bowl.");
        }};
        List<Map<String, Object>> rList = new ArrayList<>();
        rList.add(r);

        Map<String, String> filter = new HashMap<String, String>(){{
            put("name","veg salad");
        }};
        doReturn(rList).when(facade).scan(filter);
        try {
            service.updateRecipe(newR, "veg salad");
            assert(false);
        } catch (BadRequestException e) {
            assert(true);
        } catch(Exception e){
            assert(false);
        }


    }

    @Test
    void testUpdateRecipeNotinput() throws JsonMappingException, JsonProcessingException {

        Recipe updateRequest = null;
        try {
            service.updateRecipe(updateRequest, "salda");
            assert(false);
        } catch (BadRequestException e) {
            assert(true);
        } catch(RecipeManagerException e){
            assert(false);
        }


    }
}
