package fiscalizacao.dsbrs.agems.apis.dominio;

import java.time.LocalDateTime;

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
@Table(name = "formulario")
public class Formulario {

  @Schema(
    title = "Id",
    description = "Id de Formulário, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Usuário",
    description = "Usuário que criou o Formulário",
    required = true,
    implementation = Usuario.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  @Schema(
    title = "Modelo",
    description = "Modelo que origina o Formulário",
    required = true,
    implementation = Modelo.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_modelo")
  private Modelo modelo;

  @Schema(
    title = "Unidade",
    description = "Unidade que o Formulário analisa",
    required = true,
    implementation = Unidade.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_unidade")
  private Unidade unidade;

  @Schema(
    title = "Data de Criação",
    description = "Data de Criação do Formulário",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Observação",
    description = "Observação do Formulário",
    required = true,
    implementation = String.class
  )
  @Column
  private String observacao;

  public Integer getId() {
    return id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public Modelo getModelo() {
    return modelo;
  }

  public Unidade getUnidade() {
    return unidade;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }
  
  public void setDataCriacao(LocalDateTime dataCriacao) {
	  this.dataCriacao = dataCriacao;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public void setModelo(Modelo modelo) {
    this.modelo = modelo;
  }

  public void setUnidade(Unidade unidade) {
    this.unidade = unidade;
  }
}
