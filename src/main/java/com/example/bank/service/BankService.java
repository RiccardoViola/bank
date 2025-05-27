package com.example.bank.service;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.external.api.FabrickExternalService;
import com.example.bank.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankService {

    private final FabrickExternalService repository;

    public FabrickBalanceDto getBalance(String userId, String authSchema, String timeZone) {
        return repository.getBalance(userId, authSchema, timeZone);
    }

    public void createTransfer(String userId) {

    }

    public List<Transaction> getTransactions(String userId) {
        return new ArrayList<>();
    }
}