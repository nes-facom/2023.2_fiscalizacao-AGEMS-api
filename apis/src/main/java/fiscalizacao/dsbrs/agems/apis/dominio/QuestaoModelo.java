package fiscalizacao.dsbrs.agems.apis.dominio;

import fiscalizacao.dsbrs.agems.apis.dominio.chaves.QuestaoModeloKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Formulário", description = "Objeto de Formulário")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao_modelo")
public class QuestaoModelo {

  @Schema(
    title = "Id",
    description = "Id de QuestaoModelo",
    required = true,
    format = "number",
    type = "integer"
  )
  @EmbeddedId
  private QuestaoModeloKey id;

  @Schema(
    title = "Id",
    description = "Id da questão",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Questao.class)
  @MapsId("idQuestao")
  @JoinColumn(name = "id_questao")
  private Questao questao;

  @Schema(
    title = "Id",
    description = "Id da modelo",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Modelo.class)
  @MapsId("idModelo")
  @JoinColumn(name = "id_modelo")
  private Modelo modelo;
  
  public void setQuestao(Questao questao) {
	  this.questao = questao;
	  this.setKeyQuestao(questao == null ? null : questao.getId());
  }
  
  public void setModelo(Modelo modelo) {
	  this.modelo = modelo;
	  this.setKeyModelo(modelo == null ? null : modelo.getId());
  }
  
  private void setKeyQuestao(Integer questaoId) {
    this.initializeKey();
    this.id.setIdQuestao(questaoId);
  }
  
  private void setKeyModelo(Integer modeloId) {
    this.initializeKey();
    this.id.setIdModelo(modeloId);
  }
  
  private void initializeKey() {
	  if(this.id == null)
		  this.id = new QuestaoModeloKey();
  }
}