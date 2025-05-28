package com.example.bank.dto.request.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaxReliefIdType {
    SUPER_BONUS("119R"),
    SISMA_BONUS("DL50"),
    RISP_ENERG("L296"),
    RISTRUT("L449"),
    BAR_ARCH("L234");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TaxReliefIdType fromValue(String value) {
        for (TaxReliefIdType feee: values()) {
            if (feee.value.equalsIgnoreCase(value)) {
                return feee;
            }
        }
        throw new IllegalArgumentException("Value not valid for TaxReliefIdType: " + value);
    }
}
