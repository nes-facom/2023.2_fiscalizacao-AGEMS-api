package fiscalizacao.dsbrs.agems.apis.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fiscalizacao.dsbrs.agems.apis.util.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "FormularioResponse", description = "Response para Formulário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FormularioResponse extends Response {

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
    title = "Id",
    description = "Código do Formulário",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(
    title = "Usuário",
    description = "Response de Usuário que criou o Formulário",
    required = true,
    implementation = UsuarioFormResponse.class
  )
  @JsonProperty("usuario")
  private UsuarioFormResponse usuario;

  @Schema(
    title = "Unidade",
    description = "Response de Unidade atrelada ao Formulário",
    required = true,
    implementation = UnidadeResponse.class
  )
  @JsonProperty("unidade")
  private UnidadeResponse unidade;

  @Schema(
    title = "Respostas",
    description = "Conjunto de Responses de Respostas atreladas ao Formulário",
    required = true,
    type = "array"
  )
  @JsonProperty("respostas")
  private List<RespostaResponse> respostas;

  @Schema(
    title = "Imagens",
    description = "Conjunto de Responses de Imagens atreladas ao Formulário",
    required = true,
    type = "array"
  )
  @JsonProperty("imagens")
  private List<ImagemResponse> imagens;

  @Schema(
    title = "Observação",
    description = "Observação do Formulário",
    type = "string"
  )
  private String observacao;
}
