package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "UsuarioFormResponse", description = "Usuário atrelado ao Formulário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UsuarioFormResponse extends Response {

  @Schema(title = "Nome",
    description = "Nome de Usuário",
    required = true,
    type = "string",
    example = "Zezinho da Silva Sauro"
  )
  @JsonProperty("nome")
  private String nome;
}
