package com.alain.evaluacion.smartjob.bci.bcitest.services;

import java.util.List;
import java.util.Optional;

import com.alain.evaluacion.smartjob.bci.bcitest.dto.UsuarioDto;
import com.alain.evaluacion.smartjob.bci.bcitest.entities.Usuario;

public interface UsuarioService {
    UsuarioDto registrarUsuario(UsuarioDto usuarioDto);
    Optional<Usuario> actualizarUsuario(UsuarioDto usuarioDto); 
    List<Usuario> findAll();
   
  
    
    boolean existsByEmail(String email);

}