package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "Authentication Request",
  description = "Objeto de Requisição de Autenticação de Usuário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  @Schema(
    title = "Email",
    description = "Email cadastrado do Usuário",
    required = true,
    type = "string",
    format = "email",
    example = "zezinho.silva@gmail.com"
  )
  @Pattern(
    regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$",
    message = "Email Inválido"
  )
  private String email;

  @Schema(
    title = "Senha",
    description = "Senha cadastrada do Usuário",
    required = true,
    type = "string",
    format = "password",
    example = "zezinho123%"
  )
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[-+.\\[\\]~_?:!@#$%^&*])[a-zA-Z0-9-+.\\[\\]~_?:!@#$%^&*]{8,}$",
    message = "Senha de formato inválido"
  )
  String senha;
}
