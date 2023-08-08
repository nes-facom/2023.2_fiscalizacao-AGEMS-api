package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "FormularioRequest",
  description = "Objeto de Requisição de Edição ou Deleção de Formulário "
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormularioRequest {

  @Schema(
    title = "Formulário",
    description = "Identificador do Formulário",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(
    title = "Respostas",
    description = "Conjunto de Respostas do Formulário",
    type = "array"
  )
  private List<RespostaRequest> respostas;

  @Schema(
    title = "Imagens",
    description = "Conjunto de Imagens do Formulário",
    type = "array"
  )
  private List<ImagemAcaoRequest> imagens;

  @Schema(
    title = "Observação",
    description = "Observação do Formulário",
    type = "string"
  )
  private String observacao;
}
