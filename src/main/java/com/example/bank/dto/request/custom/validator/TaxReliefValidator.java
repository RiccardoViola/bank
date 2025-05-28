package com.example.bank.dto.request.custom.validator;

import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.dto.request.custom.annotation.ValidTaxRelief;
import com.example.bank.dto.request.utils.BeneficiaryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaxReliefValidator implements ConstraintValidator<ValidTaxRelief, PaymentRequestBody.TaxRelief> {

    @Override
    public boolean isValid(PaymentRequestBody.TaxRelief taxRelief, ConstraintValidatorContext context) {
        if (taxRelief == null) return true;

        boolean valid = true;

        if (BeneficiaryType.NATURAL_PERSON.equals(taxRelief.getBeneficiaryType())) {
            valid = taxRelief.getNaturalPersonBeneficiary() != null;

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("naturalPersonBeneficiary must exist if beneficiaryType is NATURAL_PERSON")
                        .addPropertyNode("naturalPersonBeneficiary")
                        .addConstraintViolation();
            }
        } else if (BeneficiaryType.LEGAL_PERSON.equals(taxRelief.getBeneficiaryType())) {
            valid = taxRelief.getLegalPersonBeneficiary() != null;

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("legalPersonBeneficiary must exist beneficiaryType is LEGAL_PERSON")
                        .addPropertyNode("legalPersonBeneficiary")
                        .addConstraintViolation();
            }
        }

        return valid;
    }
}