package com.example.bank;

import com.example.bank.dto.fabrick.FabrickTransactionsDto;
import com.example.bank.model.Transaction;
import com.example.bank.repository.BankRepository;
import com.example.bank.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BankServiceTest {

    @Autowired
    private BankRepository bankRepository;

    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService();
        bankService.bankRepository = bankRepository;
    }

    private FabrickTransactionsDto createDto(String id) {
        FabrickTransactionsDto.FabrickTransaction transaction = new FabrickTransactionsDto.FabrickTransaction();
        transaction.setTransactionId(id);
        transaction.setOperationId("OP123456789");
        transaction.setAccountingDate(LocalDate.now());
        transaction.setValueDate(LocalDate.now());
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setCurrency("EUR");
        transaction.setDescription("Test Insert");

        FabrickTransactionsDto dto = new FabrickTransactionsDto();
        dto.setList(List.of(transaction));
        return dto;
    }

    @Test
    void shouldInsertNewTransaction() {
        FabrickTransactionsDto dto = createDto("123456789");

        bankService.insertTransactions(dto);

        List<Transaction> all = bankRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTransactionId()).isEqualTo("123456789");
    }

    @Test
    void shouldNotInsertDuplicateTransaction() {
        FabrickTransactionsDto dto1 = createDto("123456789");
        bankService.insertTransactions(dto1);

        FabrickTransactionsDto dto2 = createDto("123456789");
        bankService.insertTransactions(dto2);

        List<Transaction> all = bankRepository.findAll();
        assertThat(all).hasSize(1);
    }
}