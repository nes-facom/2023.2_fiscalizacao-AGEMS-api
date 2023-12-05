package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Questão")
public interface QuestaoRepositorio extends CrudRepository<Questao, UUID> {
}
