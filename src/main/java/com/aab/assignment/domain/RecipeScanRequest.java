package com.aab.assignment.domain;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class RecipeScanRequest extends ScanRequest {

    public RecipeScanRequest() {

    }

    public static final class RecipeScanRequestBuilder {
        private String tableName;
        private Filter filter;

        private RecipeScanRequestBuilder() {
        }

        public static RecipeScanRequestBuilder aRecipeScanRequest() {
            return new RecipeScanRequestBuilder();
        }

        public RecipeScanRequestBuilder withTable(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public RecipeScanRequestBuilder withFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public ScanRequest build() throws JsonProcessingException {
            ScanRequest recipeScanRequest = new RecipeScanRequest();
            recipeScanRequest.setTableName(this.tableName);
            recipeScanRequest.setConsistentRead(false);
            if (filter != null) {
                recipeScanRequest.setFilterExpression(filter.getExpression()); // Can return null
                recipeScanRequest.setExpressionAttributeNames(this.getExpressionAttributeNames(filter)); // can return
                                                                                                         // null
                recipeScanRequest.setExpressionAttributeValues(this.getExpressionAttributeValues(filter)); // can return
                // null
            }
            return recipeScanRequest;
        }

        private Map<String, AttributeValue> getExpressionAttributeValues(Filter _filter)
                throws JsonProcessingException {
            if (_filter != null) {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(_filter.getAttributeValues());
                Item item = new Item().withJSON("document", json);
                Map<String, AttributeValue> attributes = ItemUtils.toAttributeValues(item);
                return attributes.get("document").getM();

            }
            return null;
        }

        private Map<String, String> getExpressionAttributeNames(Filter _filter) {
            if (_filter != null) {
                return _filter.getAttributeNames();
            }
            return null;
        }

    }
}
