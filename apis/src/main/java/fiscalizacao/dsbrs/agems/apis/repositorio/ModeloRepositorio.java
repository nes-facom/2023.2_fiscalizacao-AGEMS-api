package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Modelo")
public interface ModeloRepositorio extends CrudRepository<Modelo, Integer> {
   @Operation(summary = "Encontra Modelo por Nome")
  public Optional<Modelo> findByModeloNome(String modeloNome);
}
