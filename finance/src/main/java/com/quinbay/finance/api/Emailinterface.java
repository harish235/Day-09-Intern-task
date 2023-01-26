package com.quinbay.finance.api;

import com.quinbay.finance.model.EmailDetails;

// Interface
public interface Emailinterface {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}