package com.alain.evaluacion.nttdata.bci.bcitest.validations;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ListNotEmptyValidator implements ConstraintValidator<ListNotEmpty, List<?>> {
    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        return list != null && !list.isEmpty();
    }
}