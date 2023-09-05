package fiscalizacao.dsbrs.agems.apis.controller;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Controller para cadastro e autenticação do usuário")
@Tag(name = "Usuário", description = "APIs de Gerenciamento de Usuário")
@RequestMapping(path = "/usuarios", value = "/usuarios")
@RequiredArgsConstructor
public class AuthenticationController {

  @Autowired
  private final JwtService SERVICO_AUTH;

  @Operation(
    summary = "Cadastra um usuário novo",
    description = "Recebe uma requisição com dados de um usuário novo e realiza seu cadastro",
    operationId = "cadastrar"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Operação bem sucedida. Usuário criado e armazenado no Banco.",
        content = @Content(
          schema = @Schema(implementation = AuthenticationResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "409",
        description = "Conflito",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        ),
        extensions = {
          @Extension(
            name = "Detalhes do erro",
            properties = {
              @ExtensionProperty(name = "status", value = "409"),
              @ExtensionProperty(
                name = "erro",
                value = "Usuário já cadastrado!"
              ),
            }
          ),
        }
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Bad Request.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        ),
        extensions = {
          @Extension(
            name = "Detalhes do erro",
            properties = {
              @ExtensionProperty(name = "code", value = "400"),
              @ExtensionProperty(
                name = "message",
                value = "Requisição mal formada"
              ),
            }
          ),
        }
      ),
      @ApiResponse(
        responseCode = "500",
        description = "Erro de Servidor",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @PostMapping(
    name = "Cadastra um usu\u00E1rio novo",
    value = "/cadastro",
    path = "/cadastro",
    consumes = "application/json",
    produces = "application/json"
  )
  @SecurityRequirements
  public ResponseEntity<Response> cadastrar(@RequestBody RegisterRequest request)
  {
    Response response = SERVICO_AUTH.cadastrar(request);
    if (response instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) response).getStatus())
        .body(response);
    }
    return ResponseEntity.status(201).body(response);
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida.Retorna json com access_token e refresh_token",
        content = @Content(
          schema = @Schema(implementation = AuthenticationResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Requisição mal formada.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(
    summary = "Autentica um usuário.",
    description = "Recebe o e-mail e senha do usuário para realizar sua autenticação"
  )
  @PostMapping(
    path = "/autenticar",
    consumes = "application/json",
    produces = "application/json"
  )
  @SecurityRequirements
  public ResponseEntity<?> autenticar(
    @RequestBody AuthenticationRequest request
  ) {
    try {
      return ResponseEntity.ok(SERVICO_AUTH.autenticar(request));
    } catch (BadCredentialsException e) {
      return ResponseEntity
        .status(403)
        .body(
          ErroResponse
            .builder()
            .status(403)
            .erro("Bad Credentials:" + e.getMessage())
            .build()
        );
    }
  }






  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida. Retorna json com access_token e refresh_token ",
        content = @Content(
          schema = @Schema(implementation = AuthenticationResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado - Token Expirado ",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "E-mail não encontrado",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Renova o token do usuário.")
  @PostMapping(path = "/renovar-token", produces = "application/json")
  public ResponseEntity<Response> renovarToken(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws IOException, StreamWriteException, DatabindException, java.io.IOException {
    Response renovaResponse = SERVICO_AUTH.renovarToken(request, response);
    if (renovaResponse instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) renovaResponse).getStatus())
        .body(renovaResponse);
    }
    return ResponseEntity.ok().body(renovaResponse);
  }
}
