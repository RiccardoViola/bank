package com.example.bank.service;

import com.example.bank.model.Transaction;
import com.example.bank.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankService {

    private final BankRepository repository;

    public Double getBalance() {
        return 1.5;
    }

    public void createTransfer() {

    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>();
    }
}