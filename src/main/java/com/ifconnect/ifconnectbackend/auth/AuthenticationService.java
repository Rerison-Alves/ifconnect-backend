package com.ifconnect.ifconnectbackend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifconnect.ifconnectbackend.config.JwtService;
import com.ifconnect.ifconnectbackend.email.EmailSender;
import com.ifconnect.ifconnectbackend.email.EmailValidator;
import com.ifconnect.ifconnectbackend.exception.ErrorDetails;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationRequest;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationResponse;
import com.ifconnect.ifconnectbackend.requestmodels.RegisterRequest;
import com.ifconnect.ifconnectbackend.token.Token;
import com.ifconnect.ifconnectbackend.token.TokenRepository;
import com.ifconnect.ifconnectbackend.token.TokenType;
import com.ifconnect.ifconnectbackend.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsuarioRepository repository;
  private final EmailValidator emailValidator;
  private final EmailSender emailSender;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  @Value("${application.host}/api/v1/auth/register/confirm?token=")
  private String linkConfirm;

  public ResponseEntity<?> register(RegisterRequest request) {
    var user = Usuario.builder()
            .nome(request.getNome())
            .password(passwordEncoder.encode(request.getPassword()!=null?request.getPassword():null))
            .fotoPerfilBase64(request.getFotoPerfilBase64())
            .enabled(false)
            .email(request.getEmail())
            .dataNasc(request.getDataNasc())
            .aluno(request.getAluno())
            .professor(request.getProfessor())
            .role(request.getRole())
            .build();
    try {
      if (!emailValidator.test(user.getEmail())) {
        throw new IllegalStateException("Email não é válido");
      }
      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateConfirmToken(user);
      saveUserToken(savedUser, jwtToken);

      SendConfirmationEmail(request.getEmail(), request.getNome(), jwtToken);

      return ResponseEntity.ok().build();
    } catch (Exception e){
      return ResponseEntity.badRequest().body(new ErrorDetails(
              new Date(),
              e.getMessage(),
              HttpStatus.BAD_REQUEST.name()));
    }
  }

  @Transactional
  public String confirmToken(String token) {
    try {
      Token confirmationToken = getToken(tokenRepository.findByToken(token));
      tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
      repository.enableUsuario(confirmationToken.getUsuario().getEmail());
      return emailSender.confirmedPage();
    } catch (IllegalStateException e){
      return emailSender.erroConfirmPage(e.getMessage());
    }
  }

  private static Token getToken(Optional<Token> opConfirmationToken) {
    Token confirmationToken;
    if(opConfirmationToken.isPresent()){
      confirmationToken = opConfirmationToken.get();
    }else {
      throw new IllegalStateException("Opa, esse usuário não foi encontrado, houve algum erro!");
    }

    if (confirmationToken.getConfirmedAt() != null) {
      throw new IllegalStateException("Email já confirmado. Sua conta já está ativa.");
    }

    if (confirmationToken.isExpired()) {
      throw new AccountExpiredException("Link expirado, faça login novamente no aplicativo para um reenvio.");
    }
    return confirmationToken;
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

    } catch (DisabledException e) {
      var user = repository.findByEmail(request.getEmail()).orElseThrow();
      var jwtToken = jwtService.generateConfirmToken(user);
      saveUserToken(user, jwtToken);
      SendConfirmationEmail(request.getEmail(), user.getNome(), jwtToken);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(new ErrorDetails(
                      new Date(),
                      "É necessário ativar a conta, verifique seu email!.",
                      HttpStatus.UNAUTHORIZED.name()));

    }catch (AuthenticationException e) {
      // Se ocorrer uma exceção de autenticação, isso significa que as credenciais estão inválidas
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(new ErrorDetails(
                      new Date(),
                      "Credenciais inválidas. Verifique seu email e senha.",
                      HttpStatus.UNAUTHORIZED.name()));
    }
  }

  private void saveUserToken(Usuario usuario, String jwtToken) {
    var token = Token.builder()
        .usuario(usuario)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void SendConfirmationEmail(String email, String nome, String jwtToken) {
    String link =  linkConfirm + jwtToken;
    emailSender.send(
            email,
            emailSender.confirmEmail(nome, link));
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

  public ResponseEntity<?> refreshToken(HttpServletRequest request){
    try {
      //Recupera token e usuário
      final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      final String refreshToken;
      final String userEmail;

      if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
        throw new IllegalStateException("Sua sessão é inválida!");
      }
      refreshToken = authHeader.substring(7);
      userEmail = jwtService.extractUsername(refreshToken);

      //Valida usuário e token
      if (userEmail != null) {
        var user = this.repository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshToken, user)) {
          //Gera novo token
          var accessToken = jwtService.generateToken(user);
          revokeAllUserTokens(user);
          saveUserToken(user, accessToken);
          var authResponse = AuthenticationResponse.builder()
                  .accessToken(accessToken)
                  .refreshToken(refreshToken)
                  .build();
          //Retorna novo token
          return ResponseEntity.ok(authResponse);
        }else {
          throw new IllegalStateException("Sua sessão é inválida!");
        }
      }else {
        throw new IllegalStateException("Usuário não encontrado!");
      }
    }catch (IllegalStateException e){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
              new ErrorDetails(
                      new Date(),
                      e.getMessage(),
                      HttpStatus.UNAUTHORIZED.name())
      );
    }
  }
}
