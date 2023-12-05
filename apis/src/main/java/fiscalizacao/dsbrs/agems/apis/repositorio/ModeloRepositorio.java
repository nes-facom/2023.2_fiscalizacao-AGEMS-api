package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Modelo")

public interface ModeloRepositorio extends JpaRepository<Modelo, UUID> {
   @Operation(summary = "Encontra modelo por nome")
  public Optional<Modelo> findByNome(String nome);
}
