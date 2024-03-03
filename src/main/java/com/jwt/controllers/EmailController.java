package com.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.services.email.EmailService;
import com.jwt.security.services.email.models.Email;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@RestController
@RequestMapping("api/mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/mailtest")
    public void sendEmail(@RequestBody Email request) {

        // Suponiendo que EmailRequest tiene campos como to, subject y text
        emailService.sendEmailTest(request.getEmail(), "email-prueba");
    }


    //@PostMapping("/send-email")
    //public void sendEmail(@RequestBody EmailRequest request) {
        // Suponiendo que EmailRequest tiene campos como to, subject y text
     //   emailService.sendEmail(request.getTo(), request.getSubject(), request.getText());
    //}
    
}
