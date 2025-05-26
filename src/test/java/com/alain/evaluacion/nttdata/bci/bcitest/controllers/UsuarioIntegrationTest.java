package com.alain.evaluacion.nttdata.bci.bcitest.controllers;

import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.nttdata.bci.bcitest.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long userId;

    @BeforeAll
    void setup() {
        usuarioRepository.deleteAll();

        Usuario user = new Usuario();
        user.setEmail("test@dominio.cl");
        user.setPassword(passwordEncoder.encode("Abc*1234!"));
        user.setName("Test User");
        user.setActive(true);
        user.setLastLogin(LocalDateTime.now());
        user.setTelefonos(Collections.emptyList());

        userId = usuarioRepository.save(user).getId();
    }

    @Test
    void testLoginCrearListarActualizarUsuario() throws Exception {
        String loginJson = """
        {
            "email": "test@dominio.cl",
            "password": "Abc*1234!"
        }
        """;

        MvcResult loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.token");

        String jsonCreate = """
        {
            "email": "nuevo@dominio.cl",
            "name": "Nuevo Usuario",
            "password": "Clave123*",
            "phones": [
                {
                    "number": "111222333",
                    "citycode": "1",
                    "contrycode": "57"
                }
            ]
        }
        """;

        mockMvc.perform(post("/api/usuario/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreate))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("nuevo@dominio.cl"));

        mockMvc.perform(get("/api/usuario/listar")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        String jsonUpdate = String.format("""
        {
            "id": %d,
            "email": "test@dominio.cl",
            "name": "Usuario Modificado",
            "password": "Abc*1234!",
            "phones": [
                {
                    "number": "987654321",
                    "citycode": "2",
                    "contrycode": "57"
                }
            ]
        }
        """, userId);

        mockMvc.perform(put("/api/usuario/actualizar")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk());
    }
}