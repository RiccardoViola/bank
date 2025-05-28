package com.example.bank.dto.request.custom.annotation;

import com.example.bank.dto.request.custom.validator.TaxReliefValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaxReliefValidator.class)
public @interface ValidTaxRelief {
    String message() default "naturalPersonBeneficiary must exist if beneficiaryType is NATURAL_PERSON ;legalPersonBeneficiary must exist if beneficiaryType = LEGAL_PERSON";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}