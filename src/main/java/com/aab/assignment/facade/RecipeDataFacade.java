package com.aab.assignment.facade;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aab.assignment.exception.BadRequestException;
import com.aab.assignment.exception.RecipeManagerException;
import com.aab.assignment.exception.RecipeNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.ItemCollectionSizeLimitExceededException;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.PutItemExpressionSpec;

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
        } catch (ConditionalCheckFailedException | ItemCollectionSizeLimitExceededException
                | ProvisionedThroughputExceededException | ResourceNotFoundException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        } catch (Exception ex) {
            log.error("Internal server error.");
            throw new RecipeManagerException(ex.getMessage());
        }

    }

    private PutItemSpec createAddItemExpressionSpec(String request) {
        PutItemExpressionSpec PUT_ITEM_EXPRESSION_SPEC = new ExpressionSpecBuilder()
                .withCondition(ExpressionSpecBuilder.S("name").notExists())
                .withCondition(ExpressionSpecBuilder.S("type").notExists())
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
        } catch (ConditionalCheckFailedException | ItemCollectionSizeLimitExceededException
                | ProvisionedThroughputExceededException | ResourceNotFoundException e) {
            e.printStackTrace();
            throw new RecipeNotFoundException(e.getMessage());
        } catch (Exception ex) {
            log.error("Internal server error.");
            throw new RecipeManagerException(ex.getMessage());
        }

    }

    private DeleteItemRequest createDeleteItemRequest(Map<String, AttributeValue> keys) {

        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setTableName(TABLE);
        deleteItemRequest.setKey(keys);

        String conditionExpression = "attribute_exists(#name) And attribute_exists(#type)";
        deleteItemRequest.setConditionExpression(conditionExpression);

        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
        expressionAttributeNames.put("#name", "name");
        expressionAttributeNames.put("#type", "type");
        deleteItemRequest.setExpressionAttributeNames(expressionAttributeNames);

        return deleteItemRequest;
    }

    @Override
    public void query() throws RecipeManagerException {
        // TODO Auto-generated method stub

    }

    @Override
    public void scan() throws RecipeManagerException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() throws RecipeManagerException {
        // TODO Auto-generated method stub

    }

}
