package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "FormularioRegisterRequest",description = "Objeto de Requisição de Cadastro de Formulário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormularioRegisterRequest {


  @Schema(
    title = "Modelo",
    description = "Modelo que se refere o Formulário",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int modelo;

  @Schema(
    title = "Unidade",
    description = "Unidade analisada pelo Formulário",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int unidade;

  @Schema(
    title = "Respostas",
    description = "Conjunto de respostas do Formulário",
    required = true,
    type = "array"
  )
  private List<RespostaRequest> respostas;

  @Schema(
    title = "Imagens",
    description = "Conjunto de imagens do Formulário",
    required = true,
    type = "array"
  )
  private List<ImagemRegisterRequest> imagens;

  @Schema(
    title = "Observação",
    description = "Observação do Formulário",
    type = "string"
  )
  private String observacao;
}
