package com.alain.evaluacion.nttdata.bci.bcitest.services.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alain.evaluacion.nttdata.bci.bcitest.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alain.evaluacion.nttdata.bci.bcitest.dto.TelefonoDto;
import com.alain.evaluacion.nttdata.bci.bcitest.dto.UsuarioDto;
import com.alain.evaluacion.nttdata.bci.bcitest.entities.Audit;
import com.alain.evaluacion.nttdata.bci.bcitest.entities.Telefono;
import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.nttdata.bci.bcitest.repositories.JwtRepository;
import com.alain.evaluacion.nttdata.bci.bcitest.repositories.UsuarioRepository;
import com.alain.evaluacion.nttdata.bci.bcitest.services.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImp implements UsuarioService {
   
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtRepository jwtTokenRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public UsuarioServiceImp(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    @Override
    public UsuarioDto registrarUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.findByEmail(usuarioDto.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("El correo ya registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setName(usuarioDto.getName());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setActive(true);
        usuario.setLastLogin(LocalDateTime.now());
        usuario.setAudit(new Audit());
    
        // Se crea una lista de telefonos a partir de los telefonos del usuarioDto
        List<Telefono> telefonos = usuarioDto.getPhones().stream()
                .map(phoneDto -> {
                    Telefono telefono = new Telefono();
                    telefono.setNumber(phoneDto.getNumber());
                    telefono.setCitycode(phoneDto.getCitycode());
                    telefono.setCountrycode(phoneDto.getContrycode());
                    telefono.setUsuario(usuario); 
                    return telefono;
                }).collect(Collectors.toList());
        
        usuario.setTelefonos(telefonos);
    
        Usuario savedUser = usuarioRepository.save(usuario);
    
        UsuarioDto respuestaDto = new UsuarioDto();
        respuestaDto.setId(usuario.getId());
        respuestaDto.setName(usuario.getName());
        respuestaDto.setEmail(usuario.getEmail());
        respuestaDto.setActive(usuario.isActive());
        respuestaDto.setLastLogin(usuario.getLastLogin());
        respuestaDto.setAudit(usuario.getAudit());
        respuestaDto.setPhones(usuario.getTelefonos().stream()
            .map(this::convertirATelefonoDto) 
            .collect(Collectors.toList()));


        return respuestaDto;
    }
    
    private TelefonoDto convertirATelefonoDto(Telefono telefono) {
        return new TelefonoDto(telefono.getNumber(), telefono.getCitycode(), telefono.getCountrycode());
    }

    @Transactional
    @Override
    public Optional<Usuario> actualizarUsuario(UsuarioDto usuarioDto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usuarioDto.getEmail());
        if (optionalUsuario.isEmpty()) {
            return Optional.empty();
        }
    
        Usuario usuario = optionalUsuario.get();
        usuario.setName(usuarioDto.getName());
        usuario.setEmail(usuarioDto.getEmail());
        
        if (usuarioDto.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        }
        
        usuario.setActive(usuarioDto.isActive());
        usuario.setLastLogin(LocalDateTime.now());
    
        List<Telefono> telefonos = usuario.getTelefonos();
        if (telefonos != null) {
            telefonos.clear();
        } else {
            telefonos = new ArrayList<>();
        }
        
        List<Telefono> updatedTelefonos = usuarioDto.getPhones().stream()
                .map(phoneDto -> {
                    Telefono telefono = new Telefono();
                    telefono.setNumber(phoneDto.getNumber());
                    telefono.setCitycode(phoneDto.getCitycode());
                    telefono.setCountrycode(phoneDto.getContrycode());
                    telefono.setUsuario(usuario);
                    return telefono;
                }).collect(Collectors.toList());
        
        telefonos.addAll(updatedTelefonos);
        usuario.setTelefonos(telefonos);
        return Optional.of(usuarioRepository.save(usuario));
    }
    

    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

   

}
