package com.aab.assignment.facade;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.domain.RecipeQueryRequest;
import com.aab.assignment.domain.RecipeScanRequest.RecipeScanRequestBuilder;
import com.aab.assignment.domain.RecipeQueryRequest.RecipeQueryRequestBuilder;
import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeAlreadyExistsException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.exception.RecipeNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.PutItemExpressionSpec;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RecipeDataFacade extends DataFacade {

    Logger log = LoggerFactory.getLogger(RecipeDataFacade.class);

    @Override
    public void createItem(String request) throws RecipeManagerException {

        if (StringUtils.isEmpty(request)) {
            throw new RecipeManagerException("Cannot proceed with empty request.");
        }

        try {
            PutItemSpec putItemSpec = createAddItemExpressionSpec(request);
            Table recipeTable = client.getDynamodbClient().getTable(TABLE);
            recipeTable.putItem(putItemSpec);

        } catch (AmazonServiceException e) {
            log.error(e.getMessage());

            if(e instanceof ResourceNotFoundException){
                throw new RecipeManagerException(e.getMessage());
            }
            throw new RecipeAlreadyExistsException(e.getMessage());
        } catch (AmazonClientException ex) {
            throw new RecipeManagerException(ex.getMessage());
        }

    }

    private PutItemSpec createAddItemExpressionSpec(String request) {
        PutItemExpressionSpec PUT_ITEM_EXPRESSION_SPEC = new ExpressionSpecBuilder()
                .withCondition(ExpressionSpecBuilder.S("name").notExists())
                .buildForPut();

        return new PutItemSpec()
                .withItem(Item.fromJSON(request))
                .withExpressionSpec(PUT_ITEM_EXPRESSION_SPEC);
    }

    @Override
    public void delete(Map<String, String> keys) throws RecipeManagerException {
        if (keys.isEmpty()) {
            throw new RecipeManagerException("Cannot proceed with empty request.");
        }

        Map<String, AttributeValue> updateKeys = new HashMap<>();
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            updateKeys.put(entry.getKey(), new AttributeValue(entry.getValue()));
        }
        try {
            DeleteItemRequest deleteItemRequest = createDeleteItemRequest(updateKeys);
            client.getClient().deleteItem(deleteItemRequest);
        } catch (AmazonServiceException e) {
            log.error(e.getMessage());
            throw new RecipeNotFoundException();
        } catch (AmazonClientException ex) {
            ex.printStackTrace();
            throw new RecipeManagerException();
        }

    }

    private DeleteItemRequest createDeleteItemRequest(Map<String, AttributeValue> keys) {

        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setTableName(TABLE);
        deleteItemRequest.setKey(keys);

        String conditionExpression = "attribute_exists(#name)";
        deleteItemRequest.setConditionExpression(conditionExpression);

        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#name", "name");
        deleteItemRequest.setExpressionAttributeNames(expressionAttributeNames);

        return deleteItemRequest;
    }

    @Override
    public List<Map<String, Object>> scan() throws RecipeManagerException {
        ScanRequest scanRequest;
        try {
            scanRequest = RecipeScanRequestBuilder.aRecipeScanRequest()
                    .withTable(TABLE)
                    .build();
            ScanResult scanResult = client.getClient().scan(scanRequest);
            return getResponse(scanResult);

        } catch (AmazonServiceException e) {
            log.error(e.getMessage());
            throw new RecipeManagerException();
        } catch (JsonProcessingException | AmazonClientException ex) {
            log.error(ex.getMessage());
            throw new RecipeManagerException();
        }

    }

    private List<Map<String, Object>> getResponse(Object result) throws RecipeManagerException {
        if(result!=null){
            List<Map<String, Object>> __ = new ArrayList<>();
            List<Map<String, AttributeValue>> recList;

            if(result instanceof ScanResult){
                recList =  ((ScanResult) result).getItems();
            } else {
                recList =  ((QueryResult) result).getItems();
            }

            for (Map<String, AttributeValue> rec : recList) {
                __.add(ItemUtils.toSimpleMapValue(rec));
            }
            return __;
        }

        throw new RecipeManagerException("Error retrieving the records.");
        
    }

    @Override
    public List<Map<String, Object>> scan(Map<String, String> filter) throws BadRequestException, RecipeManagerException {

        ScanRequest scanRequest;
        try {
            scanRequest = RecipeScanRequestBuilder.aRecipeScanRequest()
                    .withTable(TABLE)
                    .withFilter(createRequestFilter(filter))
                    .build();
            ScanResult scanResult = client.getClient().scan(scanRequest);
            return getResponse(scanResult);

        } catch (AmazonServiceException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (JsonProcessingException | AmazonClientException ex) {
            log.error(ex.getMessage());
            throw new RecipeManagerException(ex.getMessage());
        }

    }

    public List<Map<String,Object>> query(String key) throws BadRequestException, RecipeManagerException {
        QueryRequest queryRequest;
        try{
            queryRequest = RecipeQueryRequestBuilder.aRecipeQueryRequest()
                    .withTableName(TABLE)
                    .withName(key)
                    .build();
            QueryResult queryResult = client.getClient().query(queryRequest);
            return getResponse(queryResult);
        } catch (AmazonServiceException e) {
            log.error(e.getMessage());
            throw new RecipeManagerException(e.getMessage());
        } catch (AmazonClientException ex) {
            log.error(ex.getMessage());
            throw new RecipeManagerException(ex.getMessage());
        }

    }

    private Filter createRequestFilter(Map<String, String> filter) {
        Filter f = null;
        if(filter!=null){
             f = new Filter(filter);
        }

        return f;

    }

}
