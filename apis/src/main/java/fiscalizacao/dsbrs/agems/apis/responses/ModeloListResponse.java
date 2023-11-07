package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.List;
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
public class ModeloListResponse {

  @Schema(description = "Lista de ModeloResponse", required = true)
  @JsonProperty("data")
  private List<ModeloResponse> data;
}
