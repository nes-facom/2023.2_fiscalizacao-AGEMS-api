package fiscalizacao.dsbrs.agems.apis.dominio;

import fiscalizacao.dsbrs.agems.apis.dominio.chaves.QuestaoModeloKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Formulário", description = "Objeto de Formulário")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao_modelo")
public class QuestaoModelo {

  @Schema(
    title = "Id",
    description = "Id de QuestaoModelo",
    required = true,
    format = "number",
    type = "integer"
  )
  @EmbeddedId
  private QuestaoModeloKey id;

  @Schema(
    title = "Id",
    description = "Id da questão",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Questao.class)
  @MapsId("questaoId")
  @JoinColumn(name = "idQuestao")
  private Questao questao;

  @Schema(
    title = "Id",
    description = "Id da modelo",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Modelo.class)
  @MapsId("modeloId")
  @JoinColumn(name = "idModelo")
  private Modelo modelo;
}