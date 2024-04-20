package com.email.recuperacion_email.controller;

import com.email.recuperacion_email.dto.CambioPasswordDTO;
import com.email.recuperacion_email.dto.EmailDTO;
import com.email.recuperacion_email.model.Usuario;
import com.email.recuperacion_email.service.EmailService;
import com.email.recuperacion_email.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    @Value("${email.from}")
    private String mailFrom;

    @PreAuthorize("@authService.acceso()")
    @PostMapping("/enviar-html")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailDTO emailDto) throws Exception{
        Usuario usuario = usuarioService.findOneByNombreUsuarioOrEmail(emailDto.getMailTo());
        if(usuario == null) {
            return new ResponseEntity<>(Map.of("mensaje","Usuario no encontrado"), HttpStatus.BAD_REQUEST);
        }
        emailDto.setSubject("Cambio de contraseña");
        emailDto.setUsername(usuario.getEmail());
        emailDto.setMailTo(usuario.getEmail());
        emailDto.setMailFrom(mailFrom);
        UUID uuid = UUID.randomUUID();
        emailDto.setToken(uuid.toString());
        usuario.setTokenPassword(uuid.toString());
        usuarioService.save(usuario);
        emailService.sendEmailTemplate(emailDto);
        return new ResponseEntity<>("Correo con plantilla enviado", HttpStatus.OK);
    }

    @PostMapping("/cambio-password")
    public ResponseEntity<?> cambioPassword(@RequestBody CambioPasswordDTO cambioPasswordDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(Map.of("mensaje","Campos invalidos") ,HttpStatus.BAD_REQUEST);
        if(!cambioPasswordDTO.getPassword().equals(cambioPasswordDTO.getConfirmPassword())){
            return new ResponseEntity<>(Map.of("mensaje","Las contraseñas no coinciden"),HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.findOneByTokenPassword(cambioPasswordDTO.getTokenPassword());
        if(usuario == null) {
            return new ResponseEntity<>(Map.of("mensaje","Usuario no encontrado"), HttpStatus.BAD_REQUEST);
        }
        usuario.setPassword(passwordEncoder.encode(cambioPasswordDTO.getConfirmPassword()));
        usuario.setTokenPassword(null);
        usuarioService.save(usuario);
        return new ResponseEntity<>(Map.of("mensaje","Actualizado correctamente"), HttpStatus.OK);
    }



}
