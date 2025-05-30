package com.example.bank.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ErrorMassageConstants {
    public static final String USER_ID_DIGITS_CHECK = "The userId must have only digits";
    public static final String DIGITS_CHECK = "It must contain at most 2 digits";
    public static final String ALPHANUMERIC_CHECK = "It must be alphanumeric";
}