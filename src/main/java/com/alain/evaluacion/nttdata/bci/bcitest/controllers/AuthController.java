package com.alain.evaluacion.nttdata.bci.bcitest.controllers;

import com.alain.evaluacion.nttdata.bci.bcitest.dto.LoginRequest;
import com.alain.evaluacion.nttdata.bci.bcitest.dto.LoginResponse;
import com.alain.evaluacion.nttdata.bci.bcitest.repositories.UsuarioRepository;

import com.alain.evaluacion.nttdata.bci.bcitest.security.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;

/**
 * Endpoints de autenticación.
 *
 * POST /api/v1/auth/login  →  devuelve { "type": "Bearer", "token": "<JWT>" }
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;


        /** Recibe JSON {"username": "...", "password": "..."} y devuelve el JWT. */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        try {
            // 1. Autenticar contra UserDetailsService / PasswordEncoder
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword())
            );

            // 2. Generar token usando el nombre de usuario autenticado
            String email = auth.getName();
            String token = jwtUtil.generateToken(email);

            // 2.1. Actualizar fecha de último login de forma segura
            usuarioRepository.findByEmail(email).ifPresent(usuario -> {
                usuario.setLastLogin(LocalDateTime.now());
                usuarioRepository.save(usuario);
            });

            // 3. Responder con el token
            return ResponseEntity.ok(new LoginResponse("Bearer", token));

        } catch (AuthenticationException ex) {
            // Credenciales inválidas → 401
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}
