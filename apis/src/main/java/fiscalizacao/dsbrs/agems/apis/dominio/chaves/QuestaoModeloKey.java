package fiscalizacao.dsbrs.agems.apis.dominio.chaves;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class QuestaoModeloKey implements Serializable {
  private static final long serialVersionUID = -2246871269106793175L;

  private UUID idQuestao;
  private UUID idModelo;
}
