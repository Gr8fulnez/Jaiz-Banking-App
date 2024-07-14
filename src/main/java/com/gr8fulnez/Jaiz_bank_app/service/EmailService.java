package com.gr8fulnez.Jaiz_bank_app.service;

import com.gr8fulnez.Jaiz_bank_app.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);
}
