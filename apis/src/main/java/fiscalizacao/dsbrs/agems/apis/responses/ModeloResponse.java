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
  title = "ModeloResponse",
  description = "Response de Modelo com questões e tipos de respostas"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeloResponse {

  @Schema(
    title = "Id",
    description = "id do modelo",
    required = true,
    format = "string",
    type = "UUID",
    example = "1"
  )
  @JsonProperty("id")
  private UUID id;

  @Schema(
    title = "Nome de Modelo",
    description = "Nome do modelo",
    required = true,
    type = "string",
    example = "Modelo 01"
  )
  @JsonProperty("nome")
  private String nome;

  @Schema(
    title = "Questões",
    description = "Conjunto de questões do modelo",
    required = true
  )
  @JsonProperty("questões")
  private List<QuestaoResponse> questoes;
}
