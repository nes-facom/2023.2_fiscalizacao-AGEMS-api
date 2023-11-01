package fiscalizacao.dsbrs.agems.apis.controller;

import fiscalizacao.dsbrs.agems.apis.requests.UnidadeRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Unidade", description = "APIs de Gerenciamento dos Unidades")
@RestController
@RequestMapping("/unidade")
@RequiredArgsConstructor
public class UnidadeController {

  @Autowired
  private final UnidadeService SERVICO_UNIDADE;

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Operação bem sucedida.",
        content = @Content(
          schema = @Schema(implementation = UnidadeResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Bad Request.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "409",
        description = "Unidade já existe.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),@ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Adicionar uma nova unidade")
  @SecurityRequirement(name = "BEARER")
  @PostMapping(
    path = "/add",
    produces = "application/json",
    consumes = "application/json"
  )
  public ResponseEntity<Response> adicionaUnidade(
    @RequestBody UnidadeRequest unidadeRegisterRequest
  ) {
    Response unidadeResponse = SERVICO_UNIDADE.cadastraUnidade(
      unidadeRegisterRequest
    );
    if (unidadeResponse instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) unidadeResponse).getStatus())
        .body((ErroResponse) unidadeResponse);
    } else {
      return ResponseEntity.status(201).body(unidadeResponse);
    }
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida.",
        content = @Content(
          schema = @Schema(implementation = UnidadeResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Não há nenhuma unidade cadastrada.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),@ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Listar todas as unidades cadastradas.")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/todas", produces = "application/json")
  public ResponseEntity<?> listaUnidades() {
    List<UnidadeResponse> unidadeResponses = SERVICO_UNIDADE.listarUnidades();
    if (unidadeResponses.size() != 0) {
      return ResponseEntity.ok().body(unidadeResponses);
    }
    return ResponseEntity
      .status(404)
      .body(
        ErroResponse
          .builder()
          .status(404)
          .erro("Não há nenhuma unidade cadastrada.")
          .build()
      );
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida.",
        content = @Content(
          schema = @Schema(implementation = UnidadeResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Não há nenhuma unidade cadastrada.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),@ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Ver uma unidadee específica.")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<Response> verUnidade(
    @PathVariable(value = "id") String idUnidade
  ) {
    Response response = SERVICO_UNIDADE.verUnidade(idUnidade);
    if (response instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) response).getStatus())
        .body(response);
    }
    return ResponseEntity.ok().body(response);
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida.",
        content = @Content(
          schema = @Schema(implementation = UnidadeResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Não há nenhuma unidade cadastrada.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),@ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Deletar uma unidade específica.")
  @SecurityRequirement(name = "BEARER")
  @DeleteMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<?> deletarunidade(
    @PathVariable(value = "id") String id
  ) {
    Response response = SERVICO_UNIDADE.deletarUnidade(id);
    if (response instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) response).getStatus())
        .body(response);
    }
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("ação", "Unidade deletada com sucesso");
    responseBody.put("id", ((UnidadeResponse) response).getId());
    responseBody.put("idUnidade", ((UnidadeResponse) response).getId());
    responseBody.put("endereço", ((UnidadeResponse) response).getEndereco());
    return ResponseEntity.ok().body(responseBody);
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida.",
        content = @Content(
          schema = @Schema(implementation = UnidadeResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Não há nenhuma unidade cadastrada.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado.",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),@ApiResponse(
        responseCode = "403",
        description = "Acesso Negado!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Erro na requisição!",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      )
    }
  )
  @Operation(summary = "Editar uma unidade específica.")
  @SecurityRequirement(name = "BEARER")
  @PutMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<Response> editarUnidade(
    @PathVariable(value = "id") String idUnidade,
    @RequestBody UnidadeRequest unidadeRequest
  ) {
    Response response = SERVICO_UNIDADE.editarUnidade(
      idUnidade,
      unidadeRequest
    );
    if (response instanceof ErroResponse) {
      return ResponseEntity
        .status(((ErroResponse) response).getStatus())
        .body(response);
    }
    return ResponseEntity.ok().body(response);
  }
}
