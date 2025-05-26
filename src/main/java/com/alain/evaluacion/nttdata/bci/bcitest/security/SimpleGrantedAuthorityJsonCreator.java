package com.alain.evaluacion.nttdata.bci.bcitest.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Clase que permite la creacion de un objeto SimpleGrantedAuthority a partir de un JSON con la propiedad authority.
public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }
}
