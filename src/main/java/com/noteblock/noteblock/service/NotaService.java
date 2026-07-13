package com.noteblock.noteblock.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noteblock.noteblock.dto.NotaRequestDTO;
import com.noteblock.noteblock.dto.NotaResponseDTO;
import com.noteblock.noteblock.entity.Nota;
import com.noteblock.noteblock.entity.Usuario;
import com.noteblock.noteblock.exception.NotaNaoEncontradaException;
import com.noteblock.noteblock.repository.NotaRepository;
import com.noteblock.noteblock.repository.UsuarioRepository;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaService(NotaRepository notaRepository, UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // --- leitura ---

    public List<NotaResponseDTO> listarTodas() {
        Usuario usuario = getUsuarioLogado();
        return notaRepository.findAllByUsuarioId(usuario.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public NotaResponseDTO buscarPorId(Long id) {
        Usuario usuario = getUsuarioLogado();
        Nota nota = notaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new NotaNaoEncontradaException("Nota nao encontrada."));
        return toDTO(nota);
    }

    // --- escrita ---

    public NotaResponseDTO criar(NotaRequestDTO dto) {
        Usuario usuario = getUsuarioLogado();
        Nota nota = new Nota();
        nota.setTitulo(dto.getTitulo());
        nota.setConteudo(dto.getConteudo());
        nota.setUsuario(usuario);
        return toDTO(notaRepository.save(nota));
    }

    public NotaResponseDTO atualizar(Long id, NotaRequestDTO dto) {
        Usuario usuario = getUsuarioLogado();
        Nota nota = notaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new NotaNaoEncontradaException("Nota nao encontrada."));
        nota.setTitulo(dto.getTitulo());
        nota.setConteudo(dto.getConteudo());
        return toDTO(notaRepository.save(nota));
    }

    // --- deleção ---

    @Transactional
    public void deletarPorId(Long id) {
        Usuario usuario = getUsuarioLogado();
        notaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new NotaNaoEncontradaException("Nota nao encontrada."));
        notaRepository.deleteById(id);
    }

    @Transactional
    public void deletarTodas() {
        Usuario usuario = getUsuarioLogado();
        notaRepository.deleteAllByUsuarioId(usuario.getId());
    }

    // --- helpers ---

    private Usuario getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado nao encontrado no banco."));
    }

    private NotaResponseDTO toDTO(Nota nota) {
        return new NotaResponseDTO(nota.getId(), nota.getTitulo(), nota.getConteudo());
    }
}
