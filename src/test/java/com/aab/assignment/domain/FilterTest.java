package com.aab.assignment.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class FilterTest {

    @Test
    public void testFilter(){
        Map<String, String> filter = new HashMap<String, String>(){{
            put("minimum-serves","2");
            put("type","veg");
            put("instructions-contains","boil");
        }};
        Filter f = new Filter(filter);
        Assert.assertEquals(f.getExpression(), "#0serves >= :0serves and contains(#1instructions,:1instructions) and #2type = :2type");
        Assert.assertEquals(f.getAttributeNames().size(), 3);
        Assert.assertEquals(f.getAttributeValues().size(), 3);

        Map<String, String> filter2 = new HashMap<String, String>(){{
            put("maximum-serves","2");
            put("type","veg");
            put("instructions-not-contains","boil");
            put("ingredients-not-contains","carrot");
        }};
        f = new Filter(filter2);
        Assert.assertEquals(f.getExpression(), "NOT (contains(#0instructions,:0instructions)) and NOT (contains(#1ingredients,:1ingredients)) and #2serves <= :2serves and #3type = :3type");
        Assert.assertEquals(f.getAttributeNames().size(), 4);
        Assert.assertEquals(f.getAttributeValues().size(), 4);



    }

    @Test
    public void testMultiParamFilter(){
        Map<String, String> filter = new HashMap<String, String>(){{
            put("minimum-serves","2");
            put("type","veg");
            put("ingredients-contains","tomato,cucumber,kiwi");
        }};
        Filter f = new Filter(filter);
        Assert.assertEquals(f.getExpression(), "#0serves >= :0serves and contains(#1ingredients,:1ingredients) and contains(#2ingredients,:2ingredients) and contains(#3ingredients,:3ingredients) and #4type = :4type");
        Assert.assertEquals(f.getAttributeNames().size(), 5);
        Assert.assertEquals(f.getAttributeValues().size(), 5);





    }
}
