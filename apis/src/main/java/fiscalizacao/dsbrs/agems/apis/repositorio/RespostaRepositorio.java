package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Resposta")
public interface RespostaRepositorio
  extends CrudRepository<Resposta, UUID> {
  @Operation(summary = "Encontra respostas por formulário")
  public List<Resposta> findByFormulario(Formulario formulario);
}
