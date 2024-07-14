package com.gr8fulnez.Jaiz_bank_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnquiryRequest {
    private String accountNumber;
}
