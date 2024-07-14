package com.gr8fulnez.Jaiz_bank_app.repository;

import com.gr8fulnez.Jaiz_bank_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
