package fiscalizacao.dsbrs.agems.apis.requests;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fiscalizacao.dsbrs.agems.apis.util.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
    title = "Data de criação",
    description = "Data de criação do formulário",
    required = true,
    type = "string",
    example = "1692840670072"
    )
  @JsonProperty("data_criacao")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime dataCriacao;

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