package com.email.recuperacion_email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    //SI ES ADMIN VA A TENER ACCESO
    public boolean acceso(){
        boolean pass= true;
        String rolUser = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority gra: auth.getAuthorities()){
            rolUser = gra.getAuthority();
        }
        pass = rolUser.equals("TRABAJADOR");
        logger.info(auth.getPrincipal().toString());
        return !pass;
    }

}
