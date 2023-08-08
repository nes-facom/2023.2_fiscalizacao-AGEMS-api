package fiscalizacao.dsbrs.agems.apis.dominio.chaves;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

public class RespostaKey implements Serializable {

  private Formulario formulario;
  private Questao questao;
}
