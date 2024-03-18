package com.ifconnect.ifconnectbackend.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join Usuario u\s
      on t.usuario.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  @Transactional
  @Modifying
  @Query("UPDATE Token t " +
          "SET t.confirmedAt = ?2 " +
          "WHERE t.token = ?1")
  int updateConfirmedAt(String token, LocalDateTime confirmedAt);

  Optional<Token> findByToken(String token);
}
