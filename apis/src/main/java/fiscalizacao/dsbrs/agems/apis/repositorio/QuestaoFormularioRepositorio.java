package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoFormulario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Questão")
public interface QuestaoFormularioRepositorio extends CrudRepository<QuestaoFormulario, Integer> {
   @Operation(summary = "Encontra questões por formulario")
  public List<QuestaoFormulario> findByFormulario(Formulario formulario);
}
