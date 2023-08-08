package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(
  title = "ModeloFormResponse",
  description = "Response de Modelo atrelado ao Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ModeloFormResponse extends Response {

  @Schema(
    title = "Nome do Modelo",
    description = "nome do Modelo",
    required = true
  )
  @JsonProperty("nome")
  private String nome;

  @Schema(
    title = "Questões",
    description = "Conjunto de questões do Modelo",
    required = true
  )
  @JsonProperty("questões")
  private List<QuestaoResponse> questoes;
}
