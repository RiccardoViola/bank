package com.example.bank.service;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.FabrickTransactionsDto;
import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.external.api.FabrickExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankService {

    private final FabrickExternalService externalService;

    public FabrickBalanceDto getBalance(String userId, String authSchema, String timeZone) {
        return externalService.getBalance(userId, authSchema, timeZone);
    }

    public void createTransfer(String userId, String authSchema, String timeZone, PaymentRequestBody body) {
        externalService.createTransfer(userId, authSchema, timeZone, body);
    }

    public FabrickTransactionsDto getTransactions(String userId, String authSchema, String timeZone, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        FabrickTransactionsDto transactions = externalService.getTransactions(userId, authSchema, timeZone, fromAccountingDate, toAccountingDate);
        return transactions;
    }
}