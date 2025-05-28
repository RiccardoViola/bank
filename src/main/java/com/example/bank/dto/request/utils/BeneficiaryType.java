package com.example.bank.dto.request.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BeneficiaryType {
    NATURAL_PERSON("NATURAL_PERSON"),
    LEGAL_PERSON("LEGAL_PERSON");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BeneficiaryType fromValue(String value) {
        for (BeneficiaryType feee: values()) {
            if (feee.value.equalsIgnoreCase(value)) {
                return feee;
            }
        }
        throw new IllegalArgumentException("Value not valid for BeneficiaryType: " + value);
    }
}
