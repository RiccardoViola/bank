package com.example.bank.dto.request.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeeType {
    SHA("SHA"),
    OUR("OUR"),
    BEN("BEN");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static FeeType fromValue(String value) {
        for (FeeType feee: values()) {
            if (feee.value.equalsIgnoreCase(value)) {
                return feee;
            }
        }
        throw new IllegalArgumentException("Value not valid for FeeType: " + value);
    }
}