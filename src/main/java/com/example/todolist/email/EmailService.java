package com.example.todolist.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailService() {}

    // Method to send email
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@example.com");  // Sender email (could be your SendGrid email)
        message.setTo(to);  // Receiver email
        message.setSubject(subject);  // Subject of the email
        message.setText(body);  // Body content of the email

        mailSender.send(message);  // Send the email
    }
}
