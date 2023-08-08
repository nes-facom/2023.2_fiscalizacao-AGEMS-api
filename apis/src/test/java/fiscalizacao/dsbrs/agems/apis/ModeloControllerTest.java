package fiscalizacao.dsbrs.agems.apis;

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
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.service.ModeloService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
        novoModelo.setModelo("Modelo 01");
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

        int modeloId = 1;
        ResponseEntity<?> response = modeloController.deletaModelo(modeloId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(modeloService, times(1)).deletaModelo(modeloId);
    }

    @Test
    public void testVerModeloReturnModelo() {

        int modelId = 1;
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
        int modelId = 10;
        ModeloService mockService = mock(ModeloService.class);
        when(mockService.verModelo(modelId)).thenReturn(null);
        ModeloController controller = new ModeloController(mockService);

        ResponseEntity<?> response = controller.verModelo(modelId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Modelo Não encontrado", ((ErroResponse) response.getBody()).getErro());
    }

    @Test
    public void testVerModeloIdInvalido() {
        int modelId = -1;
        ModeloController controller = new ModeloController(mock(ModeloService.class));

        ResponseEntity<?> response = controller.verModelo(modelId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id precisa ser um numero inteiro positivo", ((ErroResponse) response.getBody()).getErro());
    }

    @Test
    public void testEditaModelo(){
        ModeloEditRequest modelo = new ModeloEditRequest();
        ResponseEntity<?> response = modeloController.editaModelo(modelo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nome do modelo n\u00E3o pode ser nulo", ((ErroResponse) response.getBody()).getErro());

    }

}
