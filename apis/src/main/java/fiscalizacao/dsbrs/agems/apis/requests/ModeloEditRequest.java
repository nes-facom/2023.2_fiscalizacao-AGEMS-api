package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "ModeloEditRequest",
  description = "Objeto de Requisição de edição  de modelo"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeloEditRequest {

  @Schema(
    title = "Id",
    description = "id do modelo",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(
    title = "Nome de Modelo",
    description = "Nome do modelo",
    required = true,
    type = "string",
    example = "Modelo 01"
  )
  private String modeloNome;

  @Schema(
    title = "Questões",
    description = "Conjunto de questões do modelo",
    required = true
  )
  private List<QuestaoEditRequest> questoes;
}
