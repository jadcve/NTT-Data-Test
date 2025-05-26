package com.alain.evaluacion.nttdata.bci.bcitest.controllers;

import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.nttdata.bci.bcitest.repositories.UsuarioRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        // Crear un usuario directamente en la BD para login
        Usuario user = new Usuario();
        user.setEmail("test@dominio.cl");
        user.setPassword(passwordEncoder.encode("Abc*1234!"));
        user.setName("Test");
        user.setActive(true);
        user.setLastLogin(LocalDateTime.now());
        user.setTelefonos(Collections.emptyList());

        usuarioRepository.save(user);
    }

    @Test
    void testLoginAndAccessProtectedRoute() throws Exception {
        String loginRequest = """
            {
              "email": "test@dominio.cl",
              "password": "Abc*1234!"
            }
            """;

        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.token");

        mockMvc.perform(get("/api/usuario/listar")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
