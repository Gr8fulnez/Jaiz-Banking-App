package com.gr8fulnez.Jaiz_bank_app.service.impl;

import com.gr8fulnez.Jaiz_bank_app.dto.EmailDetails;
import com.gr8fulnez.Jaiz_bank_app.entity.Transaction;
import com.gr8fulnez.Jaiz_bank_app.entity.User;
import com.gr8fulnez.Jaiz_bank_app.repository.TransactionRepository;
import com.gr8fulnez.Jaiz_bank_app.repository.UserRepository;
import com.gr8fulnez.Jaiz_bank_app.service.EmailService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
            System.out.println("Mail sent successfully");
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            FileSystemResource file = new FileSystemResource(new File(
                    emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);

            log.info(file.getFilename() + " has been sent to user with email " +
                    emailDetails.getRecipient());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Component
    @AllArgsConstructor
    @Service
    @Slf4j
    public static class BankStatement {
        private TransactionRepository transactionRepository;
        private UserRepository userRepository;
        private EmailService emailService;
        private static final String FILE = "C:\\Users\\hp\\Documents\\MyStatement.pdf";

        /*
        *retrieve list of transaction within a data range given an account number
        * generate a pdf file of transactions
        * send the file via email
        */

        public List<Transaction> generateStatement(
                String accountNumber, String startDate, String endDate) throws DocumentException, FileNotFoundException {
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

            List<Transaction> transactionList = transactionRepository.findAll().stream()
                    .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                    .filter(transaction -> transaction.getCreatedAt().isEqual(start))
                    .filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();

            User user = userRepository.findByAccountNumber(accountNumber);
            String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();
            Rectangle statementSize = new Rectangle(PageSize.A4);
            Document document = new Document(statementSize);
            log.info("Setting size of document");
            OutputStream outputStream = new FileOutputStream(FILE);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPTable bankInfoTable = new PdfPTable(1);
            PdfPCell bankName = new PdfPCell(new Phrase("Jaiz Bank"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(20f);

            PdfPCell bankAddress = new PdfPCell(new Phrase("72, Odesola Estate, Lagos"));
            bankAddress.setBorder(0);
            bankInfoTable.addCell(bankName);
            bankInfoTable.addCell(bankAddress);

            PdfPTable statementInfo = new PdfPTable(2);
            PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
            customerInfo.setBorder(0);
            PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
            statement.setBorder(0);
            PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
            stopDate.setBorder(0);
            PdfPCell name = new PdfPCell(new Phrase("Customer Name " + customerName));
            name.setBorder(0);
            PdfPCell space = new PdfPCell();
            space.setBorder(0);
            PdfPCell address = new PdfPCell(new Phrase("Customer Address " + user.getAddress()));
            address.setBorder(0);

            PdfPTable transactionTable = new PdfPTable(4);
            PdfPCell date = new PdfPCell(new Phrase("DATE"));
            date.setBackgroundColor(BaseColor.BLUE);
            date.setBorder(0);
            PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
            transactionType.setBackgroundColor(BaseColor.BLUE);
            transactionType.setBorder(0);
            PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
            transactionAmount.setBackgroundColor(BaseColor.BLUE);
            transactionAmount.setBorder(0);
            PdfPCell status = new PdfPCell(new Phrase("STATUS"));
            status.setBackgroundColor(BaseColor.BLUE);
            status.setBorder(0);

            transactionTable.addCell(date);
            transactionTable.addCell(transactionType);
            transactionTable.addCell(transactionAmount);
            transactionTable.addCell(status);

            transactionList.forEach(transaction -> {
                transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
                transactionTable.addCell(new Phrase(transaction.getTransactionType()));
                transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
                transactionTable.addCell(new Phrase(transaction.getStatus()));
            });

            statementInfo.addCell(customerInfo);
            statementInfo.addCell(statement);
            statementInfo.addCell(endDate);
            statementInfo.addCell(name);
            statementInfo.addCell(space);
            statementInfo.addCell(address);

            document.add(bankInfoTable);
            document.add(statementInfo);
            document.add(transactionTable);

            document.close();

            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(user.getEmail())
                    .subject("STATEMENT OF ACCOUNT")
                    .messageBody("Kindly find your requested account statement attached!")
                    .attachment(FILE)
                    .build();

            emailService.sendEmailWithAttachment(emailDetails);

            return transactionList;
        }

    }
}
