package com.gr8fulnez.Jaiz_bank_app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}
