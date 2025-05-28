package com.example.bank.dto.request.custom.validator;

import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.dto.request.custom.annotation.ValidExecutionDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExecutionDateValidator implements ConstraintValidator<ValidExecutionDate, PaymentRequestBody> {

    @Override
    public boolean isValid(PaymentRequestBody body, ConstraintValidatorContext context) {
        boolean valid = true;

        if (!body.getIsInstant()) {
            valid = body.getExecutionDate() != null;

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("executionDate must exist if isInstant is false")
                        .addPropertyNode("executionDate")
                        .addConstraintViolation();
            }
        }

        return valid;
    }
}