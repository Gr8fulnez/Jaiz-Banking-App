package com.gr8fulnez.Jaiz_bank_app.service;

import com.gr8fulnez.Jaiz_bank_app.dto.*;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse tranfer(TransferRequest request);

    BankResponse login(LoginDto loginDto);
}
