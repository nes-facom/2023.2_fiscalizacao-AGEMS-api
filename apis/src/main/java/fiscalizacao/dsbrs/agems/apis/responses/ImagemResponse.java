package fiscalizacao.dsbrs.agems.apis.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(title = "ImagemResponse",
  description = "Response para Imagem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ImagemResponse extends Response {

  @Schema(
    title = "Id",
    description = "Código da Imagem",
    required = true,
    format = "string",
    type = "UUID"
  )
  @JsonProperty("uuid")
  private UUID id;

  @Schema(
    title = "Id do Formulário",
    description = "Identificador do Formulário a qual a imagem pertence",
    required = true,
    format = "string",
    type = "UUID"
  )
  @JsonProperty("formulario")
  private UUID formulario;

  @Schema(
    title = "Imagem",
    description = "Imagem",
    format = "binary",
    type = "string",
    example = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==",
    required = true
  )
  private String imagem;
}
