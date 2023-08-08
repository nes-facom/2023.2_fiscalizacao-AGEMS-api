package fiscalizacao.dsbrs.agems.apis.dominio;

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

@Schema(title = "Tipo de Resposta", description = "Objeto de Tipo Resposta")
@Entity
@Table(name = "tipoResposta")
public class TipoResposta {

  @Schema(
    title = "Id",
    description = "Id de Tipo Resposta, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Tipo de Resposta",
    description = "Texto do Tipo de Resposta que o modelo comporta, como - sim, não e não se aplica",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String resposta;

  @Schema(
    title = "Questão",
    description = "Questão a qual a Tipo Resposta se refere",
    required = true,
    implementation = Questao.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idQuestao", nullable = false)
  private Questao questao;


  public TipoResposta(String id) {
    this.id = Integer.parseInt(id);
  }

  public TipoResposta(int id) {
    this.id = id;
  }

   public TipoResposta() {}

  public int getId() {
    return this.id;
  }

  public String getResposta() {
    return this.resposta;
  }

  public void setResposta(String resposta) {
    this.resposta = resposta;
  }

  public Questao getQuestao() {
    return this.questao;
  }

  public void setQuestao(Questao questao) {
    this.questao = questao;
  }
}
