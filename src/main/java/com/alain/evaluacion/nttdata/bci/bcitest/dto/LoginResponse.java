package com.alain.evaluacion.nttdata.bci.bcitest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String type;   // "Bearer"
    private String token;  // JWT
}
