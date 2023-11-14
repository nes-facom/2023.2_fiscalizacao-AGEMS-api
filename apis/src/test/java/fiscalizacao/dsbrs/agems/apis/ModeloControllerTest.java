package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fiscalizacao.dsbrs.agems.apis.controller.ModeloController;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloBuscaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResumidoResponse;
import fiscalizacao.dsbrs.agems.apis.service.ModeloService;

class ModeloControllerTest {

  @Mock
  private ModeloRepositorio modeloRepositorio;

  @Mock
  private ModeloService modeloService;

  @InjectMocks
  private ModeloController modeloController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAdicionaModeloRetornaSucesso() {

    ModeloRegisterRequest novoModelo = new ModeloRegisterRequest();
    novoModelo.setNome("Modelo 01");
    ResponseEntity<?> response = modeloController.adicionaModelo(novoModelo);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    verify(modeloService, times(1)).cadastraModelo(novoModelo);

  }

  @Test
  public void testAdicionaModeloRetornaErroNomeNull() {

    ModeloRegisterRequest novoModelo = new ModeloRegisterRequest();
    ResponseEntity<?> response = modeloController.adicionaModelo(novoModelo);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void testDeletaModeloRetornaNotFound() {

    UUID modeloId = UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058");
    ResponseEntity<?> response = modeloController.deletaModelo(modeloId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(modeloService, times(1)).deletaModelo(modeloId);
  }

  @Test
  public void testVerModeloReturnModelo() {

    UUID modelId = UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058");
    ModeloService mockService = mock(ModeloService.class);
    ModeloResponse expectedResponse = new ModeloResponse();
    when(mockService.verModelo(modelId)).thenReturn(expectedResponse);
    ModeloController controller = new ModeloController(mockService);

    ResponseEntity<?> response = controller.verModelo(modelId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  public void testVerModeloNotFound() {
    UUID modelId = UUID.fromString("7dde8f7f-e50b-4ccd-8682-b34feee52206");
    ModeloService mockService = mock(ModeloService.class);
    when(mockService.verModelo(modelId)).thenReturn(null);
    ModeloController controller = new ModeloController(mockService);

    ResponseEntity<?> response = controller.verModelo(modelId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Modelo Não encontrado", ((ErroResponse) response.getBody()).getErro());
  }

  @Test
  public void testVerModeloIdInvalido() {
    UUID modelId = null;
    ModeloController controller = new ModeloController(mock(ModeloService.class));

    ResponseEntity<?> response = controller.verModelo(modelId);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Id precisa ser um numero inteiro positivo", ((ErroResponse) response.getBody()).getErro());
  }

  @Test
  public void testEditaModelo() {
    ModeloEditRequest modelo = new ModeloEditRequest();
    ResponseEntity<?> response = modeloController.editaModelo(modelo);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Nome do modelo n\u00E3o pode ser nulo", ((ErroResponse) response.getBody()).getErro());

  }

  @Test
  public void testListaModelosValido() {
    ModeloListResponse expectedModeloListResponse = ModeloListResponse.builder()
        .data(Collections.singletonList(new ModeloResponse())).build();

    when(modeloService.listaTodosModelos()).thenReturn(expectedModeloListResponse);
    ResponseEntity<?> response = modeloController.listaModelos();

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void testListaModelosInvalido() {
    ModeloListResponse expectedModeloListResponse = ModeloListResponse.builder().data(Collections.emptyList()).build();

    when(modeloService.listaTodosModelos()).thenReturn(expectedModeloListResponse);
    ResponseEntity<?> response = modeloController.listaModelos();

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

  }

  @Test
  public void testListaModelosResumidosValido() {
    List<ModeloResumidoResponse> expectedModeloListResponse = Collections.singletonList(new ModeloResumidoResponse());
    ModeloBuscaResponse expectedModeloBuscaResponse = new ModeloBuscaResponse(1, 1, expectedModeloListResponse);

    when(modeloService.listaTodosModelosResumido(anyInt(), anyInt())).thenReturn(expectedModeloBuscaResponse);
    ResponseEntity<?> response = modeloController.listaModelosResumido(1, 15);

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void testListaModelosResumidosInvalido() {
    List<ModeloResumidoResponse> expectedModeloListResponse = Collections.emptyList();
    ModeloBuscaResponse expectedModeloBuscaResponse = new ModeloBuscaResponse(1, 0, expectedModeloListResponse);

    when(modeloService.listaTodosModelosResumido(anyInt(), anyInt())).thenReturn(expectedModeloBuscaResponse);
    ResponseEntity<?> response = modeloController.listaModelosResumido(1, 15);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

  }

}
