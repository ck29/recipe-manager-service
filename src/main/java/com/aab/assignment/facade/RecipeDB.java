package com.aab.assignment.facade;

import java.util.Map;

import com.aab.assignment.exception.RecipeManagerException;

public interface RecipeDB {
    public void createItem(String request) throws RecipeManagerException;
    public void query() throws RecipeManagerException;
    public void scan() throws RecipeManagerException;
    public void update() throws RecipeManagerException;
    public void  delete(Map<String, String> keys) throws RecipeManagerException;

}
