package com.example.bank.dto.fabrick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class FabrickPaymentDto {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("availableBalance")
    private BigDecimal availableBalance;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("moneyTransferId")
    private String moneyTransferId;
    @JsonProperty("cro")
    private String cro;
    @JsonProperty("trn")
    private String trn;
    @JsonProperty("status")
    private String status;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("debtor")
    private UserInfo debtor;
    @JsonProperty("creditor")
    private UserInfo creditor;
    @JsonProperty("feeAccountId")
    private String feeAccountId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("createdDatetime")
    private LocalDate createdDatetime;
    @JsonProperty("accountedDatetime")
    private LocalDate accountedDatetime;
    @JsonProperty("debtorValueDate")
    private LocalDate debtorValueDate;
    @JsonProperty("creditorValueDate")
    private LocalDate creditorValueDate;
    @JsonProperty("amount")
    private AmountInfo amount;
    @JsonProperty("isUrgent")
    private Boolean isUrgent;
    @JsonProperty("isInstant")
    private Boolean isInstant;
    @JsonProperty("feeType")
    private String feeType;
    @JsonProperty("fees")
    private ArrayList<FeeInfo> fees;
    @JsonProperty("hasTaxRelief")
    private Boolean hasTaxRelief;

    @Data
    public static class UserInfo {
        @JsonProperty("name")
        private String name;
        @JsonProperty("account")
        private AccountInfo account;

        @Data
        public static class AccountInfo {
            @JsonProperty("accountCode")
            private String accountCode;
            @JsonProperty("bicCode")
            private String bicCode;
        }
    }

    @Data
    public static class FeeInfo {
        @JsonProperty("feeCode")
        private String feeCode;
        @JsonProperty("description")
        private String description;
        @JsonProperty("amount")
        private BigDecimal amount;
        @JsonProperty("currency")
        private String currency;
    }

    @Data
    public static class AmountInfo {
        @JsonProperty("debtorAmount")
        private BigDecimal debtorAmount;
        @JsonProperty("debtorCurrency")
        private String debtorCurrency;
        @JsonProperty("creditorAmount")
        private BigDecimal creditorAmount;
        @JsonProperty("creditorCurrency")
        private String creditorCurrency;
        @JsonProperty("creditorCurrencyDate")
        private String creditorCurrencyDate;
        @JsonProperty("currencyRatio")
        private BigDecimal currencyRatio;
    }
}