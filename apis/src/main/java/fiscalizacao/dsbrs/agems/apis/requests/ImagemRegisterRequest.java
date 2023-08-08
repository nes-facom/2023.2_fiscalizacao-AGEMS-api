package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "ImagemRegisterRequest",
  description = "Objeto de Requisição de Cadastro de Imagem de Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagemRegisterRequest {

  @Schema(
    title = "Questão",
    description = "Identificador da Questão a qual a imagem pertence",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
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
