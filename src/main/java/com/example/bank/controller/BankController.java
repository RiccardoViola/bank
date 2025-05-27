package com.example.bank.controller;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.model.Transaction;
import com.example.bank.service.BankService;
import com.example.bank.util.ErrorMassageConstants;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService service;

    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<FabrickBalanceDto> getBalance(
            @PathVariable @Pattern(regexp="\\d+", message = ErrorMassageConstants.USER_ID_DIGITS_CHECK) String userId,
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("X-Time-Zone") String timeZone
    ) {
        log.info("GET getBalance with userId: {}", userId);
        return ResponseEntity.ok(service.getBalance(userId, authSchema, timeZone));
    }

    @PostMapping("/user/{userId}/payment")
    public void sendPayment(
            @PathVariable @Pattern(regexp="\\d+", message = ErrorMassageConstants.USER_ID_DIGITS_CHECK) String userId,
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("X-Time-Zone") String timeZone
    ) {
        service.createTransfer(userId);
    }

    @GetMapping("/user/{userId}/transactions")
    public List<Transaction> getTransactions(
            @PathVariable @Pattern(regexp="\\d+", message = ErrorMassageConstants.USER_ID_DIGITS_CHECK) String userId,
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("X-Time-Zone") String timeZone
    ) {
        return service.getTransactions(userId);
    }
}