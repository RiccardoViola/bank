package com.example.bank.dto.fabrick;

import com.example.bank.util.ErrorMassageConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FabrickBalanceDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private LocalDate date;
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
    @JsonProperty("balance")
    private BigDecimal balance;
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
    @JsonProperty("availableBalance")
    private BigDecimal availableBalance;
    @NotBlank
    @JsonProperty("currency")
    private String currency;
}