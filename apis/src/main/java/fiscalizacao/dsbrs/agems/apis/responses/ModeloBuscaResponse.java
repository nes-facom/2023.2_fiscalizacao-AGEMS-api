package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ModeloBuscaResponse extends Response {
  @Schema(
      title = "Página",
      description = "Índice da página atual",
      required = true,
      format = "number",
      type = "int"
    )
  @JsonProperty("pagina")
  private int pagina;
  
  @Schema(
      title = "Página Máxima",
      description = "Índice máximo de páginas com a quantidade atual especificada",
      required = true,
      format = "number",
      type = "int"
    )
  @JsonProperty("paginaMax")
  private int paginaMax;
  
  @Schema(
      title = "Modelos",
      description = "Conjunto de Modelos obtidos",
      required = true,
      type = "array"
    )
  @JsonProperty("modelos")
  private List<ModeloResumidoResponse> data;
}
