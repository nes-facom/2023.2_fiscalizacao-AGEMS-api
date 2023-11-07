package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Questão")
public interface QuestaoModeloRepositorio extends CrudRepository<QuestaoModelo, Integer> {
  @Operation(summary = "Encontra questões-modelo por modelo")
 public List<QuestaoModelo> findByModelo(Modelo modelos);
  
  @Operation(summary = "Encontra questões-modelo por questão")
 public List<QuestaoModelo> findByQuestao(Questao questao);
}
