package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import fiscalizacao.dsbrs.agems.apis.dominio.chaves.RespostaKey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Resposta")
public interface RespostaRepositorio
  extends CrudRepository<Resposta, RespostaKey> {
  @Operation(summary = "Encontra respostas por formulário")
  public List<Resposta> findByFormulario(Formulario formulario);
}
