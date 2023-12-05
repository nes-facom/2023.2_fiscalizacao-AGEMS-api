package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@EnableJpaRepositories
@Repository
@Tag(name = "Interface Repositório de Token")
public interface TokenRepositorio extends JpaRepository<Token, Integer> {
  @Operation(summary = "Encontra tokens válidos por usuário")
  @Query(
    value = """
      select t from Token t inner join usuario u\s
      on t.usuario.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """
  )
  List<Token> findAllValidTokenByUser(UUID id);

  @Operation(summary = "Encontra Token pela entity Token")
  Optional<Token> findByToken(String token);
}
