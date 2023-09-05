package fiscalizacao.dsbrs.agems.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fiscalizacao.dsbrs.agems.apis.requests.UsuarioEditRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.InfoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuário", description = "APIs de Gerenciamento de Usuário")
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

  @Autowired
  private final UsuarioService SERVICO_USUARIO;

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Retorna um json com informações do usuário",
        content = @Content(
          schema = @Schema(implementation = InfoResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não está autorizado",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Não tem permissão para visualizar informações de outros usuários.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Não existe usuário com essa identificação.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Retorna informações de um usuário")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/", produces = "application/json")
  public ResponseEntity<Response> findUsuario(HttpServletRequest request) {
    Response infoResponse = SERVICO_USUARIO.getInfUserToken(request);
    if (infoResponse instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) infoResponse).getStatus())
        .body(infoResponse);
    } else {
      return ResponseEntity.ok().body(infoResponse);
    }
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Retorna um json com informações do usuário com o id ou nome dado",
        content = @Content(
          schema = @Schema(implementation = InfoResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não está autorizado",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Não tem permissão para visualizar informações de outros usuários.",
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
      @ApiResponse(
        responseCode = "404",
        description = "Não existe usuário com essa identificação.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Edita nome ou senha do usuário")
  @SecurityRequirement(name = "BEARER")
  @PutMapping(
    path = "/",
    consumes = "application/json",
    produces = "application/json"
  )
  public ResponseEntity<Response> atualizarSenha(
    HttpServletRequest request,
    @RequestBody UsuarioEditRequest editRequest
  ) {
    Response resposta = SERVICO_USUARIO.atualizarDados(
      request,
      editRequest.getNomeNovo(),
      editRequest.getSenha(),
      editRequest.getSenhaNova()
    );
    if (resposta instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) resposta).getStatus())
        .body(resposta);
    }
    return ResponseEntity.ok().body(resposta);
  }
}
