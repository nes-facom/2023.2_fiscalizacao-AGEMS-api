package fiscalizacao.dsbrs.agems.apis.controller;

import fiscalizacao.dsbrs.agems.apis.requests.FormularioRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResumoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.FormularioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Formulário", description = "APIs de Gerenciamento dos Formulários")
@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormularioController {

  private final FormularioService SERVICO_FORMULARIO;

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Operação bem sucedida.",
        content = @Content(
          schema = @Schema(implementation = FormularioResponse.class)
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
        responseCode = "403",
        description = "Não pode acessar essas informações",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
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
  @Operation(summary = "Adicionar um novo formulario")
  @SecurityRequirement(name = "BEARER")
  @PostMapping(
    path = "/add",
    produces = "application/json",
    consumes = "application/json"
  )
  public ResponseEntity<Response> adicionaFormulario(
    HttpServletRequest request,
    @RequestBody FormularioRegisterRequest novoFormulario
  ) {
    try {
      Response formularioResponse = SERVICO_FORMULARIO.cadastraFormulario(
        request,
        novoFormulario
      );
      if (formularioResponse instanceof ErroResponse) return ResponseEntity
        .status(((ErroResponse) formularioResponse).getStatus())
        .body(formularioResponse);
      return ResponseEntity.status(201).body(formularioResponse);
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(500)
        .body(
          ErroResponse
            .builder()
            .status(500)
            .erro("Internal Server Error:" + e.getMessage())
            .build()
        );
    }
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida. Retorna um conjunto de Formulários",
        content = @Content(
          schema = @Schema(implementation = FormularioResumoResponse.class)
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
        responseCode = "403",
        description = "Não pode acessar essas informações",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "500",
        description = "Erro de Servidor",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Não há formulários cadastrados",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Listar todos os formulários")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/todos/", produces = "application/json")
  public ResponseEntity<?> listaFormularios(HttpServletRequest request) {
	  
    try {
      List<FormularioResumoResponse> formularioResponse = SERVICO_FORMULARIO.listaTodosFormularios(
        request
      );
      if (formularioResponse.size() == 0) {
        return ResponseEntity
          .status(404)
          .body(
            ErroResponse
              .builder()
              .status(404)
              .erro("Não há formulários cadastrados")
              .build()
          );
      }
      return ResponseEntity.status(200).body(formularioResponse);
    } catch (
      DataIntegrityViolationException
      | IllegalArgumentException
      | NullPointerException
      | HttpMessageNotReadableException e
    ) {
      return ResponseEntity
        .badRequest()
        .body(
          ErroResponse
            .builder()
            .status(400)
            .erro("Bad Request:" + e.getMessage())
            .build()
        );
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(500)
        .body(
          ErroResponse
            .builder()
            .status(500)
            .erro("Internal Server Error:" + e.getMessage())
            .build()
        );
    }
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida. Deleta o formulário",
        content = @Content(
          schema = @Schema(implementation = FormularioAcaoResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Not Found",
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
        responseCode = "403",
        description = "Não pode acessar essas informações",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
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
  @Operation(summary = "Deletar um formulário")
  @SecurityRequirement(name = "BEARER")
  @DeleteMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<Response> deletaFormulario(
    HttpServletRequest request,
    @PathVariable(value = "id") int formulario
  ) {
    try {
      Response formularioResponse = SERVICO_FORMULARIO.deletaFormulario(
        request,
        formulario
      );
      if (formularioResponse instanceof ErroResponse) return ResponseEntity
        .status(((ErroResponse) formularioResponse).getStatus())
        .body(formularioResponse);
      return ResponseEntity.status(200).body(formularioResponse);
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(500)
        .body(
          ErroResponse
            .builder()
            .status(500)
            .erro("Internal Server Error:" + e.getMessage())
            .build()
        );
    }
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida. Edita o formulário",
        content = @Content(
          schema = @Schema(implementation = FormularioAcaoResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Not Found",
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
        responseCode = "403",
        description = "Não pode acessar essas informações",
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
        responseCode = "500",
        description = "Erro de Servidor",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Editar um formulário")
  @SecurityRequirement(name = "BEARER")
  @PutMapping(
    path = "/{id}",
    produces = "application/json",
    consumes = "application/json"
  )
  public ResponseEntity<Response> editaFormulario(
    HttpServletRequest request,
    @PathVariable(name = "id") int id,
    @RequestBody FormularioRequest formulario
  ) {
    try {
      Response formularioResponse = SERVICO_FORMULARIO.editaFormulario(
        request,
        id,
        formulario
      );
      if (formularioResponse instanceof ErroResponse) {
        return ResponseEntity
          .status(((ErroResponse) formularioResponse).getStatus())
          .body(formularioResponse);
      }
      return ResponseEntity.status(200).body(formularioResponse);
    } catch (
      DataIntegrityViolationException
      | IllegalArgumentException
      | NullPointerException
      | HttpMessageNotReadableException e
    ) {
      return ResponseEntity
        .badRequest()
        .body(
          ErroResponse
            .builder()
            .status(400)
            .erro("Bad Request:" + e.getMessage())
            .build()
        );
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(500)
        .body(
          ErroResponse
            .builder()
            .status(500)
            .erro("Internal Server Error:" + e.getMessage())
            .build()
        );
    }
  }

  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação bem sucedida. Vê o formulário",
        content = @Content(
          schema = @Schema(implementation = FormularioResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Not Found",
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
        responseCode = "403",
        description = "Não pode acessar essas informações",
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
        responseCode = "500",
        description = "Erro de Servidor",
        content = @Content(
          schema = @Schema(implementation = ErroResponse.class)
        )
      ),
    }
  )
  @Operation(summary = "Ver um formulário")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<Response> verFormulario(
    HttpServletRequest request,
    @PathVariable(name = "id") int id
  ) {
    try {
      Response formularioResponse = SERVICO_FORMULARIO.verFormulario(
        request,
        id
      );
      if (formularioResponse instanceof ErroResponse) {
        return ResponseEntity
          .status(((ErroResponse) formularioResponse).getStatus())
          .body(formularioResponse);
      }
      return ResponseEntity.status(200).body(formularioResponse);
    } catch (
      DataIntegrityViolationException
      | IllegalArgumentException
      | NullPointerException
      | HttpMessageNotReadableException e
    ) {
      return ResponseEntity
        .badRequest()
        .body(
          ErroResponse
            .builder()
            .status(400)
            .erro("Bad Request:" + e.getMessage())
            .build()
        );
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(500)
        .body(
          ErroResponse
            .builder()
            .status(500)
            .erro("Internal Server Error:" + e.getMessage())
            .build()
        );
    }
  }
}
