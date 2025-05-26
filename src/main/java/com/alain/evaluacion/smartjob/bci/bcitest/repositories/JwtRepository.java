package com.alain.evaluacion.smartjob.bci.bcitest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.alain.evaluacion.smartjob.bci.bcitest.entities.JwtToken;

public interface JwtRepository extends CrudRepository<JwtToken, Long> {
    JwtToken findByToken(String token);

}
