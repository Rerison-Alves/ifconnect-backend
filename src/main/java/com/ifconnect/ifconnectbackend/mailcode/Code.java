package com.ifconnect.ifconnectbackend.mailcode;

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
@Table(name = "codes")
public class Code {

    @Id
    @GeneratedValue
    public Integer id;

    public int value;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    public Usuario usuario;

    private LocalDateTime createdAt;
}
