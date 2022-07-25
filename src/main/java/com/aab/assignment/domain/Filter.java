package com.aab.assignment.domain;

import java.util.Map;

public class Filter {
    public String expression;
    public Map<String, String> attributeNames;
    public Map<String, Object> attributeValues;
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public Map<String, String> getAttributeNames() {
        return attributeNames;
    }
    public void setAttributeNames(Map<String, String> attributeNames) {
        this.attributeNames = attributeNames;
    }
    public Map<String, Object> getAttributeValues() {
        return attributeValues;
    }
    public void setAttributeValues(Map<String, Object> attributeValues) {
        this.attributeValues = attributeValues;
    }
    
    
}
