package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Unidade")
public interface UnidadeRepositorio extends CrudRepository<Unidade, UUID> {
	@Operation(summary = "Encontra Unidade pelo nome da Unidade")
	Optional<Unidade> findByNome(String nome);
}
