package com.example.bank.dto.request.custom.annotation;

import com.example.bank.dto.request.custom.validator.ExecutionDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExecutionDateValidator.class)
public @interface ValidExecutionDate {
    String message() default "If isInstant is false then executionDate must exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}