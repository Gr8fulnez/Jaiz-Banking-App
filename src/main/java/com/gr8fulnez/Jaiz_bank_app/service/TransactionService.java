package com.gr8fulnez.Jaiz_bank_app.service;

import com.gr8fulnez.Jaiz_bank_app.dto.TransactionDto;
import com.gr8fulnez.Jaiz_bank_app.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
