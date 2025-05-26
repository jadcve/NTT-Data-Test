package com.alain.evaluacion.nttdata.bci.bcitest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.nttdata.bci.bcitest.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;



@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {    
        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }
        
        Usuario user = usuario.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getName());

        return new org.springframework.security.core.userdetails.User(
                        user.getEmail(), 
                        user.getPassword(), 
                        true, 
                        true, 
                        true, 
                        true, 
                        Collections.singletonList(authority) 
        );
    }
    
    @EventListener
    public void handleSuccessfulLogin(AuthenticationSuccessEvent event) {
        String username = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        usuarioOptional.ifPresent(usuario -> {
            usuario.setLastLogin(LocalDateTime.now()); 
            usuarioRepository.save(usuario); 
        });
    }
}
