package fiscalizacao.dsbrs.agems.apis.dominio.chaves;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class QuestaoFormularioKey implements Serializable {
  private static final long serialVersionUID = -5546187824097610574L;

  private Integer idQuestao;
  private Integer idFormulario;
}
