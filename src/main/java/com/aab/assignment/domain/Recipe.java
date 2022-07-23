package com.aab.assignment.domain;

import java.util.List;

public class Recipe {

    public String type;
    public String name;

    public Integer serves;
    public List<String> ingredients;
    public StringBuffer instructions;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServes() {
        return serves;
    }

    public void setServes(Integer serves) {
        this.serves = serves;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public StringBuffer getInstructions() {
        return instructions;
    }

    public void setInstructions(StringBuffer instructions) {
        this.instructions = instructions;
    }

}
