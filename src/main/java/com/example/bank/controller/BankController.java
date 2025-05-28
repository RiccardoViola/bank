package com.example.bank.controller;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.FabrickTransactionsDto;
import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.service.BankService;
import com.example.bank.util.ErrorMassageConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

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
    public ResponseEntity<String> sendPayment(
            @PathVariable @Pattern(regexp="\\d+", message = ErrorMassageConstants.USER_ID_DIGITS_CHECK) String userId,
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("X-Time-Zone") String timeZone,
            @Valid @RequestBody PaymentRequestBody body
    ) {
        log.info("POST sendPayment with userId: {}", userId);
        log.info("Received body {}", body);
        service.createTransfer(userId, authSchema, timeZone, body);
        return ResponseEntity.ok("Payment sent");
    }

    @GetMapping("/user/{userId}/transactions")
    public ResponseEntity<FabrickTransactionsDto> getTransactions(
            @PathVariable @Pattern(regexp="\\d+", message = ErrorMassageConstants.USER_ID_DIGITS_CHECK) String userId,
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("X-Time-Zone") String timeZone,
            @RequestParam("fromAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull LocalDate fromAccountingDate,
            @RequestParam("toAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull LocalDate toAccountingDate
            ) {
        log.info("GET getTransactions with userId: {}", userId);
        log.info("Received fromAccountingDate {}", fromAccountingDate);
        log.info("Received fromAccountingDate {}", toAccountingDate);

        if (toAccountingDate.isBefore(fromAccountingDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toAccountingDate must be after or equal to fromAccountingDate");
        }

        return ResponseEntity.ok(service.getTransactions(userId, authSchema, timeZone, fromAccountingDate, toAccountingDate));
    }
}