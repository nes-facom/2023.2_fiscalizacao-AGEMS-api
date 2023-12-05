package fiscalizacao.dsbrs.agems.apis.repositorio;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Imagem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Interface Repositório de Imagem")
public interface ImagemRepositorio extends CrudRepository<Imagem, Integer> {
   @Operation(summary = "Encontra imagens por formulário")
  public List<Imagem> findByFormulario(Formulario formulario);
}
