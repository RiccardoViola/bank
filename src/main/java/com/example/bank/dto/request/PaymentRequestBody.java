package com.example.bank.dto.request;

import com.example.bank.dto.request.custom.annotation.ValidExecutionDate;
import com.example.bank.dto.request.custom.annotation.ValidNotPastDate;
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
    @Valid
    @NotNull
    @JsonProperty("creditor")
    Creditor creditor;
    @Valid
    @ValidNotPastDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("executionDate")
    private LocalDate executionDate;
    @JsonProperty("uri")
    private String uri;
    @Valid
    @NotNull
    @Size(max = 140)
    @JsonProperty("description")
    private String description;
    @Valid
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = ErrorMassageConstants.DIGITS_CHECK)
    @JsonProperty("amount")
    private BigDecimal amount;
    @Valid
    @NotNull
    @JsonProperty("currency")
    private String currency;
    @Valid
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("isUrgent")
    private Boolean isUrgent = false;
    @Valid
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("isInstant")
    private Boolean isInstant = false;
    @Valid
    @JsonProperty("feeType")
    private FeeType feeType;
    @JsonProperty("feeAccountId")
    private String feeAccountId;
    @Valid
    @JsonProperty("taxRelief")
    private TaxRelief taxRelief;

    @Data
    public static class Creditor {
        @Valid
        @NotNull
        @Size(max = 70)
        @JsonProperty("name")
        private String name;
        @Valid
        @NotNull
        @JsonProperty("account")
        private Account account;

        @Data
        public static class Account {
            @Valid
            @NotNull
            @JsonProperty("accountCode")
            @Pattern(regexp = "\\w+", message = ErrorMassageConstants.ALPHANUMERIC_CHECK)
            private String accountCode;
            @JsonProperty("bicCode")
            private String bicCode;
            @Valid
            @JsonProperty("address")
            private Address address;

            @Data
            public static class Address {
                @Valid
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
        @Valid
        @JsonProperty("taxReliefId")
        private TaxReliefIdType taxReliefId;
        @Valid
        @NotNull
        @JsonProperty("isCondoUpgrade")
        private Boolean isCondoUpgrade;
        @Valid
        @NotNull
        @JsonProperty("creditorFiscalCode")
        private String creditorFiscalCode;
        @Valid
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
            @Valid
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
            @Valid
            @NotNull
            @JsonProperty("fiscalCode")
            private String fiscalCode;
            @JsonProperty("legalRepresentativeFiscalCode")
            private String legalRepresentativeFiscalCode;
        }
    }
}