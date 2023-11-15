package fiscalizacao.dsbrs.agems.apis.controller;

import java.util.List;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloBuscaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResumidoResponse;
import fiscalizacao.dsbrs.agems.apis.service.ModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Modelo", description = "APIs de Gerenciamento dos Modelos")
@RestController
@RequestMapping("/modelo")
@RequiredArgsConstructor
public class ModeloController {

  private final ModeloService SERVICO_MODELO;

  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Operação bem sucedida.", content = @Content(schema = @Schema(implementation = ModeloResponse.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso Negado!", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "401", description = "Não autorizado - Token Expirado ", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro de Servidor", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
  })
  @Operation(summary = "Adicionar um novo modelo")
  @SecurityRequirement(name = "BEARER")
  @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> adicionaModelo(
      @RequestBody ModeloRegisterRequest novoModelo) {
    try {
      if (novoModelo.getNome().length() == 0) {
        return ResponseEntity
            .badRequest()
            .body(
                ErroResponse
                    .builder()
                    .status(400)
                    .erro("Nome do modelo não informado")
                    .build());
      }

      ModeloResponse modeloResponse = SERVICO_MODELO.cadastraModelo(novoModelo);
      return ResponseEntity.status(201).body(modeloResponse);
    } catch (
        DataIntegrityViolationException
        | IllegalArgumentException
        | NullPointerException
        | HttpMessageNotReadableException e) {
      return ResponseEntity
          .badRequest()
          .body(
              ErroResponse
                  .builder()
                  .status(400)
                  .erro("Bad Request:" + e.getMessage())
                  .build());
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(500)
          .body(
              ErroResponse
                  .builder()
                  .status(500)
                  .erro("Internal Server Error:" + e.getMessage())
                  .build());
    }
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operação bem sucedida. Deleta o Modelo", content = @Content(schema = @Schema(implementation = ModeloAcaoResponse.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro de Servidor", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso Negado!", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "401", description = "Não autorizado - Token Expirado ", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
  })
  @Operation(summary = "Deletar um Modelo")
  @SecurityRequirement(name = "BEARER")
  @DeleteMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<?> deletaModelo(
      @PathVariable(value = "id") UUID modelo) {
    try {
      ModeloAcaoResponse modeloResponse = SERVICO_MODELO.deletaModelo(modelo);
      if (modeloResponse == null) {
        return ResponseEntity
            .status(404)
            .body(
                ErroResponse
                    .builder()
                    .status(404)
                    .erro("Modelo Não encontrado")
                    .build());
      } else {
        return ResponseEntity.status(200).body(modeloResponse);
      }
    } catch (
        DataIntegrityViolationException
        | IllegalArgumentException
        | NullPointerException
        | HttpMessageNotReadableException e) {
      return ResponseEntity
          .badRequest()
          .body(
              ErroResponse
                  .builder()
                  .status(404)
                  .erro("Not Found:" + e.getMessage())
                  .build());
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(500)
          .body(
              ErroResponse
                  .builder()
                  .status(500)
                  .erro("Internal Server Error:" + e.getMessage())
                  .build());
    }
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operação bem sucedida. Retorna um conjunto de Modelos", content = @Content(schema = @Schema(implementation = ModeloResponse.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro de Servidor", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "404", description = "Não há modelos cadastrados", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso Negado!", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "401", description = "Não autorizado - Token Expirado ", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
  })
  @Operation(summary = "Listar todos os modelos detalhadamente")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/", produces = "application/json")
  public ResponseEntity<?> listaModelos() {
    try {
      ModeloListResponse modeloListResponse = SERVICO_MODELO.listaTodosModelos();
      if (modeloListResponse.getData().size() == 0) {
        return ResponseEntity
            .status(404)
            .body(
                ErroResponse
                    .builder()
                    .status(404)
                    .erro("Não há modelos cadastrados")
                    .build());
      }
      return ResponseEntity.status(200).body(modeloListResponse);
    } catch (
        DataIntegrityViolationException
        | IllegalArgumentException
        | NullPointerException
        | HttpMessageNotReadableException e) {
      return ResponseEntity
          .badRequest()
          .body(
              ErroResponse
                  .builder()
                  .status(400)
                  .erro("Bad Request:" + e.getMessage())
                  .build());
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(500)
          .body(
              ErroResponse
                  .builder()
                  .status(500)
                  .erro("Internal Server Error:" + e.getMessage())
                  .build());
    }
  }
  
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operação bem sucedida. Retorna um conjunto de Modelos", content = @Content(schema = @Schema(implementation = ModeloResumidoResponse.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro de Servidor", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "404", description = "N\u00E3o h\u00E1 modelos cadastrados", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso Negado!", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "401", description = "Não autorizado - Token Expirado ", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
  })
  @Operation(summary = "Listar todos os modelos resumidamente")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/todos", produces = "application/json")
  public ResponseEntity<?> listaModelosResumido(
      @RequestParam(required = false, defaultValue = "0") int pagina,
      @RequestParam(required = false, defaultValue = "15") int quantidade
  ) {
    try {
      ModeloBuscaResponse modeloListResponse = SERVICO_MODELO.listaTodosModelosResumido(pagina, quantidade);
      if (modeloListResponse.getData().size() == 0) {
        return ResponseEntity
            .status(404)
            .body(
                ErroResponse
                    .builder()
                    .status(404)
                    .erro("Não há modelos cadastrados")
                    .build());
      }
      return ResponseEntity.status(200).body(modeloListResponse);
    } catch (
        DataIntegrityViolationException
        | IllegalArgumentException
        | NullPointerException
        | HttpMessageNotReadableException e) {
      return ResponseEntity
          .badRequest()
          .body(
              ErroResponse
                  .builder()
                  .status(400)
                  .erro("Bad Request:" + e.getMessage())
                  .build());
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(500)
          .body(
              ErroResponse
                  .builder()
                  .status(500)
                  .erro("Internal Server Error:" + e.getMessage())
                  .build());
    }
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operação bem sucedida. Modelo encontrado", content = @Content(schema = @Schema(implementation = ModeloResponse.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso Negado!", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "401", description = "Não autorizado - Token Expirado ", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro de Servidor", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
  })
  @Operation(summary = "Listar um modelo")
  @SecurityRequirement(name = "BEARER")
  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<?> verModelo(@PathVariable(name = "id") UUID id) {
    try {
      if (id != null) {
        ModeloResponse modeloResponse = SERVICO_MODELO.verModelo(id);
        if (modeloResponse == null) {
          return ResponseEntity
              .status(404)
              .body(
                  ErroResponse
                      .builder()
                      .status(404)
                      .erro("Modelo Não encontrado")
                      .build());
        } else {
          return ResponseEntity.status(200).body(modeloResponse);
        }
      } else {
        return ResponseEntity
            .status(400)
            .body(
                ErroResponse
                    .builder()
                    .status(400)
                    .erro("Id precisa ser um numero inteiro positivo")
                    .build());

      }
    } catch (
        DataIntegrityViolationException
        | IllegalArgumentException
        | NullPointerException
        | HttpMessageNotReadableException e) {
      return ResponseEntity
          .badRequest()
          .body(
              ErroResponse
                  .builder()
                  .status(404)
                  .erro("Not Found:" + e.getMessage())
                  .build());
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(500)
          .body(
              ErroResponse
                  .builder()
                  .status(500)
                  .erro("Internal Server Error:" + e.getMessage())
                  .build());
    }
  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operação bem sucedida. Edita o Modelo", content = @Content(schema = @Schema(implementation = ModeloAcaoResponse.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "403", description = "Acesso Negado!", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "401", description = "Não autorizado - Token Expirado ", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro de Servidor", content = @Content(schema = @Schema(implementation = ErroResponse.class))),
  })
  @Operation(summary = "Editar um modelo")
  @SecurityRequirement(name = "BEARER")
  @PutMapping(path = "/edit", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> editaModelo(@RequestBody ModeloEditRequest modelo) {
    try {
      if (modelo.getModeloNome() !=null) {
        if(!modelo.getModeloNome().isEmpty()){
          
          ModeloAcaoResponse modeloResponse = SERVICO_MODELO.editaModelo(modelo);
          return ResponseEntity.status(200).body(modeloResponse);
        }else{
          return ResponseEntity.badRequest()
            .body(ErroResponse.builder().status(400).erro("Nome do modelo não informado").build());

        }
    
      } else {

        return ResponseEntity.badRequest()
            .body(ErroResponse.builder().status(400).erro("Nome do modelo não pode ser nulo").build());

      }

    } catch (
        DataIntegrityViolationException
        | IllegalArgumentException
        | NullPointerException
        | HttpMessageNotReadableException e) {
      return ResponseEntity
          .badRequest()
          .body(
              ErroResponse
                  .builder()
                  .status(404)
                  .erro("Not Found:" + e.getMessage())
                  .build());
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(500)
          .body(
              ErroResponse
                  .builder()
                  .status(500)
                  .erro("Internal Server Error:" + e.getMessage())
                  .build());
    }
  }
}
