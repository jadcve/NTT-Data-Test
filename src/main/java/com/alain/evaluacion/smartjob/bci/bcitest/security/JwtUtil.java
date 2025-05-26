package com.alain.evaluacion.smartjob.bci.bcitest.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@SuppressWarnings("deprecation")
public class JwtUtil {
    
    // Clave secreta para firmar el token, se genera con el algoritmo HS256
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // clase que genera el token con el email del usuario y una fecha de expiracion de 1 hora
    public static String generateToken(String email) {
        long expirationTime = 3600000; 
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        String token = Jwts.builder()
                .setSubject(email) 
                .setIssuedAt(now) 
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY) 
                .compact(); 

        return token;
    }
}





