package com.aab.assignment.facade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.junit4.SpringRunner;

import com.aab.assignment.exception.RecipeManagerException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;

@RunWith(SpringRunner.class)
public class RecipeDataFacadeTest {

    @InjectMocks
    private RecipeDataFacade facade;

    @Mock
    private DBClient client;

    @BeforeEach
    public void setUp(){
        facade = spy(RecipeDataFacade.class);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateItem() throws RecipeManagerException {
        Table mockTable = Mockito.mock(Table.class);
        DynamoDB dd = Mockito.mock(DynamoDB.class);

        when(client.getDynamodbClient()).thenReturn(dd);
        when(dd.getTable(Mockito.anyString())).thenReturn(mockTable);
        when(mockTable.putItem((PutItemSpec)notNull())).thenReturn((PutItemOutcome)notNull());

        try{
            facade.createItem("{}");
            assert(true);
        }catch(Exception e){
            assert(false);
        }
    }

    @Test
    public void testCreateItemEmptyRequest() throws RecipeManagerException {
        Table mockTable = Mockito.mock(Table.class);
        DynamoDB dd = Mockito.mock(DynamoDB.class);

        when(client.getDynamodbClient()).thenReturn(dd);
        when(dd.getTable(Mockito.anyString())).thenReturn(mockTable);
        when(mockTable.putItem((PutItemSpec)notNull())).thenReturn((PutItemOutcome)notNull());

        try{
            facade.createItem("");
            assert(false);
        }catch(RecipeManagerException e){
            assert(true);
        }catch(Exception ex){
            assert(false);
        }
    }

    @Test
    public void testDelete() {
        Map<String, String> request = new HashMap<>();
        request.put("name", "salad");
        request.put("type", "veg");

        AmazonDynamoDB dd = Mockito.mock(AmazonDynamoDB.class);

        when(client.getClient()).thenReturn(dd);
        when(dd.deleteItem((DeleteItemRequest)notNull())).thenReturn((DeleteItemResult)notNull());

        try{
            facade.delete(request);
            assert(true);
        }catch(Exception e){
            assert(false);
        }

    }

    @Test
    public void testDeleteInvalid() {
        Map<String, String> request = new HashMap<>();

        AmazonDynamoDB dd = Mockito.mock(AmazonDynamoDB.class);

        when(client.getClient()).thenReturn(dd);
        when(dd.deleteItem((DeleteItemRequest)notNull())).thenReturn((DeleteItemResult)notNull());

        try{
            facade.delete(request);
            assert(false);
        }catch(RecipeManagerException e){
            assert(true);
        }

    }

    @Test
    void testScan() {

    }

    @Test
    void testScan2() {

    }
}
