package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "ModeloListResponse",
  description = "Response de Modelo com questões e tipos de respostas"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeloResumidoResponse {

  @Schema(description = "id do modelo", required = true)
  @JsonProperty("UUID")
  private UUID id;

  @Schema(description = "nome do Modelo", required = true)
  @JsonProperty("nome")
  private String nome;
}
