package fiscalizacao.dsbrs.agems.apis.dominio;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
  title = "Função de Usuário",
  description = "Papel que desemepnha o usuário"
)
public enum Papel {
  USER,
  ADMIN,
}
