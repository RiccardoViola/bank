package com.example.bank.dto.request.custom.validator;

import com.example.bank.dto.request.custom.annotation.ValidNotPastDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NotPastDateValidator implements ConstraintValidator<ValidNotPastDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.isBefore(LocalDate.now());
    }
}
