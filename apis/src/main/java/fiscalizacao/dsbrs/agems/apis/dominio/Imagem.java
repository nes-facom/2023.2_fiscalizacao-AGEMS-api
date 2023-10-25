package fiscalizacao.dsbrs.agems.apis.dominio;

import java.time.LocalDate;

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
    description = "Identificador da Imagem",
    required = true,
    format = "number",
    type = "integer",
    example = "1"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

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
    title = "Questão",
    required = true,
    implementation = Questao.class,
    description = "Questão a qual a imagem pertence"
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_questao")
  private Questao questao;

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
    title = "Data de Criação",
    description = "Data de criação da imagem",
    required = true,
    implementation = LocalDate.class
  )
  @Column
  private LocalDate dataCriacao;

  public void setDate() {
    this.dataCriacao = LocalDate.now();
  }

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
