package com.aab.assignment.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.exception.RecipeNotFoundException;
import com.aab.assignment.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

    private MockMvc mockmvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWrite = new ObjectMapper().writer();

    @Mock
    private RecipeService service;
  
    @InjectMocks
    private RecipeController controller;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockmvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAddRecipe() throws Exception {
        Recipe recipe = createRecipe();
        
        doNothing().when(service).addRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .post("/recipe/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated());
        
    }

    @Test
    public void testAddRecipeInvalidInput() throws Exception {
        Recipe recipe = createRecipe();
        recipe.setServes(null);
        doNothing().when(service).addRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .post("/recipe/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
        
    }

    @Test
    public void testAddRecipeAlreadyExists() throws Exception {
        Recipe recipe = createRecipe();
        
        doThrow(new BadRequestException()).when(service).addRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .post("/recipe/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
        
    }

    @Test
    public void testAddRecipeServerErrors() throws Exception {
        Recipe recipe = createRecipe();
        
        doThrow(new RecipeManagerException()).when(service).addRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .post("/recipe/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isInternalServerError());
        
    }

    @Test
    public void deleteRecipe() throws Exception {
        Recipe recipe = createRecipe();
        
        doNothing().when(service).deleteRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .delete("/recipe/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
        
    }

    @Test
    public void deleteRecipeInvalidData() throws Exception {
        Recipe recipe = createRecipe();
        recipe.setType(null);
        doNothing().when(service).deleteRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .delete("/recipe/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
        
    }

    @Test
    public void deleteRecipeNotFound() throws Exception {
        Recipe recipe = createRecipe();
        doThrow(new RecipeNotFoundException()).when(service).deleteRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(createRecipe());
        mockmvc.perform(
            MockMvcRequestBuilders
            .delete("/recipe/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isNotFound());
        
    }

    @Test
    public void updateRecipe() throws Exception {
        
        Map<String, Recipe> updateRequest = createUpdateRequestIdenticalRecipe();
        
        doNothing().when(service).updateRecipe(updateRequest);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(updateRequest);
        mockmvc.perform(
            MockMvcRequestBuilders
            .put("/recipe/edit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
        
    }
    
    @Test
    public void updateRecipeWithNoChange() throws Exception {
        
        Map<String, Recipe> updateRequest = createUpdateRequestIdenticalRecipe();
        
       doThrow(new BadRequestException()).when(service).updateRecipe(updateRequest);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(updateRequest);
        mockmvc.perform(
            MockMvcRequestBuilders
            .put("/recipe/edit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
        
    }

    @Test
    public void updateRecipeNonExistent() throws Exception {
        
        Map<String, Recipe> updateRequest = createUpdateRequestIdenticalRecipe();
        
       doThrow(new RecipeNotFoundException()).when(service).updateRecipe(updateRequest);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(updateRequest);
        mockmvc.perform(
            MockMvcRequestBuilders
            .put("/recipe/edit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isNotFound());
        
    }

    @Test
    public void getRecipe() throws Exception{
        List<Map<String, Object>> req = createResponseRecords();

        Mockito.when(service.getRecepies()).thenReturn(req);
        mockmvc.perform(
            MockMvcRequestBuilders
            .get("/recipe/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    private Recipe createRecipe(){
        Recipe recipe = new Recipe();
        recipe.setName("salad");
        recipe.setType("veg");
        recipe.setIngredients(Arrays.asList("tomato", "raddish"));
        recipe.setServes(2);
        recipe.setInstructions(new StringBuffer("Cut and put in bowl"));
        return recipe;

    }

    private  Map<String, Recipe>  createUpdateRequestIdenticalRecipe() {
        Recipe exrecipe = new Recipe();
        exrecipe.setName("salad");
        exrecipe.setType("veg");
        exrecipe.setIngredients(Arrays.asList("tomato", "raddish"));
        exrecipe.setServes(2);
        exrecipe.setInstructions(new StringBuffer("Cut and put in bowl"));

        Recipe newrecipe = new Recipe();
        newrecipe.setName("salad");
        newrecipe.setType("veg");
        newrecipe.setIngredients(Arrays.asList("tomato", "raddish"));
        newrecipe.setServes(2);
        newrecipe.setInstructions(new StringBuffer("Cut and put in bowl"));
        
        Map<String, Recipe> updateRequest = new HashMap<>();
        updateRequest.put("existing", exrecipe);
        updateRequest.put("new", newrecipe);
        return updateRequest;
    }

    public List<Map<String, Object>> createResponseRecords(){
        Map<String, Object> ob1 = new HashMap<>();
        ob1.put("name", "salad");
        ob1.put("type", "veg");
        ob1.put("ingredients", Arrays.asList("tomato", "raddish"));
        ob1.put("serves", 2);
        ob1.put("instructions", "cut and put in bowl");

        Map<String, Object> ob2 = new HashMap<>();
        ob2.put("name", "salad");
        ob2.put("type", "veg");
        ob2.put("ingredients", Arrays.asList("tomato", "raddish"));
        ob2.put("serves", 2);
        ob2.put("instructions", "cut and put in bowl");

        List<Map<String, Object>> req = new ArrayList<>();
        req.add(ob1);
        req.add(ob2);
        return req;
    }
    
}
