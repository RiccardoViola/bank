package com.example.bank.dto.request;

import com.example.bank.dto.request.custom.annotation.ValidExecutionDate;
import com.example.bank.dto.request.custom.annotation.ValidTaxRelief;
import com.example.bank.dto.request.utils.BeneficiaryType;
import com.example.bank.dto.request.utils.FeeType;
import com.example.bank.dto.request.utils.TaxReliefIdType;
import com.example.bank.util.ErrorMassageConstants;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ValidExecutionDate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequestBody {
    @NotNull
    @JsonProperty("creditor")
    Creditor creditor;
    @Valid
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("executionDate")
    private LocalDate executionDate;
    @JsonProperty("uri")
    private String uri;
    @NotNull
    @Size(max = 140)
    @JsonProperty("description")
    private String description;
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
    @JsonProperty("amount")
    private BigDecimal amount;
    @NotNull
    @JsonProperty("currency")
    private String currency;
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("isUrgent")
    private Boolean isUrgent = false;
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("isInstant")
    private Boolean isInstant = false;
    @JsonProperty("feeType")
    private FeeType feeType;
    @JsonProperty("feeAccountId")
    private String feeAccountId;
    @JsonProperty("taxRelief")
    private TaxRelief taxRelief;

    @Data
    public static class Creditor {
        @NotNull
        @Size(max = 70)
        @JsonProperty("name")
        private String name;
        @NotNull
        @JsonProperty("account")
        private Account account;

        @Data
        public static class Account {
            @NotNull
            @JsonProperty("accountCode")
            @Pattern(regexp = "\\w+", message = ErrorMassageConstants.ALPHANUMERIC_CHECK)
            private String accountCode;
            @JsonProperty("bicCode")
            private String bicCode;
            @JsonProperty("address")
            private Address address;

            @Data
            public static class Address {
                @Size(max = 40)
                @JsonProperty("address")
                private String address;
                @JsonProperty("city")
                private String city;
                @JsonProperty("countryCode")
                private String countryCode;
            }
        }
    }

    @Data
    @ValidTaxRelief
    public static class TaxRelief {
        @JsonProperty("taxReliefId")
        private TaxReliefIdType taxReliefId;
        @NotNull
        @JsonProperty("isCondoUpgrade")
        private Boolean isCondoUpgrade;
        @NotNull
        @JsonProperty("creditorFiscalCode")
        private String creditorFiscalCode;
        @NotNull
        @JsonProperty("beneficiaryType")
        private BeneficiaryType beneficiaryType;
        @Valid
        @JsonProperty("naturalPersonBeneficiary")
        private NaturalPersonBeneficiary naturalPersonBeneficiary;
        @Valid
        @JsonProperty("legalPersonBeneficiary")
        private LegalPersonBeneficiary legalPersonBeneficiary;

        @Data
        public static class NaturalPersonBeneficiary {
            @NotNull
            @JsonProperty("fiscalCode1")
            private String fiscalCode1;
            @JsonProperty("fiscalCode2")
            private String fiscalCode2;
            @JsonProperty("fiscalCode3")
            private String fiscalCode3;
            @JsonProperty("fiscalCode4")
            private String fiscalCode4;
            @JsonProperty("fiscalCode5")
            private String fiscalCode5;
        }

        @Data
        public static class LegalPersonBeneficiary {
            @NotNull
            @JsonProperty("fiscalCode")
            private String fiscalCode;
            @JsonProperty("legalRepresentativeFiscalCode")
            private String legalRepresentativeFiscalCode;
        }
    }
}