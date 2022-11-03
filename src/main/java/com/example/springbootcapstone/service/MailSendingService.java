package com.example.springbootcapstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSendingService {

    @Autowired
    JavaMailSender javaMailSender;

    @Async
    public void mailSender(String email,String token){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sanjaysankar002@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/register/confirm?token="+token);

        javaMailSender.send(mailMessage);
    }

    @Async
    public void forgotMailSender(String email,String token){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sanjaysankar002@gmail.com");
        mailMessage.setText("To change your password, please click here : "
                +"http://localhost:8080/forgot/changePassword?token="+token);

        javaMailSender.send(mailMessage);
    }
}
