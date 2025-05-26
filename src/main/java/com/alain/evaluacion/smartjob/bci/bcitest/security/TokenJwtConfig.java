package com.alain.evaluacion.smartjob.bci.bcitest.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {

    /**
     * The secret key used for JWT authentication.
     * https://github.com/jwtk/jjwt?tab=readme-ov-file#quickstart 
     */
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); 
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}
