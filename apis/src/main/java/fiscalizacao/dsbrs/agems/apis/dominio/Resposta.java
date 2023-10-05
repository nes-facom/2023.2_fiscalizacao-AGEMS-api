package fiscalizacao.dsbrs.agems.apis.dominio;

import java.time.LocalDateTime;
import fiscalizacao.dsbrs.agems.apis.dominio.chaves.RespostaKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Resposta", description = "Objeto de Resposta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(RespostaKey.class)
@Table(name = "resposta")
public class Resposta {

  @Schema(
    title = "Id",
    description = "Id de resposta, autogerável no banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Questão",
    description = "Questão a qual a resposta responde",
    required = true,
    implementation = Questao.class
  )
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idQuestao", nullable = false)
  private Questao questao;

  @Schema(
    title = "Id",
    description = "Formulário ao qual a resposta foi registrada",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idFormulario", nullable = false)
  private Formulario formulario;

  @Schema(
    title = "Id do usuário de criação",
    description = "Id de usuário reponsável pela inserção da resposta",
    required = true,
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuariCriacao")
  private Usuario usuarioCriacao;

  @Schema(
    title = "Id do usuário de alteração",
    description = "Id de usuário reponsável pela última alteração da resposta",
    required = true,	
    format = "number",
    type = "integer"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioAlteracao")
  private Usuario usuarioAlteracao;

  @Schema(
    title = "Resposta",
    description = "Texto da resposta",
    required = true,
    type = "string",
    implementation = String.class
  )
  private String resposta;

  @Schema(
    title = "Observação",
    description = "Texto de observação da resposta",
    required = true,
    type = "string",
    implementation = String.class
  )
  private String observacao;

  @Schema(
    title = "Data de criação",
    description = "Data de criação da resposta",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataCriacao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Data de alteração",
    description = "Data de alteração da resposta",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataAlteracao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataAlteracao;
}
