package com.aab.assignment.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AllowedFilters {
    MinimumServes("minimum-serves", "serves"),
    MaximumServes("maximum-serves", "serves"),
    Type("type", "type"),
    NameContains("name-contains", "name"),
    IngredientsContains("ingredients-contains", "ingredients"),
    IngredientsNotContains("ingredients-not-contains", "ingredients"),
    InstructionsContains("instructions-contains", "instructions"),
    InstructionsNotContains("instructions-not-contains", "instructions");


    private final String key;
    private final String group;

    private static Map<String, String> parameterMap;

    AllowedFilters(String key, String group){
        this.key = key;
        this.group = group;

    }

    public static String getGroupByKey(String key){
        if(parameterMap == null){
            parameterMap = new HashMap<>();
            for (AllowedFilters s : AllowedFilters.values()){
                parameterMap.put(s.getKey(), s.getGroup());
            }
        }
        return parameterMap.get(key);
    }


    public String getKey() {
        return key;
    }
    public String getGroup() {
        return group;
    }

    public static List<String> getAllowedFilters(){
        return Stream.of(AllowedFilters.values())
                .map(AllowedFilters::getKey)
                .collect(Collectors.toList());
    }

}
