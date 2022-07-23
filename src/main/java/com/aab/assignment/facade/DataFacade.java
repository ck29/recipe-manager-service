package com.aab.assignment.facade;

import org.springframework.beans.factory.annotation.Autowired;

abstract public class DataFacade implements RecipeDB{
    
    @Autowired
    protected DBClient client;

    abstract public void createItem();
    abstract public void query();
    abstract public void scan();
    abstract public void update();
    abstract public void delete();

}
