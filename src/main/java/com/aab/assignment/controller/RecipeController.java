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
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.service.RecipeService;

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
    public ResponseEntity<Response> addRecipe(@Validated @RequestBody(required = true) Recipe recipe) {
        try{
            log.info("Adding new recipe.");
            service.addRecipe(recipe);
        }
        catch(RecipeManagerException e ){
            log.error(e.getMessage());
            return new ResponseEntity<Response>(new Response("Server Error."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Response>(new Response("New recipe added."), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "edit", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateRecipe() {
        return new ResponseEntity("Hi, Its recipe manager.", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteRecipe() {
        return new ResponseEntity("Hi, Its recipe manager.", HttpStatus.OK);
    }

}
