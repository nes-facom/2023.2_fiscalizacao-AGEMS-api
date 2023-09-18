package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
    title = "Modelo",
    description = "Response de Modelo atrelado ao Formulário",
    required = true,
    implementation = ModeloFormResponse.class
  )
  @JsonProperty("modelo")
  private ModeloFormResponse modelo;

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
