package com.email.recuperacion_email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${email.from}")
    private String emailUser;

    @Value("${email.password}")
    private String password;


    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(587);
        mailSender.setUsername(emailUser);
        mailSender.setPassword(password);
        mailSender.setHost("smtp.gmail.com");
        Properties propiedades = mailSender.getJavaMailProperties();
        propiedades.put("mail.transport.protocol", "smtp");
        propiedades.put("mail.smtp.auth", "true"); //habilitamos la autenticación con nuestro usuario y contraseña, sino no puede enviar el correo
        propiedades.put("mail.smtp.starttls.enable", "true"); //habilitamos el cifrado
        propiedades.put("mail.debug", "false"); //para que en la consola imprima información relacionada al gmail (para desarrollo pero debe estar desabilitado para producción)
        return mailSender;
    }



}
