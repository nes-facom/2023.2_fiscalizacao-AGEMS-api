package fiscalizacao.dsbrs.agems.apis.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    description = "Id de Formulário, autogerável no banco",
    required = true,
    format = "string",
    type = "UUID"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(
    title = "Id do usuário de criação",
    description = "Id de usuário reponsável pela inserção do formulário",
    required = true,
    format = "string",
    type = "UUID"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioCriacao")
  private Usuario usuarioCriacao;

  @Schema(
    title = "Id do usuário de alteração",
    description = "Id de usuário reponsável pela última alteração do formulário",
    required = true,
    format = "string",
    type = "UUID"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioAlteracao")
  private Usuario usuarioAlteracao;

  @Schema(
    title = "Unidade",
    description = "Unidade respectiva ao formulário",
    required = true,
    implementation = Unidade.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idUnidade")
  private Unidade unidade;

  @Schema(
    title = "Observação",
    description = "Observação do formulário",
    required = true,
    implementation = String.class
  )
  @Column
  private String observacao;

  @Schema(
    title = "Respostas do formulário",
    description = "Respostas associadas a um formulário"
  )
  @OneToMany(
    mappedBy = "formulario",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER
  )
  private List<Resposta> respostas;

  @Schema(
    title = "Lista de questões",
    description = "Questões associadas ao formulário",
    required = true,
    type = "array"
  )
  @OneToMany(
    mappedBy = "formulario",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER
  )
  private List<QuestaoFormulario> questoes;
  
  @Schema(
    title = "Imagens do formulário",
    description = "Imagens associadas a um formulário"
  )
  @OneToMany(
    mappedBy = "formulario",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Imagem> imagens;
  
  @Schema(
    title = "Data de criação",
    description = "Data de criação do formulário",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataCriacao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Data de alteração",
    description = "Data de alteração do formulário",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataAlteracao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataAlteracao;
}
