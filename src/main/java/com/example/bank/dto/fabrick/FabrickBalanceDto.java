package com.example.bank.dto.fabrick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FabrickBalanceDto {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("availableBalance")
    private BigDecimal availableBalance;
    @JsonProperty("currency")
    private String currency;
}