package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

  @Schema(
    title = "Id",
    description = "Id de Usuário, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer"
  )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(
    title = "Nome do Usuário",
    description = "Nome do Usu\u00E1rio",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String nome;

  @Schema(
    title = "Email do Usuário",
    description = "Email do Usu\u00E1rio",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(unique = true, nullable = false)
  private String email;

  @Schema(
    title = "Senha do Usuário",
    description = "Senha do Usu\u00E1rio",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(nullable = false)
  private String senha;

  @Schema(
    title = "Data de Criação",
    description = "Data de cadastro do Usuário no sistema",
    required = true,
    type = "string",
    implementation = LocalDate.class
  )
  @Column(nullable = false)
  private LocalDate dataCriacao;

  @Schema(
    title = "Cargo do Usuário",
    description = "Cargo do Usu\u00E1rio",
    required = true,
    type = "string",
    pattern = "Coordenador|Analista de Regulação|Assessor Técnico|Assessor Jurídico",
    implementation = String.class
  )
  @Column(nullable = false)
  private String cargo;

  @Schema(
    title = "Papel do Usuário no sistema",
    description = "Papel do Usu\u00E1rio no sistema",
    required = true,
    pattern = "USER|ADMIN",
    type = "string"
  )
  @Enumerated(EnumType.STRING)
  private Papel funcao;

  @Schema(
    title = "Tokens do Usuário ",
    description = "Lista de Tokens do Usu\u00E1rio no sistema"
  )
  @OneToMany(mappedBy = "usuario")
  private List<Token> tokens;

  public String getCargo() {
    return this.cargo;
  }

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (!email.contains("@")) {
      this.email = null;
    } else {
      this.email = email;
    }
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDate() {
    this.dataCriacao = LocalDate.now();
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
