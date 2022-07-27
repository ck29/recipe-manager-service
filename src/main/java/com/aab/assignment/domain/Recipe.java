package com.aab.assignment.domain;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aab.assignment.domain.validatation.groups.AddRecipeValidateGroup;
import com.aab.assignment.domain.validatation.groups.DeleteRecipeValidateGroup;
import com.aab.assignment.domain.validatation.groups.UpdateRecipeValidateGroup;

public class Recipe {

    @NotBlank(groups = { AddRecipeValidateGroup.class, DeleteRecipeValidateGroup.class,
            UpdateRecipeValidateGroup.class }, message = "Recipe type cannot be empty")
    public String type;

    @NotBlank(groups = { AddRecipeValidateGroup.class, DeleteRecipeValidateGroup.class,
            UpdateRecipeValidateGroup.class }, message = "Recipe cannot be empty")
    public String name;

    @NotNull(groups = { AddRecipeValidateGroup.class,
        UpdateRecipeValidateGroup.class }, message = "Number of serving is required.")
    public Integer serves;

    @NotEmpty(groups = { AddRecipeValidateGroup.class, 
        UpdateRecipeValidateGroup.class}, message = "List of ingredients are required.")
    public List<String> ingredients;

    @NotBlank(groups = { AddRecipeValidateGroup.class,
        UpdateRecipeValidateGroup.class }, message = "Recipe instructions are required.")
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        // null
        if (obj == null) {
            return false;
        }
        // type
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        // cast and compare state
        Recipe other = (Recipe) obj;
        return name.equals(other.name) 
                && type.equals(other.type)
                && isIngredientsEqual(ingredients, other.ingredients) 
                && instructions.toString().equals(other.instructions.toString());
    }

    private boolean isIngredientsEqual(List<String> ingredients2, List<String> ingredients3) {
        
        if(ingredients2!=null && ingredients3!=null){
            Collections.sort(ingredients2);
            Collections.sort(ingredients3);
            return ingredients2.equals(ingredients3);
        }
        return false;
        
    }

}
