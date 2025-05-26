package com.alain.evaluacion.smartjob.bci.bcitest.controllers;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alain.evaluacion.smartjob.bci.bcitest.dto.UsuarioDto;
import com.alain.evaluacion.smartjob.bci.bcitest.entities.Usuario;
import com.alain.evaluacion.smartjob.bci.bcitest.services.impl.UsuarioServiceImp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioServiceImp usuarioService;

    public UsuarioController(UsuarioServiceImp usuarioService) {
        this.usuarioService = usuarioService;
    }



    // BindingResult result es un objeto que contiene los errores de validación
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result) {
        // hasFieldErrors()  se utiliza para determinar si hay errores de validación específicos en un campo determinado de un objeto
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        UsuarioDto registeredDto = usuarioService.registrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredDto);
    }

    
    @PutMapping("/actualizar")
    public ResponseEntity<Optional<Usuario>> updateUsuario(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result) {
        Optional<Usuario> usuarioActualizado = usuarioService.actualizarUsuario(usuarioDto);
        if (usuarioActualizado.isPresent()) {
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
    

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
}
