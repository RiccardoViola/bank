package com.example.bank.repository;

import com.example.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Transaction, String> {

}