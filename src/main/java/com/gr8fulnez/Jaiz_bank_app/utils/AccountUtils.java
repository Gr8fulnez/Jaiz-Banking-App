package com.gr8fulnez.Jaiz_bank_app.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account Created!";
    public static final String ACCOUNT_CREATION_SUCCESS ="002";
    public static final String ACCOUNT_CREATION_MESSAGE ="Account has been successfully created";
    public static  final String ACCOUNT_NOT_EXIST_CODE  = "003";
    public static  final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist";
    public static  final String ACCOUNT_FOUND_CODE  = "004";
    public static  final String ACCOUNT_FOUND_SUCCESS  = "User Account Found";
    public static  final String ACCOUNT_CREDITED_SUCCESS  = "005";
    public static  final String ACCOUNT_CREDITED_SUCCESS_MESSAGE  = "User Account Credited";
    public static  final String INSUFFICIENT_BALANCE_CODE  = "006";
    public static  final String INSUFFICIENT_BALANCE_MESSAGE  = "Insufficient balance";
    public static  final String ACCOUNT_DEBITED_SUCCESS  = "007";
    public static  final String ACCOUNT_DEBITED_MESSAGE  = "Account has been successfully debited";
    public static  final String TRANSFER_SUCCESSFUL_CODE  = "008";
    public static  final String TRANSFER_SUCCESSFUL_MESSAGE  = "Transfer successful";




    public static String generateAccountNumber(){
        /**
         * account no should have current year and any random sixDigits
         */

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //generate a random number between min and max

        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        //converting the current and randomNumber to strings when concatenated

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randNumber).toString();
    }
}
