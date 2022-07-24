package com.aab.assignment.facade;

import org.springframework.stereotype.Component;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Component
public class DBClient {

    private AmazonDynamoDB client;

    public AmazonDynamoDB getClient() {
        return client;
    }

    public DynamoDB getDynamodbClient(){
        return new DynamoDB(this.client);
    }

    public DBClient() {
        this.client = createDynamoDbClient();
    }

    private AmazonDynamoDB createDynamoDbClient() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:9000", "localhost"))
                .build();
    }
}
