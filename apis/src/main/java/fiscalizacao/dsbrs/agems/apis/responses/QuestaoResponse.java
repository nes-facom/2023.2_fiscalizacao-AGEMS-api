package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(
  title = "QuestaoResponse",
  description = "Response de Questão atrelada ao Modelo"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QuestaoResponse extends Response {

  @Schema(
    title = "Id",
    description = "Id da Questão",
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
    title = "Pergunta",
    description = "Pergunta para a questão do Modelo",
    required = true
  )
  @JsonProperty("pergunta")
  private String pergunta;

  @Schema(
    title = "Objetiva",
    description = "boolean que diz se a questão é objetiva",
    required = true
  )
  @JsonProperty("objetiva")
  private boolean objetiva;

  @Schema(
    title = "Portaria",
    description = "Portaria a qual a questão faz referência",
    type = "string",
    implementation = String.class,
    example = "Portaria 01"
  )
  @JsonProperty("portaria")
  private String portaria;

  @Schema(
    title = "Tipo Respostas",
    description = "Conjunto de respostas da questão",
    required = true,
    type = "array"
  )
  @JsonProperty("respostas")
  private List<AlternativaRespostaResponse> respostas;
}
