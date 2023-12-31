package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fiscalizacao.dsbrs.agems.apis.controller.FormularioController;
import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioBuscaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResumoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.FormularioService;
import jakarta.servlet.http.HttpServletRequest;

public class FormularioControllerTest {

  private FormularioService formularioService;
  private FormularioController formularioController;
  private HttpServletRequest request;

  @BeforeEach
  public void setUp() {
    formularioService = mock(FormularioService.class);
    formularioController = new FormularioController(formularioService);
    request = mock(HttpServletRequest.class);
  }

  @Test
  public void testAdicionaFormulario() {
    FormularioRegisterRequest novoFormulario = new FormularioRegisterRequest();

    Response expectedResponse = new FormularioResponse();

    when(formularioService.cadastraFormulario(request, novoFormulario))
      .thenReturn(expectedResponse);

    ResponseEntity<Response> responseEntity = formularioController.adicionaFormulario(
      request,
      novoFormulario
    );

    verify(formularioService).cadastraFormulario(request, novoFormulario);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(expectedResponse, responseEntity.getBody());
  }

  @Test
  public void testListaFormulariosResumidosOK() {
    FormularioBuscaResponse formularioBuscaResponse = new FormularioBuscaResponse();
    List<FormularioResumoResponse> formularioResponseList = new ArrayList<>();
    FormularioResumoResponse formularioResponse = new FormularioResumoResponse();
    FormularioResumoResponse formularioResponse2 = new FormularioResumoResponse();
    FormularioResumoResponse formularioResponse3 = new FormularioResumoResponse();
    formularioResponseList.add(formularioResponse);
    formularioResponseList.add(formularioResponse2);
    formularioResponseList.add(formularioResponse3);
    formularioBuscaResponse.setFormularios(formularioResponseList);
    formularioBuscaResponse.setPagina(1);
    formularioBuscaResponse.setPaginaMax(1);
    when(formularioService.listaTodosFormulariosResumidos(request, 1, 15))
      .thenReturn(formularioBuscaResponse);

    ResponseEntity<?> responseEntity = formularioController.listaFormulariosResumidos(
      request,
      1,
      15
    );

    verify(formularioService).listaTodosFormulariosResumidos(request, 1, 15);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(formularioBuscaResponse, responseEntity.getBody());
  }
}
