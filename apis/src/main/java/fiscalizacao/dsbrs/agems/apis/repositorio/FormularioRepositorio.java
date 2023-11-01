package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Formulário")
public interface FormularioRepositorio
  extends CrudRepository<Formulario, Integer> {
  @Operation(summary = "Encontra formulários por usuário de criação")
  public List<Formulario> findByUsuarioCriacao(Usuario usuario);
}
