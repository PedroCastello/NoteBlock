package com.noteblock.noteblock.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noteblock.noteblock.dto.LoginDTO;
import com.noteblock.noteblock.dto.RegistroDto;
import com.noteblock.noteblock.entity.Usuario;
import com.noteblock.noteblock.repository.UsuarioRepository;
import com.noteblock.noteblock.security.TokenService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public void registrar(RegistroDto registroDto) {
        if (usuarioRepository.findByEmail(registroDto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email ja cadastrado.");
        }

        Usuario usuario = new Usuario(
                registroDto.getNome(),
                registroDto.getEmail(),
                passwordEncoder.encode(registroDto.getSenha()));

        usuarioRepository.save(usuario);
    }

    public String login(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return tokenService.generateToken(userDetails);
        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Credenciais invalidas.");
        }
    }
}
