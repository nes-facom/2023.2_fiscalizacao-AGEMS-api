package fiscalizacao.dsbrs.agems.apis.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fiscalizacao.dsbrs.agems.apis.util.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "FormularioResumoResponse",
  description = "Response Resumo para Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormularioResumoResponse extends Response {

  @Schema(
    title = "Id",
    description = "Código do Formulário",
    required = true,
    format = "string",
    type = "UUID"
  )
  @JsonProperty("id")
  private UUID id;
  
  @Schema(
    title = "Unidade",
    description = "Nome da Unidade do Formulário",
    type = "string"
  )
  private String unidade;
  
  @Schema(
    title = "Data de criação",
    description = "Data de criação do formulário",
    required = true,
    type = "number",
    example = "1692840670072"
  )
  @JsonProperty("data_criacao")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime dataCriacao;
  
  @Schema(
    title = "Usuário de Criação",
    description = "Usuário que criou o Formulário",
    type = "string"
  )
  @JsonProperty("usuario_criacao")
  private String usuarioCriacao;
}
