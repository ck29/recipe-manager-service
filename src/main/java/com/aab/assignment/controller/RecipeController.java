package com.aab.assignment.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.domain.Response;
import com.aab.assignment.domain.validatation.AddRecipeValidateGroup;
import com.aab.assignment.domain.validatation.DeleteRecipeValidateGroup;
import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.exception.RecipeNotFoundException;
import com.aab.assignment.service.RecipeService;
import com.amazonaws.services.kms.model.NotFoundException;

@Controller
@RequestMapping(value = "recipe")
public class RecipeController {

    Logger log = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService service;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "greet", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> greet() {
        return new ResponseEntity<String>("Hi, Its recipe manager.", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getRecepies(@RequestParam Map<String, String> queryparams) {
        return new ResponseEntity(queryparams, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "new", method = RequestMethod.POST, produces = "application/json") //TODO: Implement intelligent validation
    public ResponseEntity<Response> addRecipe(@Validated({AddRecipeValidateGroup.class}) @RequestBody(required = true) Recipe recipe) {
        try{
            log.info("Adding new recipe.");
            service.addRecipe(recipe);
        }
        catch(RecipeManagerException e ){
            log.error(e.getMessage());
            if (e instanceof BadRequestException){
                return new ResponseEntity<Response>(new Response("Request cannot be completed as recipe already exists."), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Response>(new Response("Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Added new recipe.");
        return new ResponseEntity<Response>(new Response("New recipe added."), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "edit", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateRecipe() {
        return new ResponseEntity("Hi, Its recipe manager.", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteRecipe(@Validated({DeleteRecipeValidateGroup.class}) @RequestBody(required = true) Recipe recipe) {
        try{
           log.info("Deleting recipe.");
           service.deleteRecipe(recipe);
        }
        catch(RecipeManagerException e ){
            log.error(e.getMessage());
            if (e instanceof RecipeNotFoundException){
                return new ResponseEntity<Response>(new Response("Recipe not found."), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Response>(new Response("Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Recipe deleted.");
        return new ResponseEntity<Response>(new Response("Recipe deleted."), HttpStatus.OK);
    }

}
