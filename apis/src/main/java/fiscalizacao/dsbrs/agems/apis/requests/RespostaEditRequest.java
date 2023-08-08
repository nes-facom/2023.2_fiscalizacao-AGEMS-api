package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "RespostaEditRequest",
  description = "Requisição de Resposta do Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespostaEditRequest {

  @Schema(
    title = "Questão",
    description = "Id da Questão que se refere a Resposta",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int questao;

  @Schema(
    title = "Resposta",
    description = "Resposta para a questão do Formulário (objetiva, subjetiva ou múltipla)",
    required = true
  )
  private String resposta;

  @Schema(
    title = "Observação",
    description = "Observação para a questão do Formulário",
    type = "string",
    example = "Em conformidade."
  )
  private String obs;
}
