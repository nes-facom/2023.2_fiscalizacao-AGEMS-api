package fiscalizacao.dsbrs.agems.apis.dominio;

import fiscalizacao.dsbrs.agems.apis.dominio.chaves.RespostaKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Resposta", description = "Objeto de Resposta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(RespostaKey.class)
@Table(name = "resposta")
public class Resposta {

  @Schema(
    title = "Id",
    description = "Id de Resposta, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idFormulario", nullable = false)
  private Formulario formulario;

  @Schema(
    title = "Questão",
    description = "Questão a qual a Resposta responde",
    required = true,
    implementation = Questao.class
  )
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idQuestao", nullable = false)
  private Questao questao;

  @Schema(
    title = "Resposta",
    description = "Texto de Resposta",
    required = true,
    type = "string",
    implementation = String.class
  )
  private String resposta;

  @Schema(
    title = "Observação",
    description = "Texto de Observação",
    required = true,
    type = "string",
    implementation = String.class
  )
  private String obs;
}
