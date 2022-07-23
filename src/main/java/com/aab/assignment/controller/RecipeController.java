package com.aab.assignment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aab.assignment.domain.Recipe;
import com.aab.assignment.domain.Response;

@Controller
@RequestMapping(value = "recipe")
public class RecipeController {

    // @Autowired
    // private PasswordService service;

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
    @RequestMapping(value = "new", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> addRecipe(@RequestBody Recipe recipe) {
        System.out.println(recipe.getName());
        System.out.println(recipe.getInstructions());
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
