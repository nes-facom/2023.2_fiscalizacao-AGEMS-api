package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Modelo")
public interface ModeloRepositorio extends CrudRepository<Modelo, Integer> {
   @Operation(summary = "Encontra modelo por nome")
  public Optional<Modelo> findByNome(String nome);
}
