package com.aab.assignment.domain.validatation.custom;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.aab.assignment.domain.Recipe;

public class KeyConstraintValidator implements ConstraintValidator<KeyConstraint, Map<String, Recipe>>{

    @Override
    public boolean isValid(Map<String, Recipe> request, ConstraintValidatorContext context) {
        Recipe existingRecipe = request.get("existing");
        if(existingRecipe!= null){
            if(StringUtils.isNotEmpty(existingRecipe.getType().trim()) && StringUtils.isNotEmpty(existingRecipe.getName().trim())){
                return true;
            }
        }
        return false;
    }
    
}
