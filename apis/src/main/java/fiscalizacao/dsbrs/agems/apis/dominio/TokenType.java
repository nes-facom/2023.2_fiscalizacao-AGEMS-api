package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
  title = "Tipo de Token",
  description = "Tipo de token de autenticação e autorização que aplicação gera e consome."
)
public enum TokenType {
  BEARER,
}
