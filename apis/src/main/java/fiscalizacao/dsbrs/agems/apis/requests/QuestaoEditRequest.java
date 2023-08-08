package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "QuestaoEditRequest",
  description = "Requisição de edição de questão"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestaoEditRequest {

  @Schema(
    title = "Ação",
    description = "Ação de edição ou deleção da questão",
    required = true,
    type = "string",
    implementation = String.class,
    example = "edit",
    pattern = "edit|delete"
  )
  private String acao;

  @Schema(
    title = "Id",
    description = "id da pergunta",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(title = "Pergunta", description = "Pergunta", required = true)
  private String pergunta;

  @Schema(
    title = "Objetiva",
    description = "boolean que diz se a questão é objetiva",
    required = true
  )
  private Boolean objetiva;

  @Schema(
    title = "Portaria",
    description = "Portaria a qual a questão faz referência",
    type = "string",
    implementation = String.class,
    example = "Portaria 01"
  )
  private String portaria;

  @Schema(
    title = "Tipo Respostas",
    description = "Conjunto de respostas da questão",
    required = true,
    type = "array"
  )
  private List<TipoRespostaEditRequest> tipoRespostas;
}
