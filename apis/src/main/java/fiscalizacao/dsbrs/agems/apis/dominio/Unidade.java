package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Unidade", description = "Objeto de Unidade que é fiscalizada.")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unidade")
public class Unidade {

  @Schema(
    title = "Id",
    description = "Id de Unidade, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Nome da Unidade",
    description = "Nome da Unidade",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false, unique = true)
  private String idUnidade;

  @Schema(
    title = "Endereço da Unidade",
    description = "Endereço da Unidade",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String endereco;

  @Schema(
    title = "Tipo da Unidade",
    description = "Tipo da Unidade",
    type = "string",
    implementation = String.class
  )
  private String tipo;

  public Unidade(String idUnidade, String endereco) {
    this.idUnidade = idUnidade;
    this.endereco = endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public void setIdUnidade(String idUnidade) {
    this.idUnidade = idUnidade;
  }

  public String getIdUnidade() {
    return this.idUnidade;
  }

  public String getEndereco() {
    return this.endereco;
  }
}
