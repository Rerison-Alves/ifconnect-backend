package com.ifconnect.ifconnectbackend.auth;

import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationRequest;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationResponse;
import com.ifconnect.ifconnectbackend.requestmodels.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    ResponseEntity<?> responseEntity = service.register(request);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      // Se o registro for bem-sucedido, retornar a resposta OK diretamente
      return ResponseEntity.ok(responseEntity.getBody());
    } else {
      // Se houver um erro durante o registro, retornar a resposta de erro com a mensagem apropriada
      return ResponseEntity.status(responseEntity.getStatusCode())
              .body(responseEntity.getBody());
    }
  }
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
    ResponseEntity<?> responseEntity = service.authenticate(request);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      // Se a autenticação for bem-sucedida, retornar a resposta OK diretamente
      return ResponseEntity.ok(responseEntity.getBody());
    } else {
      // Se houver um erro durante a autenticação, retornar a resposta de erro com a mensagem apropriada
      return ResponseEntity.status(responseEntity.getStatusCode())
              .body(responseEntity.getBody());
    }
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
