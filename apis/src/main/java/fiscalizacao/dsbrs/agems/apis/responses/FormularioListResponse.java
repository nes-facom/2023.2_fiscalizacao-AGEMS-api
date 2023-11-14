package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "FormularioListResponse", description = "Response para Formulário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FormularioListResponse extends Response {
  @Schema(
      title = "Dados",
      description = "Conjunto de Formulários obtidos",
      required = true,
      type = "array"
    )
  @JsonProperty("data")
  private List<FormularioResponse> data;
}
