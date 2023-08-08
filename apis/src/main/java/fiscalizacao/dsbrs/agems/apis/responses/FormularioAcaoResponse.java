package fiscalizacao.dsbrs.agems.apis.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(
  title = "FormularioAcaoResponse",
  description = "Response para Edição ou Deleção de Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FormularioAcaoResponse extends Response {

  @Schema(
    title = "Ação",
    description = "Ação de Edição ou Deleção de Formulário",
    required = true,
    type = "string",
    example = "edit",
    pattern = "edit|delete"
  )
  private String acao;

  @Schema(
    title = "Formulário",
    description = "Instância de Formulário",
    required = true,
    implementation = FormularioResponse.class
  )
  private FormularioResponse formulario;
}
