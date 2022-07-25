package com.aab.assignment.facade;

import java.util.List;
import java.util.Map;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.exception.RecipeManagerException;

public interface RecipeDB {
    public void createItem(String request) throws RecipeManagerException;
    public List<Map<String, Object>> scan(Filter filter) throws RecipeManagerException;
    public List<Map<String, Object>> scan() throws RecipeManagerException ;
    public void  delete(Map<String, String> keys) throws RecipeManagerException;

}
