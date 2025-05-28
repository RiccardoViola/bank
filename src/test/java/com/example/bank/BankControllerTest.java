package com.example.bank;

import com.example.bank.controller.BankController;
import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.dto.request.utils.BeneficiaryType;
import com.example.bank.dto.request.utils.TaxReliefIdType;
import com.example.bank.dto.request.utils.FeeType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BankControllerTest {

    @InjectMocks
    private BankController controller;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Method findGetBalanceMethod() {
        try {
            return BankController.class.getMethod("getBalance", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Method not found", e);
        }
    }

    @Test
    void testGetBalanceValidUserIdShouldSuccessValidation() {
        String invalidUserId = "123";

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(
                        controller,
                        findGetBalanceMethod(),
                        new Object[]{invalidUserId, "S2S", "Europe/Rome"}
                );

        assertThat(violations).isEmpty();
    }

    @Test
    void testGetBalanceInvalidUserIdShouldFailValidation() {
        String invalidUserId = "abc123";

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(
                        controller,
                        findGetBalanceMethod(),
                        new Object[]{invalidUserId, "S2S", "Europe/Rome"}
                );

        assertThat(violations).isNotEmpty();
    }

    private PaymentRequestBody getValidPaymentRequestBody() {
        PaymentRequestBody body = new PaymentRequestBody();
        PaymentRequestBody.Creditor.Account.Address address = new PaymentRequestBody.Creditor.Account.Address();
        address.setAddress("Via Roma");
        address.setCity("Roma");
        address.setCountryCode("IT");

        PaymentRequestBody.Creditor.Account account = new PaymentRequestBody.Creditor.Account();
        account.setAccountCode("ABC123");
        account.setBicCode("UNCRITMMXXX");
        account.setAddress(address);

        PaymentRequestBody.Creditor creditor = new PaymentRequestBody.Creditor();
        creditor.setName("Mario Rossi");
        creditor.setAccount(account);

        PaymentRequestBody.TaxRelief.NaturalPersonBeneficiary natural = new PaymentRequestBody.TaxRelief.NaturalPersonBeneficiary();
        natural.setFiscalCode1("RSSMRA85M01H501U");

        PaymentRequestBody.TaxRelief taxRelief = new PaymentRequestBody.TaxRelief();
        taxRelief.setTaxReliefId(TaxReliefIdType.SUPER_BONUS);  // Assumi enum valido
        taxRelief.setIsCondoUpgrade(false);
        taxRelief.setCreditorFiscalCode("RSSMRA85M01H501U");
        taxRelief.setBeneficiaryType(BeneficiaryType.NATURAL_PERSON);
        taxRelief.setNaturalPersonBeneficiary(natural);

        body.setCreditor(creditor);
        body.setExecutionDate(LocalDate.now().plusDays(1));
        body.setDescription("Pagamento bolletta");
        body.setAmount(new BigDecimal("100.50"));
        body.setCurrency("EUR");
        body.setIsUrgent(false);
        body.setIsInstant(false);
        body.setFeeType(FeeType.SHA); // Assumi enum valido
        body.setFeeAccountId("IT60X0542811101000000123456");
        body.setTaxRelief(taxRelief);

        return body;
    }

    @Test
    void testSendPaymentValidUserIdShouldSuccessValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody validBody = getValidPaymentRequestBody();

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123456", "S2S", "Europe/Rome", validBody});

        assertThat(violations).isEmpty();
    }

    @Test
    void testSendPaymentInvalidCreditorNameShouldFailValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody body = getValidPaymentRequestBody();
        body.getCreditor().setName(null);

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123", "S2S", "Europe/Rome", body});

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testSendPaymentLongDescriptionShouldFailValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody body = getValidPaymentRequestBody();
        body.setDescription("x".repeat(141));

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123", "S2S", "Europe/Rome", body});

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testSendPaymentInvalidAmountPrecisionShouldFailValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody body = getValidPaymentRequestBody();
        body.setAmount(new BigDecimal("100.1234"));

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123", "S2S", "Europe/Rome", body});

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testSendPaymentMissingCurrencyShouldFailValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody body = getValidPaymentRequestBody();
        body.setCurrency(null);

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123", "S2S", "Europe/Rome", body});

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testSendPaymentExecutionDateInPastShouldFailValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody body = getValidPaymentRequestBody();
        body.setExecutionDate(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123", "S2S", "Europe/Rome", body});

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testSendPaymentMissingNaturalPersonFiscalCodeShouldFailValidation() throws Exception {
        Method method = BankController.class.getMethod("sendPayment", String.class, String.class, String.class, PaymentRequestBody.class);
        PaymentRequestBody body = getValidPaymentRequestBody();
        body.getTaxRelief().getNaturalPersonBeneficiary().setFiscalCode1(null);

        Set<ConstraintViolation<BankController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[]{"123", "S2S", "Europe/Rome", body});

        assertThat(violations).isNotEmpty();
    }
}