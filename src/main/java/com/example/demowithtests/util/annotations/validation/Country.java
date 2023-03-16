package com.example.demowithtests.util.annotations.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
public @interface Country {
    String message() default "Invalid country format or does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}