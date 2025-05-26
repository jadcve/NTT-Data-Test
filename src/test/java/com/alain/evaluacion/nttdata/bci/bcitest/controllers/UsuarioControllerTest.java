package com.alain.evaluacion.nttdata.bci.bcitest.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.alain.evaluacion.nttdata.bci.bcitest.dto.UsuarioDto;
import com.alain.evaluacion.nttdata.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.nttdata.bci.bcitest.services.UsuarioService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testCrearUsuario() throws Exception {
        String jsonRequest = """
        {
            "email": "test@example.com",
            "name": "Test User",
            "password": "MyPass123$",
            "phones": [
                {
                    "number": "123456789",
                    "citycode": "1",
                    "contrycode": "57"
                }
            ]
        }
        """;

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("test@example.com");
        usuarioDto.setName("Test User");
        usuarioDto.setPassword("MyPass123$");
        // Otros setters si es necesario

        given(usuarioService.registrarUsuario(any(UsuarioDto.class))).willReturn(usuarioDto);

        mockMvc.perform(post("/api/usuario/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        String jsonRequest = """
        {
            "id": 1,
            "email": "update@example.com",
            "name": "Updated User",
            "password": "Password2!",
            "phones": [
                {
                    "number": "987654321",
                    "citycode": "2",
                    "contrycode": "57"
                }
            ]
        }
        """;

        given(usuarioService.actualizarUsuario(any(UsuarioDto.class))).willReturn(Optional.of(new Usuario()));

        mockMvc.perform(put("/api/usuario/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarUsuarios() throws Exception {
        given(usuarioService.findAll()).willReturn(Collections.singletonList(new Usuario()));

        mockMvc.perform(get("/api/usuario/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
