package com.alain.evaluacion.smartjob.bci.bcitest.controllers;

import com.alain.evaluacion.smartjob.bci.bcitest.dto.UsuarioDto;
import com.alain.evaluacion.smartjob.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.smartjob.bci.bcitest.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearUsuario() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("test@example.com");
        usuarioDto.setPassword("Password1!");
        usuarioDto.setName("Test User");

        // Simulamos el registro de un usuario
        given(usuarioService.registrarUsuario(any(UsuarioDto.class))).willReturn(usuarioDto);

        // Enviamos una solicitud POST al endpoint /crear con un objeto JSON
        mockMvc.perform(post("/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setEmail("update@example.com");
        usuarioDto.setPassword("Password2!");
        usuarioDto.setName("Updated User");

        given(usuarioService.actualizarUsuario(any(UsuarioDto.class))).willReturn(java.util.Optional.of(new Usuario()));

        mockMvc.perform(put("/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarUsuarios() throws Exception {
        given(usuarioService.findAll()).willReturn(Collections.singletonList(new Usuario()));

        mockMvc.perform(get("/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").exists());
    }
}
