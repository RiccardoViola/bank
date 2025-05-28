package com.example.bank.service;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.FabrickTransactionsDto;
import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.external.api.FabrickExternalService;
import com.example.bank.model.Transaction;
import com.example.bank.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankService {

    @Autowired
    private FabrickExternalService externalService;
    @Autowired
    public BankRepository bankRepository;

    public FabrickBalanceDto getBalance(String userId, String authSchema, String timeZone) {
        return externalService.getBalance(userId, authSchema, timeZone);
    }

    public void createTransfer(String userId, String authSchema, String timeZone, PaymentRequestBody body) {
        externalService.createTransfer(userId, authSchema, timeZone, body);
    }

    public void insertTransactions(FabrickTransactionsDto response) {
        List<Transaction> entityList = response.getList().stream().map(dto -> {
            Transaction t = new Transaction();
            t.setTransactionId(dto.getTransactionId());
            t.setOperationId(dto.getOperationId());
            t.setAccountingDate(dto.getAccountingDate());
            t.setValueDate(dto.getValueDate());
            t.setAmount(dto.getAmount());
            t.setCurrency(dto.getCurrency());
            t.setDescription(dto.getDescription());
            if (dto.getType() != null) {
                t.setTypeEnumeration(dto.getType().getEnumeration());
                t.setTypeValue(dto.getType().getValue());
            }
            return t;
        }).toList();

        bankRepository.saveAll(entityList);
    }

    public FabrickTransactionsDto getTransactions(String userId, String authSchema, String timeZone, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        FabrickTransactionsDto transactions = externalService.getTransactions(userId, authSchema, timeZone, fromAccountingDate, toAccountingDate);

        log.info("Start to insert records in H2 database");
        this.insertTransactions(transactions);
        log.info("End to insert records in H2 database");

        return transactions;
    }
}