package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "TipoRespostaResponse",
  description = "Response de Modelo com questões e tipos de respostas"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoRespostaResponse {

  @Schema(
    title = "Id",
    description = "Id do tipo de resposta da quest\u00E3",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  @JsonProperty("id")
  private int id;

  @Schema(
    title = "Resposta",
    description = "Um tipo de resposta da questão em questão",
    required = true,
    type = "string",
    example = "Sim"
  )
  @JsonProperty("resposta")
  private String resposta;

  @Schema(
    title = "Questão",
    description = "Id da Questão que se refere a instância de Tipo Resposta",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  @JsonProperty("id_questão")
  private int idQuestao;
}
