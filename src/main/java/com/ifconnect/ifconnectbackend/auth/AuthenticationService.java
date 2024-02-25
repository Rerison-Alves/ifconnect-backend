package com.ifconnect.ifconnectbackend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifconnect.ifconnectbackend.config.JwtService;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationRequest;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationResponse;
import com.ifconnect.ifconnectbackend.requestmodels.ErrorResponse;
import com.ifconnect.ifconnectbackend.requestmodels.RegisterRequest;
import com.ifconnect.ifconnectbackend.token.Token;
import com.ifconnect.ifconnectbackend.token.TokenRepository;
import com.ifconnect.ifconnectbackend.token.TokenType;
import com.ifconnect.ifconnectbackend.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.ifconnect.ifconnectbackend.auth.AutheticationValidator.handleDuplicateError;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<?> register(RegisterRequest request) {
    var user = Usuario.builder()
            .nome(request.getNome())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .dataNasc(request.getDataNasc())
            .aluno(request.getAluno())
            .professor(request.getProfessor())
            .role(request.getRole())
            .build();
    try {
      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);
      saveUserToken(savedUser, jwtToken);
      return ResponseEntity.ok(AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build());
    } catch (DataIntegrityViolationException e){
      return handleDuplicateError(e.getMessage(), user);
    }
  }

  public ResponseEntity<?> authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
      var user = repository.findByEmail(request.getEmail()).orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return ResponseEntity.ok(AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build());
    } catch (AuthenticationException e) {
      // Se ocorrer uma exceção de autenticação, isso significa que as credenciais estão inválidas
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(new ErrorResponse(
                      "Credenciais inválidas. Verifique seu email e senha.",
                      HttpStatus.UNAUTHORIZED.name()));
    }
  }

  private void saveUserToken(Usuario user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Usuario user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
