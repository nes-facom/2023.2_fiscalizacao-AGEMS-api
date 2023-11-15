package fiscalizacao.dsbrs.agems.apis.requests;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "ModeloRegisterRequest",description = "Objeto de Requisição de Cadastro de Modelo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeloRegisterRequest {
  
  @Schema(
    title = "UUID gerado no app",
    description = "Código UUID gerado no banco local do aplicativo",
    required = false,
    format = "string",
    type = "UUID"
  )
  private UUID uuidLocal;
  
  @Schema(
    description = "Nome do modelo",
    required = true,
    type = "string",
    example = "Modelo 01"
  )
  private String nome;

  @Schema(description = "Conjunto de questões do modelo", required = true)
  private List<QuestaoRegisterRequest> questoes;
}
