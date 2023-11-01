package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Questão")
public interface QuestaoModeloRepositorio extends CrudRepository<QuestaoModelo, Integer> {
   @Operation(summary = "Encontra questões por modelo")
  public List<QuestaoModelo> findByModelo(Modelo modelos);
}
