package com.noteblock.noteblock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteblock.noteblock.entity.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findAllByUsuarioId(Long usuarioId);

    Optional<Nota> findByIdAndUsuarioId(Long notaId, Long usuarioId);

    long deleteAllByUsuarioId(Long usuarioId);

}
