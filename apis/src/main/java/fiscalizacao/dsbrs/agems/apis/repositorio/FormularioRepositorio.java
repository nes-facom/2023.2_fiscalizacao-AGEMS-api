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
  @Operation(summary = "Encontra Formul\u00E1rios por Usuário")
  public List<Formulario> findByUsuario(Usuario usuario);
}
