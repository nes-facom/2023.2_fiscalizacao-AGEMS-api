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
import lombok.Data;

@Entity
@Data
@Table(name = "questao")
public class Questao {

  @Schema(
    title = "Id",
    description = "Id de Questão, autogerável no banco",
    required = true,
    format = "string",
    type = "UUID",
    example = "1"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Schema(
    title = "Id do usuário de criação",
    description = "Id de usuário reponsável pela inserção da questão",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioCriacao")
  private Usuario usuarioCriacao;

  @Schema(
    title = "Id do usuário de alteração",
    description = "Id de usuário reponsável pela última alteração da questão",
    required = true,	
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioAlteracao")
  private Usuario usuarioAlteracao;

  @Schema(
    title = "Pergunta",
    description = "Texto da Pergunta",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false, name="idPergunta")
  private String pergunta;

  @Schema(
    title = "Objetiva",
    description = "Campo de validação se é objetiva ou não",
    required = true,
    type = "boolean"
  )
  @Column(nullable = false, name="objetiva")
  private boolean objetiva;
  
  @Schema(
    title = "Lista de modelos",
    description = "Modelos associados a uma questão",
    required = true,
    type = "array"
  )
  @OneToMany(
    mappedBy = "questao",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<QuestaoModelo> modelos;
  
  @Schema(
    title = "Lista de formulários",
    description = "Formulários associados a uma questão",
    required = true,
    type = "array"
  )
  @OneToMany(
    mappedBy = "questao",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<QuestaoFormulario> formularios;

  @Schema(
    title = "Alternativas da questão",
    description = "Alternativas associadas a uma questão",
    required = true,	
    type = "array"
  )
  @OneToMany(
	fetch = FetchType.LAZY,
	cascade = CascadeType.ALL,
  	mappedBy = "questao"
  )
  private List<AlternativaResposta> alternativasResposta;
  
  @Schema(
    title = "Respostas da questão",
    description = "Respostas associadas a uma questão",
    type = "array"
  )
  @OneToMany(
	mappedBy = "resposta",
	fetch = FetchType.LAZY ) 
  private List<Resposta> respostas;
  
  @Schema(
    title = "Portaria",
    description = "Descrição da Portaria",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column
  private String portaria;

  @Schema(
    title = "Data de criação",
    description = "Data de criação da questão",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataCriacao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Data de alteração",
    description = "Data de alteração da questão",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataAlteracao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataAlteracao;
}
