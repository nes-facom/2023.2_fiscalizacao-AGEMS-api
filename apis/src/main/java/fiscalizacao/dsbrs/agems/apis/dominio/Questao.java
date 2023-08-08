package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "questao")
public class Questao {

  @Schema(
    title = "Id",
    description = "Id de Questão, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer",
    example = "1"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Pergunta",
    description = "Texto da Pergunta",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String pergunta;

  @Schema(
    title = "Objetiva",
    description = "Campo de validação se é objetiva ou não",
    required = true,
    type = "boolean"
  )
  @Column(nullable = false)
  private boolean objetiva;

  @Schema(
    title = "Portaria",
    description = "Texto da Portaria",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column
  private String portaria;

  @Schema(
    title = "Modelo",
    description = "Modelo que contém as Questões",
    required = true,
    implementation = Modelo.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idModelo", nullable = false)
  private Modelo modelo;

  @Schema(
    title = "Tipo de Resposta",
    description = "Texto do tipo de resposta das Questões",
    required = true
  )
  @OneToMany(
    mappedBy = "questao",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<TipoResposta> respostas;

  public List<TipoResposta> getRespostas() {
    return this.respostas;
  }

  public void setModelo(Modelo modelo) {
    this.modelo = modelo;
  }

  public void setRespostas(List<TipoResposta> respostas) {
    this.respostas = respostas;
  }

  public Questao() {}

  public Questao(String id) {
    this.id = Integer.parseInt(id);
  }

  public Questao(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public String getPergunta() {
    return this.pergunta;
  }

  public String getPortaria() {
    return this.portaria;
  }

  public void setPergunta(String pergunta) {
    this.pergunta = pergunta;
  }

  public boolean isObjetiva() {
    return this.objetiva;
  }

  public void setObjetiva(boolean objetiva) {
    this.objetiva = objetiva;
  }

  public void setPortaria(String portaria) {
    this.portaria = portaria;
  }

  public Modelo getModelo() {
    return this.modelo;
  }
}
