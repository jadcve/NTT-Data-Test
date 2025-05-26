package com.alain.evaluacion.smartjob.bci.bcitest.validations;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordIsValid, String> {

    @Value("${password.validation.regex}")
    private String passwordRegex;

    @Override
    public void initialize(PasswordIsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return true;
        }
        // Validar que el password cumpla con el formato requerido
        return Pattern.matches(passwordRegex, password);
    }

}


