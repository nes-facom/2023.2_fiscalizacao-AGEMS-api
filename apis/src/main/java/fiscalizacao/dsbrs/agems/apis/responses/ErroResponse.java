package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(
  title = "ErroResponse",
  description = "Response para Erros dos Endpoints"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ErroResponse extends Response {

  @Schema(
    title = "Status",
    description = "Código do Erro",
    required = true,
    format = "number",
    type = "int",
    minimum = "400",
    maximum = "599",
    defaultValue = "0"
  )
  @ValidStatus
  @JsonProperty("status")
  private int status;

  @Schema(
    title = "Erro",
    description = "Mensagem do Erro",
    required = true,
    type = "string",
    example = "Isso é uma mensagem de erro"
  )
  @JsonProperty("error")
  private String erro;
}
