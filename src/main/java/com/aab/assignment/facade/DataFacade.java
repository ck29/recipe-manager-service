package com.aab.assignment.facade;

import org.springframework.beans.factory.annotation.Autowired;

import com.aab.assignment.exception.RecipeManagerException;

abstract public class DataFacade implements RecipeDB{
    
    @Autowired
    protected DBClient client;
    protected static final String TABLE = "recipes";

    abstract public void createItem(String request) throws RecipeManagerException;
    abstract public void query() throws RecipeManagerException;
    abstract public void scan() throws RecipeManagerException;
    abstract public void update() throws RecipeManagerException;
    abstract public void delete() throws RecipeManagerException;

}
