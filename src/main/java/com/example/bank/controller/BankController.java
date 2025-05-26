package com.example.bank.controller;

import com.example.bank.model.Transaction;
import com.example.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService service;

    @GetMapping("/user/{id}/balance")
    public Double getBalance(@PathVariable String userId) {
        return service.getBalance();
    }

    @PostMapping("/user/{id}/payment")
    public void sendPayment(@PathVariable String userId) {
        return service.createTransfer();
    }

    @GetMapping("/user/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable String userId) {
        return service.getTransactions();
    }
}