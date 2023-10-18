package fiscalizacao.dsbrs.agems.apis.requests;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.util.LocalDateTimeDeserializer;
import fiscalizacao.dsbrs.agems.apis.util.LocalDateTimeSerializer;
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
    title = "Data de criação",
    description = "Data de criação do usuário",
    required = true,
    type = "number",
    format = "int64",
    example = "1692840670079"
    
  )
  @JsonProperty("data_criacao")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime dataCriacao;
  
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
  private Cargo cargo;

  public void setEmail(String email) {
    if (email.contains("@")) {
      this.email = email;
    } else {
      email = null;
    }
  }
}
