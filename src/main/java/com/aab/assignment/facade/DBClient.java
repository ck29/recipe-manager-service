package com.aab.assignment.facade;

import org.springframework.stereotype.Component;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Component
public class DBClient {

    private AmazonDynamoDB client;

    public AmazonDynamoDB getClient() {
        return client;
    }

    public DBClient() {
        this.client = createDynamoDbClient();
    }

    private AmazonDynamoDB createDynamoDbClient() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "localhost"))
                .build();
    }
}
