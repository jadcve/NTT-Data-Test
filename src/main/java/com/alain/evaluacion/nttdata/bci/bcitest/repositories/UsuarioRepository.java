package com.alain.evaluacion.nttdata.bci.bcitest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

}
