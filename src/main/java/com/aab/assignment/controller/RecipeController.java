package com.aab.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "recipe")
public class RecipeController {

    // @Autowired
    // private PasswordService service;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "greet", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity createPassword() {
        return new ResponseEntity("Hi, Its recipe manager.", HttpStatus.OK);
    }
}
