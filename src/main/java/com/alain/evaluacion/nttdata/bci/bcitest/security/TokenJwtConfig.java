package com.alain.evaluacion.nttdata.bci.bcitest.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class TokenJwtConfig {

    /**
     * The secret key used for JWT authentication.
     * https://github.com/jwtk/jjwt?tab=readme-ov-file#quickstart 
     */
    public static final SecretKey SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}
