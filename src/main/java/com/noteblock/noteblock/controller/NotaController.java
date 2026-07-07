package com.noteblock.noteblock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noteblock.noteblock.dto.NotaRequestDTO;
import com.noteblock.noteblock.dto.NotaResponseDTO;
import com.noteblock.noteblock.service.NotaService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    // POST /notas — cria nota do usuario logado
    @PostMapping
    public ResponseEntity<NotaResponseDTO> criar(@Valid @RequestBody NotaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<NotaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(notaService.listarTodas());
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody NotaRequestDTO dto) {
        return ResponseEntity.ok(notaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        notaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodas() {
        notaService.deletarTodas();
        return ResponseEntity.noContent().build();
    }

}
