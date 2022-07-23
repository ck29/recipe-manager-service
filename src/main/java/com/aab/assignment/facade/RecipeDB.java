package com.aab.assignment.facade;

import com.aab.assignment.exception.RecipeManagerException;

public interface RecipeDB {
    public void createItem(String request) throws RecipeManagerException;
    public void query() throws RecipeManagerException;
    public void scan() throws RecipeManagerException;
    public void update() throws RecipeManagerException;
    public void delete() throws RecipeManagerException;

}
