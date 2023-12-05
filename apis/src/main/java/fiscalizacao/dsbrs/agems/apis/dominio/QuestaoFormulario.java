package fiscalizacao.dsbrs.agems.apis.dominio;

import java.util.UUID;

import fiscalizacao.dsbrs.agems.apis.dominio.chaves.QuestaoFormularioKey;
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

@Schema(title = "QuestaoFormulario", description = "Objeto de QuestaoFormulario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questaoFormulario")
public class QuestaoFormulario {

  @Schema(
    title = "Id",
    description = "Id de QuestaoFormulario",
    required = true,
    format = "number",
    type = "integer"
  )
  @EmbeddedId
  private QuestaoFormularioKey id;

  @Schema(
    title = "Id",
    description = "Id da questão",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Questao.class)
  @MapsId("idQuestao")
  @JoinColumn(name = "idQuestao")
  private Questao questao;

  @Schema(
    title = "Id",
    description = "Id do formulário",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Formulario.class)
  @MapsId("idFormulario")
  @JoinColumn(name = "idFormulario")
  private Formulario formulario;
  
  public void setQuestao(Questao questao) {
	  this.questao = questao;
	  this.setKeyQuestao(questao == null ? null : questao.getId());
  }
  
  public void setFormulario(Formulario formulario) {
	  this.formulario = formulario;
	  this.setKeyFormulario(formulario == null ? null : formulario.getId());
  }
  
  private void setKeyQuestao(UUID questaoId) {
    this.initializeKey();
    this.id.setIdQuestao(questaoId);
  }
  
  private void setKeyFormulario(UUID formularioId) {
    this.initializeKey();
    this.id.setIdFormulario(formularioId);
  }
  
  private void initializeKey() {
	  if(this.id == null)
		  this.id = new QuestaoFormularioKey();
  }
}