package fiscalizacao.dsbrs.agems.apis.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "FormularioResumoResponse",
  description = "Response Resumo para Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormularioResumoResponse extends Response {

  @Schema(
    title = "Id",
    description = "Código do Formulário",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;
}
