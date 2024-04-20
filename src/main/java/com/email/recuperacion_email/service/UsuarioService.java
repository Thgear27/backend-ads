package com.email.recuperacion_email.service;

import com.email.recuperacion_email.model.Usuario;
import com.email.recuperacion_email.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario) throws Exception{
        return usuarioRepository.save(usuario);
    };

    public Usuario findOneByNombreUsuarioOrEmail(String cadena){
        return usuarioRepository.findOneByNombreUsuarioOrEmail(cadena,cadena);
    }

    public Usuario findOneByTokenPassword(String token){
        return usuarioRepository.findOneByTokenPassword(token);
    }

}
