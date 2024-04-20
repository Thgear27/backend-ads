package com.email.recuperacion_email.security;

import com.email.recuperacion_email.model.Usuario;
import com.email.recuperacion_email.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findOneByEmail(email);
        if(usuario==null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        String role = usuario.getRole().getRole();
        roles.add(new SimpleGrantedAuthority(role));
        return new User(usuario.getEmail(),usuario.getPassword(),roles);
    }
}
