package fiscalizacao.dsbrs.agems.apis.repositorio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Interface Repositório de Formulário")
public interface FormularioRepositorio
  extends JpaRepository<Formulario, UUID> {
  @Operation(summary = "Encontra formulários por usuário de criação")
  public List<Formulario> findByUsuarioCriacao(Usuario usuario);
}
