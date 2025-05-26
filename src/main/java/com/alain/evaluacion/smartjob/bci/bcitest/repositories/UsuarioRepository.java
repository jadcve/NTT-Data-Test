package com.alain.evaluacion.smartjob.bci.bcitest.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alain.evaluacion.smartjob.bci.bcitest.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findAllById(Long id);

    boolean existsByEmail(String email);

}
