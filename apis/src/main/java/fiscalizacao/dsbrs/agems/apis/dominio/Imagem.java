package fiscalizacao.dsbrs.agems.apis.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Imagem", description = "Objeto de Imagem de Formulário")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imagem")
public class Imagem {

  @Schema(
    title = "Id da imagem",
    description = "Identificador da Imagem, autogerável no banco",
    required = true,
    format = "string",
    type = "UUID"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(
    title = "Formulário",
    description = "Formulário a qual a imagem pertence",
    required = true,
    implementation = Formulario.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_formulario", nullable = false)
  private Formulario formulario;

  @Schema(
    title = "Id do usuário de criação",
    description = "Id de usuário reponsável pela inserção da imagem",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioCriacao")
  private Usuario usuarioCriacao;

  @Schema(
    title = "Id do usuário de alteração",
    description = "Id de usuário reponsável pela última alteração da imagem",
    required = true,	
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioAlteracao")
  private Usuario usuarioAlteracao;

  @Schema(
    title = "Imagem (Base64)",
    implementation = String.class,
    description = "Imagem em formato Base64",
    format = "binary",
    type = "string",
    example = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==",
    required = true
  )
  @Column(nullable = false)
  private byte[] imagem;

  @Schema(
    title = "Data de criação",
    description = "Data de criação da imagem",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataCriacao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Data de alteração",
    description = "Data de alteração da imagem",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataAlteracao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataAlteracao;
  
  public void setImagem(String base64Image) {
    this.imagem = Base64.decodeBase64(base64Image);
  }

  public String getImagem() {
    return Base64.encodeBase64String(this.imagem);
  }
  
  public byte[] getImagemBytes() {
    return this.imagem;
  }
}
