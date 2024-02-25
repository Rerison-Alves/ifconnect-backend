package com.ifconnect.ifconnectbackend.requestmodels;

import com.ifconnect.ifconnectbackend.models.Aluno;
import com.ifconnect.ifconnectbackend.models.Professor;
import com.ifconnect.ifconnectbackend.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String nome;
  private String email;
  private String password;
  private Date dataNasc;
  private Aluno aluno;
  private Professor professor;
  private Role role;

}
