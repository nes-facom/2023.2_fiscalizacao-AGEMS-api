package fiscalizacao.dsbrs.agems.apis.dominio.chaves;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuestaoModeloKey implements Serializable {
  private static final long serialVersionUID = -2246871269106793175L;

  @Column(name = "id_questao")
  private Integer questaoId;

  @Column(name = "id_modelo")
  private Integer modeloId;
}
