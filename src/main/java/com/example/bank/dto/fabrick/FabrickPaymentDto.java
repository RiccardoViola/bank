package com.example.bank.dto.fabrick;

import com.example.bank.util.ErrorMassageConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class FabrickPaymentDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private LocalDate date;
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
    @JsonProperty("balance")
    private BigDecimal balance;
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("createdDatetime")
    private LocalDate createdDatetime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("accountedDatetime")
    private LocalDate accountedDatetime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("debtorValueDate")
    private LocalDate debtorValueDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
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
            @Pattern(regexp = "\\w+", message = ErrorMassageConstants.ALPHANUMERIC_CHECK)
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
        @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
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
        @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
        private BigDecimal creditorAmount;
        @JsonProperty("creditorCurrency")
        private String creditorCurrency;
        @JsonProperty("creditorCurrencyDate")
        private String creditorCurrencyDate;
        @JsonProperty("currencyRatio")
        private BigDecimal currencyRatio;
    }
}

/*
        "moneyTransferId": "628947333",
        "cro": "4394485470503268",
        "trn": "",
        "status": "BOOKED",
        "uri": "NOTPROVIDED",
        "direction": "OUTGOING",
        "debtor": {
            "name": "LUCA TERRIBILE",
            "account": {
                "accountCode": "IT40L0326822311052923800661",
                "bicCode": null
            }
        },
        "creditor": {
            "name": "JOHN DOE",
            "account": {
                "accountCode": "IT33K0300203280764627118497",
                "bicCode": "UNCRITMMXXX"
            }
        },
        "feeAccountId": "14537780",
        "description": "PAYMENT INVOICE 75/2017",
        "createdDatetime": "2025-05-27T20:24:38.498+0200",
        "accountedDatetime": "",
        "debtorValueDate": "2025-06-30",
        "creditorValueDate": "2025-07-01",
        "amount": {
            "debtorAmount": 800,
            "debtorCurrency": "EUR",
            "creditorAmount": 800,
            "creditorCurrency": "EUR",
            "creditorCurrencyDate": "",
            "currencyRatio": 1
        },
        "isUrgent": false,
        "isInstant": false,
        "feeType": "SHA",
        "fees": [{
          "feeCode": "MK001",
          "description": "Money transfer execution fee",
          "amount": 0.25,
          "currency": "EUR"
        }],
        "hasTaxRelief": false
*/