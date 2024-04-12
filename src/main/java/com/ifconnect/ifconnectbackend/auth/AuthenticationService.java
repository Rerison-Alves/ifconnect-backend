package com.ifconnect.ifconnectbackend.auth;

import com.ifconnect.ifconnectbackend.config.JwtService;
import com.ifconnect.ifconnectbackend.email.EmailSender;
import com.ifconnect.ifconnectbackend.email.EmailValidator;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationRequest;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationResponse;
import com.ifconnect.ifconnectbackend.requestmodels.RegisterRequest;
import com.ifconnect.ifconnectbackend.token.Token;
import com.ifconnect.ifconnectbackend.token.TokenRepository;
import com.ifconnect.ifconnectbackend.token.TokenType;
import com.ifconnect.ifconnectbackend.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
  private static final String API_CONFIRM_ADRESS = "/api/v1/auth/register/confirm?token=";
  @Value("${application.host}")
  private String host;

  public void register(RegisterRequest request) {
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
    if (!emailValidator.test(user.getEmail())) {
      throw new IllegalStateException("Email não é válido");
    }
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateConfirmToken(user);
    saveUserToken(savedUser, jwtToken);

    sendConfirmationEmail(request.getEmail(), request.getNome(), jwtToken);
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

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
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

      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();

    } catch (DisabledException e) {
      var user = repository.findByEmail(request.getEmail()).orElseThrow();
      var jwtToken = jwtService.generateConfirmToken(user);
      saveUserToken(user, jwtToken);
      sendConfirmationEmail(request.getEmail(), user.getNome(), jwtToken);
      throw new IllegalStateException("É necessário ativar a conta, verifique seu email!.");
    }catch (AuthenticationException e) {
      throw new IllegalStateException("Credenciais inválidas. Verifique seu email e senha.");
    }
  }

  private void saveUserToken(Usuario usuario, String jwtToken) {
    var token = Token.builder()
        .usuario(usuario)
        .value(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void sendConfirmationEmail(String email, String nome, String jwtToken) {
    String link =  host + API_CONFIRM_ADRESS + jwtToken;
    emailSender.send(
            email,
            emailSender.confirmEmail(nome, link),
            "Confirme seu email");
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

  public AuthenticationResponse refreshToken(HttpServletRequest request){
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
          //Retorna novo token
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
      }else {
        throw new IllegalStateException("Sua sessão é inválida!");
      }
    }else {
      throw new IllegalStateException("Usuário não encontrado!");
    }
  }
}
