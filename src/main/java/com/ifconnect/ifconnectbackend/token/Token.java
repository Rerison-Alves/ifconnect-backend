package com.ifconnect.ifconnectbackend.token;

import com.ifconnect.ifconnectbackend.models.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

  @Id
  @GeneratedValue
  public Integer id;

  @Column(unique = true)
  public String value;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  private boolean revoked;

  private boolean expired;

  private LocalDateTime confirmedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario")
  public Usuario usuario;
}
