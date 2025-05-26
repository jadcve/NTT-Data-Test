package com.alain.evaluacion.smartjob.bci.bcitest.validations;
import java.lang.annotation.*;

import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@jakarta.validation.Constraint(validatedBy = ListNotEmptyValidator.class)
// Anotacion que valida si una lista no esta vacia
public @interface ListNotEmpty {
    String message() default "La lista no puede estar vacía";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
