package fiscalizacao.dsbrs.agems.apis.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(title = "Unidade", description = "Objeto de Unidade que é fiscalizada.")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unidade")
public class Unidade {

  @Schema(
    title = "Id",
    description = "Id de Unidade, autogerável no banco",
    required = true,
    format = "string",
    type = "UUID"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(
    title = "Id do usuário de criação",
    description = "Id de usuário reponsável pela inserção da unidade",
    required = true,
    format = "string",
    type = "UUID"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioCriacao")
  private Usuario usuarioCriacao;

  @Schema(
    title = "Id do usuário de alteração",
    description = "Id de usuário reponsável pela última alteração da unidade",
    required = true,
    format = "string",
    type = "UUID"
  )
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
  @JoinColumn(name = "idUsuarioAlteracao")
  private Usuario usuarioAlteracao;

  @Schema(
    title = "Nome da unidade",
    description = "Nome da unidade",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false, unique = true)
  private String nome;

  @Schema(
    title = "Endereço da unidade",
    description = "Endereço da unidade",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String endereco;

  @Schema(
    title = "Tipo da unidade",
    description = "Tipo da unidade",
    type = "string",
    implementation = String.class
  )
  private String tipo;

  @Schema(
    title = "Data de criação",
    description = "Data de criação da unidade",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataCriacao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Data de alteração",
    description = "Data de alteração da unidade",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataAlteracao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataAlteracao;
  
  @Schema(
    title = "Formulários da unidade",
    description = "Formulários associados a uma unidade"
  )
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY ) 
  private List<Formulario> formularios;
}
