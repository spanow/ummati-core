package com.ummati.ummati_core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinCurrentDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinCurrentDate {

    String message() default "The date must be in the present or future.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
