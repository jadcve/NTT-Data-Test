package com.alain.evaluacion.smartjob.bci.bcitest.security.filter;

import java.io.IOException;

import static com.alain.evaluacion.smartjob.bci.bcitest.security.TokenJwtConfig.CONTENT_TYPE;
import static com.alain.evaluacion.smartjob.bci.bcitest.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.alain.evaluacion.smartjob.bci.bcitest.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.alain.evaluacion.smartjob.bci.bcitest.security.TokenJwtConfig.SECRET_KEY;
import com.alain.evaluacion.smartjob.bci.bcitest.security.SimpleGrantedAuthorityJsonCreator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * El JwtValidationFilter intercepta las solicitudes HTTP para verificar si hay un token JWT en el header de autorización. 
 * Si se encuentra un token, intenta validarlo y extraer información de autenticación. 
 * Si el token es válido, se establece el contexto de seguridad. Si no es válido, se responde con un error 401 Unauthorized.
 */
public class JwtValidationFilter extends BasicAuthenticationFilter  {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String usename = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, 
                                    SimpleGrantedAuthorityJsonCreator.class)
                .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class)
                );

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usename, null, authorities);
            SecurityContextHolder .getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT es invalido!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
        
    }

}
