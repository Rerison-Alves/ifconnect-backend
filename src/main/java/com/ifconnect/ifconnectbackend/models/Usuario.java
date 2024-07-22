package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifconnect.ifconnectbackend.models.enums.Role;
import com.ifconnect.ifconnectbackend.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Nome ${notblank}")
    @Size(min = 3, max = 100, message = "Nome ${size}")
    private String nome;

    @JsonIgnore
    @NotBlank(message = "Senha ${notblank}")
    @Size(min = 8, max = 255, message = "Senha ${size}")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "Email ${notblank}")
    private String email;

    @Column(columnDefinition = "TEXT")
    @JsonIgnore
    private String fotoPerfilBase64;

    @JsonFormat(pattern="dd/MM/yyyy")
    @NotNull(message = "Data de nascimento ${notblank}")
    private Date dataNasc;

    @Embedded
    private Aluno aluno;

    @Embedded
    private Professor professor;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Grupo> grupos;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turma> turmas;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Permissão ${notblank}")
    private Role role;

    @JsonIgnore
    private Boolean enabled;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Token> tokens;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
