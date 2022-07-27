package com.aab.assignment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {

    public static String toJson(Object obj) throws JsonProcessingException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws JsonMappingException, JsonProcessingException{
        return new ObjectMapper().readValue(json, clazz);
    }
}

