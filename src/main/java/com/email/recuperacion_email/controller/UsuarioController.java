package com.email.recuperacion_email.controller;

import com.email.recuperacion_email.model.Usuario;
import com.email.recuperacion_email.service.UsuarioService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    public UsuarioController(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Usuario usuario) throws Exception{
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
    }


}
