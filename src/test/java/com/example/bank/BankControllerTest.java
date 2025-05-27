package com.example.bank;

import com.example.bank.controller.BankController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Method;
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
}