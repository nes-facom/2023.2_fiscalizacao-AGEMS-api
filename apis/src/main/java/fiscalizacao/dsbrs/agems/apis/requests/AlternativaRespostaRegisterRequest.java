package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "TipoRespostaRegisterRequest",description = "Objeto de Requisição de cadastrode tipo de respostas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlternativaRespostaRegisterRequest {

  @Schema(title = "Resposta",
    description = "Um tipo de resposta da questão em questão",
    required = true,
    type = "string",
    example = "Sim"
  )
  private String descricao;
}
