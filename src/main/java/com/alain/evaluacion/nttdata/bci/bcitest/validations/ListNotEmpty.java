package com.alain.evaluacion.nttdata.bci.bcitest.validations;
import java.lang.annotation.*;

import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@jakarta.validation.Constraint(validatedBy = ListNotEmptyValidator.class)

public @interface ListNotEmpty {
    String message() default "La lista no puede estar vacía";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
