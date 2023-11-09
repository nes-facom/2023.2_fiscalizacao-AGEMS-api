package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Schema(
  title = "UnidadeResponse",
  description = "Response de Unidade atrelada ao Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UnidadeResponse extends Response {

  @Schema(
    title = "Id",
    description = "Id da Unidade",
    required = true,
    format = "number",
    type = "UUID",
    example = "1"
  )
  @JsonProperty("id")
  private UUID id;

  @Schema(
    title = "Id Unidade",
    description = "Nome da Unidade",
    required = true,
    type = "String",
    example = "Unidade 1 de Tratamento de Esgoto de Dourados"
  )
  @JsonProperty("nome")
  private String nome;

  @Schema(
    title = "Endere\u00E7o",
    description = "Endereço da Unidade",
    required = true,
    type = "String",
    example = "Rua das Neves, 114"
  )
  @JsonProperty("endereco")
  private String endereco;

  @Schema(
    title = "Tipo",
    description = "Tipo da unidade",
    required = true,
    type = "string",
    example = "Tratamento de Esgoto"
  )
  @JsonProperty("tipo")
  private String tipo;
}
