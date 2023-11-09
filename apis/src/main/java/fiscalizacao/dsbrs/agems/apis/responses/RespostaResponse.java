package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "RespostaResponse", description = "Response de Resposta atrelada ao Formulário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RespostaResponse extends Response {

  @Schema(
    title = "Id",
    description = "Id da Resposta",
    required = true,
    format = "string",
    type = "UUID"
  )
  @JsonProperty("uuid")
  private UUID id;
  
  @Schema(
    title = "UUID gerado no app",
    description = "Código UUID gerado no banco local do aplicativo",
    required = false,
    format = "string",
    type = "UUID"
  )
  @JsonProperty("uuid_local")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private UUID uuidLocal;

  @Schema(
    title = "Questão",
    description = "Id da Questão que se refere a Resposta",
    required = true,
    format = "string",
    type = "UUID"
  )
  private UUID questao;

   @Schema(title = "Resposta",
    description = "Resposta para a questão do Formulário (objetiva, subjetiva ou múltipla)",
    required = true,
    type = "string",
    example = "Sim"
  )
  @JsonProperty("resposta")
  private String resposta;

  @Schema(title = "Observação" ,
    description = "Observação para a questão do Formulário",
    type = "string",
    example = "Em conformidade."
  )
  @JsonProperty("obs")
  private String obs;
}
