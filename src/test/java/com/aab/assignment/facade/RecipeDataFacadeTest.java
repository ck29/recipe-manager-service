package com.aab.assignment.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.aab.assignment.domain.Filter;
import com.aab.assignment.domain.RecipeScanRequest.RecipeScanRequestBuilder;
import com.aab.assignment.exception.RecipeManagerException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(MockitoJUnitRunner.class)
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
    public void testScan() {
        AmazonDynamoDB dd = Mockito.mock(AmazonDynamoDB.class);

        ScanResult scanResult = new ScanResult();
        Map<String, Object> item1 = new HashMap<>();
                item1.put("name", "salad");
                item1.put("type", "veg");
                item1.put("ingredients", Arrays.asList("Tomato", "raddish"));
                item1.put("instructions", "cut and put");
        
        List <Map<String, AttributeValue>> items = new ArrayList<>();
        items.add(ItemUtils.fromSimpleMap(item1));
        scanResult.setItems(items);

        when(client.getClient()).thenReturn(dd);
        when(dd.scan((ScanRequest)notNull())).thenReturn(scanResult);

        try {
            List<Map<String, Object>> response =  facade.scan();
            assertEquals(response.size(), 1);
        } catch (RecipeManagerException e) {
            assert(false);
        }
    }

    @Test
    void testScanWithFilter() {

        Filter filter = new Filter();
        filter.setExpression("#type=:type And #name=:name");
        
        Map<String, String> attrName = new HashMap<>();
        attrName.put("#type", "type");
        attrName.put("#name", "name");

        Map<String, Object> attrValue = new HashMap<>();
        attrValue.put(":type","veg");
        attrValue.put(":name","salad");
        filter.setAttributeNames(attrName);
        filter.setAttributeValues(attrValue);
        
        ScanRequest scanRequest = null;
        try {
            scanRequest = RecipeScanRequestBuilder.aRecipeScanRequest()
                        .withTable("recipe")
                        .withFilter(filter)
                        .build();
        } catch (JsonProcessingException e1) {
            assert(false);
        }

        ScanResult scanResult = new ScanResult();
        Map<String, Object> item1 = new HashMap<>();
                item1.put("name", "salad");
                item1.put("type", "veg");
                item1.put("ingredients", Arrays.asList("Tomato", "raddish"));
                item1.put("instructions", "cut and put");
        
        List <Map<String, AttributeValue>> items = new ArrayList<>();
        items.add(ItemUtils.fromSimpleMap(item1));
        scanResult.setItems(items);

        AmazonDynamoDB dd = Mockito.mock(AmazonDynamoDB.class);
        when(client.getClient()).thenReturn(dd);
        when(dd.scan((ScanRequest)notNull())).thenReturn(scanResult);

        try {
            List<Map<String, Object>> response =  facade.scan(filter);
            assertEquals(response.size(), 1);
        } catch (RecipeManagerException e) {
            assert(false);
        }
    }
}
