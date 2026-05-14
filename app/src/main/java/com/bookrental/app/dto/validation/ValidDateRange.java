package com.bookrental.app.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // Note: where to use? -> only on class or interfaces type;
@Retention(RetentionPolicy.RUNTIME) // Note: crucial because on runtime Java tends to delete comments, so -> "don't delete this let Spring use it";
@Constraint(validatedBy = DateRangeValidator.class) // Note: crucial because in DateRangeValidator.class we have the implementation of this custom annotation;
public @interface ValidDateRange { // Note: that's how we create annotations;
    String message() default "End date must be after start date";
    Class<?>[] groups() default {}; // Note: groups of classes that we use when using this annotation;
    Class<? extends Payload>[] payload() default {}; // Note: needed attribute but not used, usually for severity type of errors (INFO, WARNING, FATAL, etc..);
}
