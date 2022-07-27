package  com.aab.assignment.domain.validatation.custom;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = KeyConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KeyConstraint {
    String message() default "Name and Type of recipe is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}