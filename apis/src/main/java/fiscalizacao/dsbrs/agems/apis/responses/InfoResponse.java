package fiscalizacao.dsbrs.agems.apis.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fiscalizacao.dsbrs.agems.apis.util.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "InfoResponse", description = "Objeto de Resposta de Informações de Usuário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InfoResponse extends Response {

  @JsonProperty("id")
  @Schema(
    title = "Id",
    description = "Id do Usuário",
    required = true,
    format = "number",
    type = "int",
    example = "1"
  )
  private int id;

  @Schema(title = "Nome",
    description = "Nome de Usuário",
    required = true,
    type = "string",
    example = "Zezinho da Silva Sauro"
  )
  @JsonProperty("nome")
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
  @JsonProperty("email")
  private String email;

  @Schema(
    title = "Senha",
    description = "Senha de Usuário",
    required = true,
    type = "string",
    example = "$2a$10$qERHTVPdF62JZtzkgFmDC.KNU1cHjEc89P4td2R99B8B5.CycC9o6"
  )
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[-+.\\[\\]~_?:!@#$%^&*])[a-zA-Z0-9-+.\\[\\]~_?:!@#$%^&*]{8,}$",
    message = "Senha de formato inválido"
  )
  @JsonProperty("senha")
  private String senha;

   @Schema(
    title = "Cargo",
    description = "Cargo de Usuário",
    required = true,
    type = "string",
    example = "Analista de Regulação"
  )
  @JsonProperty("cargo")
  private String cargo;

   @Schema(
    title = "Data de criação",
    description = "Data de criação do usuário",
    required = true,
    type = "string",
    example = "1692840670072"
  )
  @JsonProperty("data_criacao")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime dataCriacao;
}
