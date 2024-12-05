package com.project1.LoginPage.service;

import com.project1.LoginPage.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Users user, String otp) {
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setSubject("Welcome to Our System");
            mail.setText(STR."""
                Dear \{user.getUserName()}
                Your OTP code is \{otp}""");
            javaMailSender.send(mail);
        }
        catch(Exception e){
            log.error("Error while sending email", e);
        }
    }
}
