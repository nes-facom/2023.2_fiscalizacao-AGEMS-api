package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Unidade")
public interface UnidadeRepositorio extends CrudRepository<Unidade, Integer> {
   @Operation(summary = "Encontra Unidade pelo nome da Unidade")
  Optional<Unidade> findByIdUnidade(String idUnidade);
}
