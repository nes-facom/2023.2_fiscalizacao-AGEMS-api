package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Schema(title = "Usuário", description = "Objeto de Usuário do sistema")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

  private static final long serialVersionUID = -5851758882055753022L;

  @Schema(
    title = "Id",
    description = "Id de Usuário, autogerável no banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Nome do usuário",
    description = "Nome do usuário",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String nome;

  @Schema(
    title = "Email do usuário",
    description = "Email do usuário",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(unique = true, nullable = false)
  private String email;

  @Schema(
    title = "Senha do usuário",
    description = "Senha do usuário",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String senha;

  @Schema(
    title = "Cargo do usuário",
    description = "Cargo do usuário",
    required = true,
    type = "string",
    pattern = "Coordenador|Analista de Regulação|Assessor Técnico|Assessor Jurídico",
    implementation = String.class
  )
  @Column(nullable = false)
  private String cargo;

  @Schema(
    title = "Papel do usuário no sistema",
    description = "Papel do usuário no sistema",
    required = true,
    pattern = "USER|ADMIN",
    type = "string"
  )
  @Enumerated(EnumType.STRING)
  private Papel funcao;

  @Schema(
    title = "Data de criação",
    description = "Data de criação do usuário",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataCriacao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataCriacao;

  @Schema(
    title = "Data de alteração",
    description = "Data de alteração do usuário",
    required = true,
    implementation = LocalDateTime.class
  )
  @Column(name = "dataAlteracao")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime dataAlteracao;

  @Schema(
    title = "Questões criadas pelo usuário",
    description = "Lista de questões criadas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY) 
  private List<Questao> questoesCriadas;

  @Schema(
    title = "Questões alteradas pelo usuário",
    description = "Lista de questões alteradas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioAlteracao", fetch = FetchType.LAZY) 
  private List<Questao> questoesAlteradas;

  @Schema(
    title = "Modelos criados pelo usuário",
    description = "Lista de modelos criados pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY) 
  private List<Modelo> modelosCriados;

  @Schema(
    title = "Modelos alterados pelo usuário",
    description = "Lista de modelos alterados pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioAlteracao", fetch = FetchType.LAZY) 
  private List<Modelo> modelosAlterados;

  @Schema(
    title = "Formulários criados pelo usuário",
    description = "Lista de formulários criados pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY) 
  private List<Formulario> formulariosCriados;

  @Schema(
    title = "Formulários alterados pelo usuário",
    description = "Lista de formulários alterados pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioAlteracao", fetch = FetchType.LAZY) 
  private List<Formulario> formulariosAlterados;

  @Schema(
    title = "Respostas criadas pelo usuário",
    description = "Lista de respostas criadas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY) 
  private List<Resposta> respostasCriadas;

  @Schema(
    title = "Respostas alteradas pelo usuário",
    description = "Lista de respostas alteradas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioAlteracao", fetch = FetchType.LAZY) 
  private List<Resposta> respostasAlteradas;

  @Schema(
    title = "Imagens criadas pelo usuário",
    description = "Lista de imagens criadas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY) 
  private List<Imagem> imagensCriadas;

  @Schema(
    title = "Imagens alteradas pelo usuário",
    description = "Lista de imagens alteradas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioAlteracao", fetch = FetchType.LAZY) 
  private List<Imagem> imagensAlteradas;

  @Schema(
    title = "Unidades criadas pelo usuário",
    description = "Lista de unidades criadas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioCriacao", fetch = FetchType.LAZY) 
  private List<Unidade> unidadesCriadas;

  @Schema(
    title = "Unidades alteradas pelo usuário",
    description = "Lista de unidades alteradas pelo usuário"
  )
  @OneToMany(mappedBy = "usuarioAlteracao", fetch = FetchType.LAZY) 
  private List<Unidade> unidadesAlteradas;
  
  @Schema(
    title = "Tokens do usuário ",
    description = "Lista de tokens do usuário no sistema"
  )
  @OneToMany(mappedBy = "usuario")
  private List<Token> tokens;

  public void setCargo(String cargo) {
    final String CARGOS[] = {
      "Coordenador",
      "Analista de Regulação",
      "Assessor Técnico",
      "Assessor Jurídico",
    };
    for (int i = 0; i < CARGOS.length; i++) {
      if (CARGOS[i].equalsIgnoreCase(cargo)) {
        this.cargo = CARGOS[i];
        return;
      }
    }
    this.cargo = null;
  }

  public void setEmail(String email) {
    if (!email.contains("@")) {
      this.email = null;
    } else {
      this.email = email;
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(funcao.name()));
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
