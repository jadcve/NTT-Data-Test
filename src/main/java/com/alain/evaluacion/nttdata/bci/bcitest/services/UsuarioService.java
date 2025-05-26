package com.alain.evaluacion.nttdata.bci.bcitest.services;

import java.util.List;
import java.util.Optional;

import com.alain.evaluacion.nttdata.bci.bcitest.dto.UsuarioDto;
import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;

public interface UsuarioService {
    UsuarioDto registrarUsuario(UsuarioDto usuarioDto);
    Optional<Usuario> actualizarUsuario(UsuarioDto usuarioDto); 
    List<Usuario> findAll();
   
  
    
    boolean existsByEmail(String email);

}