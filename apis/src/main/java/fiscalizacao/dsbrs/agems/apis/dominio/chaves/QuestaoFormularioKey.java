package fiscalizacao.dsbrs.agems.apis.dominio.chaves;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuestaoFormularioKey implements Serializable {
  private static final long serialVersionUID = -5546187824097610574L;

  @Column(name = "id_questao")
  private Integer questaoId;

  @Column(name = "id_formulario")
  private Integer formularioId;
}
