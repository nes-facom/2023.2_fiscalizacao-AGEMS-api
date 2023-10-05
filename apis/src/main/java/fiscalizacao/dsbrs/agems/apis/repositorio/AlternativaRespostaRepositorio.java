package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Tipo Resposta")
public interface AlternativaRespostaRepositorio
  extends CrudRepository<AlternativaResposta, Integer> {
     @Operation(summary = "Encontra alternativas por questao")
  public List<AlternativaResposta> findByQuestao(Questao questao);
}
