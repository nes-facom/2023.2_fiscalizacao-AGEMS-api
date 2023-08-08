package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "ImagemAcaoRequest",
  description = "Requisição para Edição ou Deleção de Imagem"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagemAcaoRequest {

  @Schema(
    title = "Ação",
    description = "Ação de Edição ou Deleção",
    required = true,
    type = "string",
    example = "edit",
    pattern = "edit|delete"
  )
  private String acao;

  @Schema(
    title = "Id",
    description = "Id da Imagem",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

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
