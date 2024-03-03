package com.jwt.security.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
     @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String template, Context context) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);

            // Procesar la plantilla con los datos del contexto
            String htmlContent = templateEngine.process(template, context);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void sendEmailTest(String to,String template) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Mail de Prueba");

            Context context = new Context();
            //context.setVariable(template, context);
            // Procesar la plantilla con los datos del contexto
            String htmlContent = templateEngine.process(template,context);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
