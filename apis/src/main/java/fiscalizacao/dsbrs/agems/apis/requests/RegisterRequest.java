package fiscalizacao.dsbrs.agems.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
  title = "RegisterRequest",
  description = "Objeto de Requisição de Cadastro de Usuário"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @Schema(
    title = "Nome",
    description = "Nome de Usuário",
    required = true,
    type = "string",
    example = "Zezinho da Silva Sauro"
  )
  private String nome;

  @Schema(
    title = "Email",
    description = "Email de Usuário",
    required = true,
    type = "string",
    example = "zezinho.silva@gmail.com"
  )
  @Pattern(
    regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$",
    message = "Email Inválido"
  )
  private String email;

  @Schema(
    title = "Senha",
    description = "Senha de Usuário",
    required = true,
    type = "string",
    example = "zezinho123%"
  )
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[-+.\\[\\]~_?:!@#$%^&*])[a-zA-Z0-9-+.\\[\\]~_?:!@#$%^&*]{8,}$",
    message = "Senha de formato inválido"
  )
  private String senha;

  @Schema(
    title = "Cargo",
    description = "Cargo de Usuário",
    required = true,
    type = "string",
    example = "Analista de Regulação"
  )
  private String cargo;

  public String getCARGOS(String cargo) {
    final String CARGOS[] = {
      "Coordenador",
      "Analista de Regulação",
      "Assessor Técnico",
      "Assessor Jurídico",
    };
    for (int i = 0; i < CARGOS.length; i++) {
      if (CARGOS[i].equalsIgnoreCase(cargo)) {
        return CARGOS[i];
      }
    }
    return null;
  }

  public void setEmail(String email) {
    if (email.contains("@")) {
      this.email = email;
    } else {
      email = null;
    }
  }
}
