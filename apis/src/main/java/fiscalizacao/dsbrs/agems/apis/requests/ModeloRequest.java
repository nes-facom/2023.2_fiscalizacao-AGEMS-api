package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "ModeloRequest",
  description = "Objeto de Requisição de Deleção de Modelo "
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeloRequest {

  @Schema(
    title = "Id",
    description = "Identificador do Modelo",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(
    title = "Nome de Modelo",
    description = "nome do modelo",
    required = true,
    type = "string",
    example = "Modelo 01"
  )
  private String modeloNome;
}
