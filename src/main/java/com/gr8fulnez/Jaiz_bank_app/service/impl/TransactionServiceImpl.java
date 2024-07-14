package com.gr8fulnez.Jaiz_bank_app.service.impl;

import com.gr8fulnez.Jaiz_bank_app.dto.TransactionDto;
import com.gr8fulnez.Jaiz_bank_app.entity.Transaction;
import com.gr8fulnez.Jaiz_bank_app.repository.TransactionRepository;
import com.gr8fulnez.Jaiz_bank_app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .build();

        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully");
    }
}
