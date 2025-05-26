package com.alain.evaluacion.smartjob.bci.bcitest.validations;
import org.springframework.stereotype.Component;

import com.alain.evaluacion.smartjob.bci.bcitest.services.UsuarioService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByEmailValidation implements ConstraintValidator<ExistsByEmail, String> {

    private final UsuarioService service;
    public ExistsByEmailValidation(UsuarioService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return service == null || !service.existsByEmail(email);
    }
}

