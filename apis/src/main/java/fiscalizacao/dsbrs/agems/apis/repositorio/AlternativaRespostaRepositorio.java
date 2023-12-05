package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Tipo Resposta")
public interface AlternativaRespostaRepositorio
  extends CrudRepository<AlternativaResposta, UUID> {
     @Operation(summary = "Encontra alternativas por questao")
  public List<AlternativaResposta> findByQuestao(Questao questao);
}
