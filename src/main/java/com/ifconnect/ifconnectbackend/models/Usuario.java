package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifconnect.ifconnectbackend.models.enums.Role;
import com.ifconnect.ifconnectbackend.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nome;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataNasc;

    @Embedded
    private Aluno aluno;

    @Embedded
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
