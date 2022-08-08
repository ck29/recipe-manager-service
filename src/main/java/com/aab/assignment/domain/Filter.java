package com.aab.assignment.domain;

import com.aab.assignment.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Filter {
    
    private String expression;
    private Map<String, String> attributeNames;
    private Map<String, Object> attributeValues;



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

    public Map<String, String> getFilterConditions() {
        return filterConditions;
    }

    public void setFilterConditions(Map<String, String> filterConditions) {
        this.filterConditions = filterConditions;
    }

    private Map<String, String> filterConditions;
    
    public Filter(){
        
    }
    
    public Filter(Map<String, String> queryFilter){
        createFilter(queryFilter);
    }

    private void createFilter(Map<String, String> filterQuery){
        if(filterQuery!=null && !filterQuery.isEmpty()){
            if(IsValidFilterList(filterQuery)){
                StringBuilder expression = new StringBuilder();
                Map<String, String> attributeNames = new HashMap<>();
                Map<String, Object> attributeValues = new HashMap<>();

                boolean isFirst = true;
                for(Map.Entry<String, String> entry : filterQuery.entrySet()){
                    String groupName = AllowedFilters.getGroupByKey(entry.getKey());
                    String value = entry.getValue();

                    attributeNames.put("#" + groupName, groupName);
                    attributeValues.put(":" + groupName, StringUtils.isNumeric(value) ? Integer.valueOf(value) : value);

                    if(!isFirst){
                        expression.append(" and ");
                    }
                    expression.append(createExpression(groupName, entry.getKey()));
                    isFirst = false;
                }
                this.expression = expression.toString();
                this.attributeNames = attributeNames;
                this.attributeValues = attributeValues;

            }else{
                throw new BadRequestException("Invalid query paramters.");
            }
        }
    }

    private String createExpression(String group, String key) {
        String operator = getOperator(key);
        if(operator.equals("not-contains")){
            return "NOT (contains(#" + group + "," + ":" + group + "))";
        }else if(operator.equals("contains")){
            return "contains(#" + group + "," + ":" + group + ")";
        }else{
            return "#" + group + " " + operator + " " + ":" + group;
        }
    }

    private String getOperator(String key) {
        if (key.contains("minimum")) {
            return ">=";
        } else if (key.contains("maximum")) {
            return "<=";
        } else if (key.contains("not-contains")) {
            return "not-contains";
        } else if (key.contains("contains")) {
            return "contains";
        } else {
            return "=";
        }
    }


    private boolean IsValidFilterList(Map<String, String> filterQuery) {
        Map<String, String> filterQueryCopy = new HashMap<>(filterQuery);
        List<String> allowed = AllowedFilters.getAllowedFilters();
        filterQueryCopy.keySet().retainAll(allowed);
        return filterQueryCopy.size() == filterQuery.size();
    }


    public static void main(String[] args) {
        Map<String, String> m = new HashMap<>();
        m.put("type","salad");
        m.put("minimum-serves","2");
        m.put("ingredients-contains","tomato");
        m.put("instructions-not-contains","oven");
//        m.put("name","oven");
        Filter f = new Filter(m);
    }
}
