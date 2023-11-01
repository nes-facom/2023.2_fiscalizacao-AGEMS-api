package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "TipoRespostaEditRequest",
  description = "Objeto de Requisição de edição tipo de respostas"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlternativaRespostaEditRequest {

  @Schema(
    title = "Ação",
    description = "Ação de edição ou deleção de tipo de Resposta",
    required = true,
    type = "string",
    implementation = String.class,
    example = "edit",
    pattern = "edit|delete"
  )
  private String acao;

  @Schema(
    title = "Id",
    description = "Id do Tipo de Resposta",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(title = "Resposta",
    description = "Um tipo de resposta da questão em questão",
    required = true,
    type = "string",
    example = "Sim"
  )
  private String resposta;
}
