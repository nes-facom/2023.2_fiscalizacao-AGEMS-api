package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "Token", description = "Objeto de Token")
@SecurityScheme(
  name = "BEARER",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "BEARER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

  @Schema(
    title = "Id",
    description = "Id de Token, autogerável no Banco",
    required = true,
    format = "number",
    type = "integer",
    implementation = Integer.class
  )
  @Id
  @GeneratedValue
  private Integer id;

  @Schema(
    title = "Token",
    description = "String de Token",
    required = true,
    type = "string",
    implementation = String.class
  )
  @Column(unique = true)
  private String token;

  @Schema(
    title = "Tipo de Token",
    description = "Tipo de Token que a aplicação possui",
    required = true,
    pattern = "BEARER",
    type = "string"
  )
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private TokenType tokenType = TokenType.BEARER;

  @Schema(
    title = "Revocado",
    description = "Propriedade que indica se o token já foi revocado ou não",
    required = true,
    type = "boolean"
  )
  private boolean revoked;

  @Schema(
    title = "Expirado",
    description = "Propriedade que indica se o token já foi expirado ou não",
    required = true,
    type = "boolean"
  )
  private boolean expired;

  @Schema(
    title = "Usuário",
    description = "Usuário a qual o Token pertence",
    required = true,
    implementation = Usuario.class
  )
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_Usuario")
  private Usuario usuario;

  public boolean isExpired() {
    return expired;
  }

  public boolean isRevoked() {
    return revoked;
  }
}
