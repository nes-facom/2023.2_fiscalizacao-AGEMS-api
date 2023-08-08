package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Tipo Resposta")
public interface TipoRespostaRepositorio
  extends CrudRepository<TipoResposta, Integer> {
     @Operation(summary = "Encontra Tipo Resposta por Questao")
  public List<TipoResposta> findByQuestao(Questao questao);
}
