package com.aab.assignment.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.aab.assignment.exception.RecipeManagerException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;


@Component
public class RecipeDataFacade extends DataFacade{

    Logger log = LoggerFactory.getLogger(RecipeDataFacade.class);

    @Override
     public void createItem(String request) throws RecipeManagerException{
       try{
           Table recipeTable = client.getDynamodbClient().getTable(TABLE);
           recipeTable.putItem(Item.fromJSON(request));
       }
       catch(AmazonClientException e){
            e.printStackTrace();
            throw new RecipeManagerException(e.getMessage());
       }
         
     }
      
     @Override
     public void delete() throws RecipeManagerException{
         // TODO Auto-generated method stub
         
     }

     @Override
     public void query() throws RecipeManagerException{
         // TODO Auto-generated method stub
         
     }

     @Override
     public void scan() throws RecipeManagerException{
         // TODO Auto-generated method stub
         
     }

     @Override
     public void update() throws RecipeManagerException{
         // TODO Auto-generated method stub
         
     }

    
}
