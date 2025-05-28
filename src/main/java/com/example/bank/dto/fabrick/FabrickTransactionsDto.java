package com.example.bank.dto.fabrick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FabrickTransactionsDto {
    @JsonProperty("list")
    private List<FabrickTransaction> list;

    @Data
    public static class FabrickTransaction {
        @JsonProperty("transactionId")
        private String transactionId;
        @JsonProperty("operationId")
        private String operationId;
        @JsonProperty("accountingDate")
        private LocalDate accountingDate;
        @JsonProperty("valueDate")
        private LocalDate valueDate;
        @JsonProperty("type")
        private FabrickTransactionType type;
        @JsonProperty("amount")
        private BigDecimal amount;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("description")
        private String description;

        public static class FabrickTransactionType {
            @JsonProperty("enumeration")
            private String enumeration;
            @JsonProperty("value")
            private String value;
        }
    }
}