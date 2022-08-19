package com.aab.assignment.domain;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;

import java.util.HashMap;
import java.util.Map;

public class RecipeQueryRequest extends QueryRequest {


    public static final class RecipeQueryRequestBuilder {
        private String tableName;
        private String name;
        private Boolean consistentRead;
        private Boolean scanIndexForward;
        private Map<String, String> expressionAttributeNames;
        private Map<String, AttributeValue> expressionAttributeValues;

        private RecipeQueryRequestBuilder() {
        }

        public static RecipeQueryRequestBuilder aRecipeQueryRequest() {
            return new RecipeQueryRequestBuilder();
        }

        public RecipeQueryRequestBuilder withTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public RecipeQueryRequestBuilder withName(String name) {
            this.name = name;
            return this;
        }


        public RecipeQueryRequest build() {
            RecipeQueryRequest recipeQueryRequest = new RecipeQueryRequest();
            recipeQueryRequest.setTableName(tableName);
            recipeQueryRequest.setConsistentRead(false);
            recipeQueryRequest.setScanIndexForward(false);
            recipeQueryRequest.setKeyConditionExpression("#name = :name");
            recipeQueryRequest.setExpressionAttributeNames(new HashMap<String, String>(){{
                put("#name", "name");
            }});
            recipeQueryRequest.setExpressionAttributeValues(new HashMap<String, AttributeValue>(){{
                put(":name", new AttributeValue(name));
            }});
            return recipeQueryRequest;
        }
    }
}
