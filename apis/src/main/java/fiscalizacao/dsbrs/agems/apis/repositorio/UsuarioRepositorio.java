package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Usuário")
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
  @Operation(summary = "Encontra usuário pelo nome")
  public Optional<Usuario> findByNome(String nome);

  @Operation(summary = "Encontra todos os Usuário pelo nome")
  public List<Usuario> findAllByNome(String nome);

  @Operation(summary = "Encontra Usuário pelo email")
  public Optional<Usuario> findByEmail(String email);
}
