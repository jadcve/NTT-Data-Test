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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword())
            );

            String email = auth.getName();
            String token = jwtUtil.generateToken(email);
            usuarioRepository.findByEmail(email).ifPresent(usuario -> {
                usuario.setLastLogin(LocalDateTime.now());
                usuarioRepository.save(usuario);
            });
            return ResponseEntity.ok(new LoginResponse("Bearer", token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}
