package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "ImagemResponse",
  description = "Response para Imagem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ImagemResponse extends Response {

  @Schema(
    title = "Id",
    description = "Código da Imagem",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(
    title = "Id do Formulário",
    description = "Identificador do Formul\u00E1rio a qual a imagem pertence",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  @JsonProperty("formulario")
  private int formulario;

  @Schema(
    title = "Id da Questão",
    description = "Identificador da Questão a qual a imagem pertence",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  @JsonProperty("questao")
  private int questao;

  @Schema(
    title = "Imagem",
    description = "Imagem",
    format = "binary",
    type = "string",
    example = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==",
    required = true
  )
  private String imagem;
}
