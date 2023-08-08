package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Schema(title = "Modelo", description = "Objeto de Modelo")
@Entity
@Table(name = "modelo")
public class Modelo {

  @Schema(
    title = "Id",
    description = "Id de Modelo, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Nome do Modelo",
    description = "Nome do Modelo",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String modeloNome;

  @Schema(
    title = "Lista de Perguntas",
    description = "Questões associadas a um Modelo",
    required = true,
    type = "array"
  )
  @OneToMany(
    mappedBy = "modelo",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Questao> perguntas;

  public List<Questao> getPerguntas() {
    return this.perguntas;
  }

  public void setPerguntas(List<Questao> perguntas) {
    this.perguntas = perguntas;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setModeloNome(String modeloNome) {
    this.modeloNome = modeloNome;
  }

  public int getId() {
    return id;
  }

  public String getModeloNome() {
    return modeloNome;
  }
}
