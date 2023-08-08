package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "UsuarioEditRequest",description = "Objeto de Requisição de Edição de Usuário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEditRequest {

   @Schema(title = "Nome",
    description = "Nome novo de Usuário",
    required = true,
    type = "string",
    example = "Zezinho da Silva Sauro"
  )
  private String nomeNovo;

  @Schema(
    title = "Senha atual",
    description = "Senha cadastrada",
    required = true,
    type = "string",
    example = "zezinho123%"
  )
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[-+.\\[\\]~_?:!@#$%^&*])[a-zA-Z0-9-+.\\[\\]~_?:!@#$%^&*]{8,}$",
    message = "Senha de formato inválido"
  )
  String senha;

  @Schema(
    title = "Senha",
    description = "Senha nova do Usu\u00E1rio",
    required = true,
    type = "string",
    example = "zezinho123$"
  )
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[-+.\\[\\]~_?:!@#$%^&*])[a-zA-Z0-9-+.\\[\\]~_?:!@#$%^&*]{8,}$",
    message = "Senha de formato inválido"
  )
  String senhaNova;
}
