package fiscalizacao.dsbrs.agems.apis.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "ModeloAcaoResponse", description = "Response para Deleção de Modelo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeloAcaoResponse {

  @Schema(
    title = "Ação",
    description = "Ação de Edição ou Deleção de Modelo",
    required = true,
    type = "string",
    example = "edit",
    pattern = "edit|delete"
  )
  private String acao;

 @Schema(
    title = "Modelo",
    description = "Instância de Modelo",
    required = true,
    implementation = ModeloResponse.class
  )
  private ModeloResponse modelo;
}
