package com.aab.assignment.facade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.exception.RecipeManagerException;

abstract public class DataFacade implements RecipeDB{
    
    @Autowired
    protected DBClient client;
    protected static final String TABLE = "recipes";

    abstract public void createItem(String request) throws RecipeManagerException;
    abstract public List<Map<String, Object>> scan(Filter filter) throws RecipeManagerException;
    abstract public List<Map<String, Object>> scan() throws RecipeManagerException ;
    abstract public void  delete(Map<String, String> keys) throws RecipeManagerException;

}
