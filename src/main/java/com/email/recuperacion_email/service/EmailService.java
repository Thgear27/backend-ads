package com.email.recuperacion_email.service;

import com.email.recuperacion_email.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${email.from}")
    private String emailUser;

    @Value("${mail.urlFront}")
    private String urlFront;

    public void sendEmailTemplate(EmailDTO emailDTO){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();
            model.put("username",emailDTO.getUsername());
            model.put("token",emailDTO.getToken().split("-")[1]+ "-".concat(emailDTO.getToken().split("-")[2]));
            model.put("url", urlFront);
            context.setVariables(model);
            String html = templateEngine.process("email",context);
            helper.setFrom(emailDTO.getMailFrom()); //quien envia el correo
            helper.setTo(emailDTO.getMailTo()); //a quien se va a hacer el envio
            helper.setSubject(emailDTO.getSubject()); //asunto
            helper.setText(html,true); //cuerpo
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

}
