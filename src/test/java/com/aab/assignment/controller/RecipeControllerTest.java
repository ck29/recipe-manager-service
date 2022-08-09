package com.aab.assignment.controller;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.exception.*;
import com.aab.assignment.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
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
        this.mockmvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RecipeControllerAdvisor()).build();
    }

    @Test
    public void testAddRecipe() throws Exception {
        Recipe recipe = createRecipe();
        Map<String, Object> result = objectMapper.convertValue(recipe, Map.class);

        doReturn(result).when(service).addRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .post("/recipe/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
                .andExpect(content().json(json));
        
    }

    @Test
    public void testAddRecipeInvalidInput() throws Exception {
        Recipe recipe = createRecipe();
        recipe.setServes(null);
        Map<String, Object> result = objectMapper.convertValue(recipe, Map.class);

        doReturn(result).when(service).addRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .post("/recipe/")
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
            .post("/recipe/")
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
            .post("/recipe/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isInternalServerError());
        
    }

    @Test
    public void deleteRecipe() throws Exception {
        String recipe = "salad";

        doNothing().when(service).deleteRecipe(recipe);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .delete("/recipe/salad")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());

    }

    @Test
    public void deleteRecipeInvalidData() throws Exception {
        doThrow(new BadRequestException()).when(service).deleteRecipe("salad");

        mockmvc.perform(
            MockMvcRequestBuilders
            .delete("/recipe/salad")
            .contentType(MediaType.APPLICATION_JSON)
            .content(""))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteRecipeNotFound() throws Exception {
        doThrow(new RecipeNotFoundException()).when(service).deleteRecipe("recipe");

        mockmvc.perform(
            MockMvcRequestBuilders
            .delete("/recipe/recipe")
            .contentType(MediaType.APPLICATION_JSON)
            .content(""))
            .andExpect(status().isNotFound());

    }

    @Test
    public void updateRecipe() throws Exception {
        Recipe recipe = createRecipe();

        Map<String, Object> result = objectMapper.convertValue(recipe, Map.class);
        doReturn(result).when(service).updateRecipe(recipe, "salad");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .put("/recipe/salad")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());

    }

    @Test
    public void updateRecipeWithNoChange() throws Exception {

        Recipe recipe = createRecipe();

       doThrow(new RecipeAlreadyExistsException()).when(service).updateRecipe(recipe, "salad");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .put("/recipe/salad")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void updateRecipeNonExistent() throws Exception {

        Recipe recipe = createRecipe();

       doThrow(new RecipeNotFoundException()).when(service).updateRecipe(recipe, "salad");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(recipe);
        mockmvc.perform(
            MockMvcRequestBuilders
            .put("/recipe/salad")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isNotFound());

    }

    @Test
    public void getRecipe() throws Exception{
        List<Map<String, Object>> req = createResponseRecords();
        Map<String, String> filter = null;
        Mockito.when(service.getRecepies(filter)).thenReturn(req);
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
