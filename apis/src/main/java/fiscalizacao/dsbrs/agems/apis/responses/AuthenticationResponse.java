package fiscalizacao.dsbrs.agems.apis.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "AuthenticationResponse", description = "Objeto de Resposta de Autenticação de Usuário")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends Response {

  @Schema(
    title = "Token de Acesso",
    description = "Token de acesso do Usuário, validade de um dia",
    required = true,
    type = "string",
    example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NDg2Mzk0NCwiZXhwIjoxNjg0OTUwMzQ0fQ.xcErUMgCmV2ou29x0CqsvOYsTdnnQZZRXtPB_oWmssM"
  )
  @JsonProperty("access_token")
  private String accessToken;

  @Schema(
    title = "Token de Renovação",
    description = "Token de renovação do Usuário, validade de sete dias",
    required = true,
    type = "string",
    example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NDg2Mzk0NCwiZXhwIjoxNjg1NDY4NzQ0fQ.xYfWNMAdttQs0_t2NAdw117ABjfyrB59RzD0xq4Mq7w"
  )
  @JsonProperty("refresh_token")
  private String refreshToken;
}
