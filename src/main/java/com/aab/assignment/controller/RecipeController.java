package com.aab.assignment.controller;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.domain.Response;
import com.aab.assignment.domain.validatation.groups.AddRecipeValidateGroup;
import com.aab.assignment.domain.validatation.groups.UpdateRecipeValidateGroup;
import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.exception.RecipeNotFoundException;
import com.aab.assignment.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "recipe")
public class RecipeController {

    Logger log = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService service;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getRecipes(
            @RequestParam(required = false) Map<String, String> filterCondition)
            throws BadRequestException, RecipeManagerException {

        List<Map<String, Object>> result = null;
        result = service.getRecepies(filterCondition);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = { "/{name}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getRecipe(
            @PathVariable String name)
            throws BadRequestException, RecipeNotFoundException, RecipeManagerException {

        Map<String, Object> result = null;
        result = service.getRecipe(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> addRecipe(
            @Validated({ AddRecipeValidateGroup.class }) @RequestBody(required = true) Recipe recipe)
            throws RecipeManagerException {
        log.info("Adding new recipe.");

        Map<String, Object> result = null;
        result = service.addRecipe(recipe);
        log.info("Added new recipe.");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = { "/{name}"}, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> editRecipe(
            @Validated({ UpdateRecipeValidateGroup.class })
            @RequestBody(required = true) Recipe updateRequest, @PathVariable(required = true) String name)
            throws RecipeManagerException {
        log.info("Updating recipe.");
        Map<String, Object> result = service.updateRecipe(updateRequest, name);

        log.info("Recipe updated.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = { "/{name}", }, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteRecipe(
            @PathVariable(required = true) String name)
            throws RecipeManagerException {
        log.info("Deleting recipe.");
        service.deleteRecipe(name);

        log.info("Recipe deleted.");
        return new ResponseEntity<>(new Response("Recipe deleted."), HttpStatus.OK);
    }

}
