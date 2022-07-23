package com.aab.assignment.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class Recipe {

    @NotBlank(message = "Recipe type cannot be empty")
    public String type;

    @NotBlank(message = "Recipe cannot be empty")
    public String name;

    @NotNull(message = "Number of serving is required.")
    public Integer serves;

    @NotEmpty(message = "List of ingredients are required.")
    public List<String> ingredients;

    @NotBlank(message = "Recipe instructions are required.")
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
