package com.noteblock.noteblock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noteblock.noteblock.dto.AuthTokenResponseDTO;
import com.noteblock.noteblock.dto.LoginDTO;
import com.noteblock.noteblock.dto.RegistroDto;
import com.noteblock.noteblock.dto.RegistroResponseDTO;
import com.noteblock.noteblock.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<RegistroResponseDTO> registrar(@Valid @RequestBody RegistroDto registroDto) {
        authService.registrar(registroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegistroResponseDTO(registroDto.getNome(), registroDto.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponseDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(new AuthTokenResponseDTO(token));
    }
}
