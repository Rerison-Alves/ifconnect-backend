package com.ifconnect.ifconnectbackend.auth;

import com.ifconnect.ifconnectbackend.exception.ErrorDetails;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationRequest;
import com.ifconnect.ifconnectbackend.requestmodels.AuthenticationResponse;
import com.ifconnect.ifconnectbackend.requestmodels.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação")
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "Register users", description = "Register users")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Request Ok"),
          @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
          @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
          @ApiResponse(responseCode = "404", description = "Resource not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      service.register(request);
      return ResponseEntity.ok().build();
    }catch (Exception e){
      return ResponseEntity.badRequest().body(new ErrorDetails(
              new Date(),
              e.getMessage(),
              HttpStatus.BAD_REQUEST.name()));
    }
  }

  @GetMapping("/register/confirm")
  public String confirm(@RequestParam("token") String token) {
    return service.confirmToken(token);
  }

  @Operation(summary = "Authenticate users", description = "Return auth of user")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Request Ok"),
          @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
          @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
          @ApiResponse(responseCode = "404", description = "Resource not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
    try {
      var response = service.authenticate(request);
      return ResponseEntity.ok(response);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(new ErrorDetails(
                      new Date(),
                      e.getMessage(),
                      HttpStatus.UNAUTHORIZED.name()));
    }
  }

  @Operation(summary = "Refresh auth users", description = "Return refreshed-auth of user")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Request Ok"),
          @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
          @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
          @ApiResponse(responseCode = "404", description = "Resource not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
  @GetMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(HttpServletRequest request){
    try {
      var response = service.refreshToken(request);
      return ResponseEntity.ok(response);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
              new ErrorDetails(
                      new Date(),
                      e.getMessage(),
                      HttpStatus.UNAUTHORIZED.name())
      );
    }
  }


}
