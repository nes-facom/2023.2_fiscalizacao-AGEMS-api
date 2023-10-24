package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "UnidadeRequest",
  description = "Requisição de Unidade do Formulário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeRequest {

  @Schema(
    title = "Nome",
    description = "Nome da unidade",
    required = true,
    type = "string",
    example = "Unidade 1 de Tratamento de Esgoto de Dourados"
  )
  private String nome;

  @Schema(
    title = "Endereço",
    description = "Endereço da unidade",
    required = true,
    type = "string",
    example = "Rua das Neves, 114"
  )
  private String endereco;

  @Schema(
    title = "Tipo",
    description = "Tipo da unidade",
    required = true,
    type = "string",
    example = "Tratamento de Esgoto"
  )
  private String tipo;
}
