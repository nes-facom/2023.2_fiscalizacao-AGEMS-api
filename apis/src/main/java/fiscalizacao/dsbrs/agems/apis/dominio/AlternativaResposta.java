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

@Schema(title = "Alternativa de uma Resposta",description = "Objeto de AlternativaResposta")
@Entity
@Table(name = "alternativaResposta")
public class AlternativaResposta {

  @Schema(
    title = "Id",
    description = "Id da alternativa da Resposta, autogerável no banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Decrição da alternativa",
    description = "Texto da descrição da alternativa de uma resposta. Exemplo: sim, não e não se aplica",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false, name="descricao")
  private String descricao;

  @Schema(
    title = "Questão",
    description = "Id da questão à qual a alternativa se refere",
    required = true,
    implementation = Questao.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idQuestao", nullable = false)
  private Questao questao;
}
