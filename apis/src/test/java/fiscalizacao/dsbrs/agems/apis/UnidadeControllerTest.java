package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiscalizacao.dsbrs.agems.apis.controller.UnidadeController;
import fiscalizacao.dsbrs.agems.apis.requests.UnidadeRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.service.UnidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UnidadeControllerTest {

  @Mock
  private UnidadeService unidadeService;

  @InjectMocks
  private UnidadeController unidadeController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(unidadeController).build();
  }

  @Test
  void adicionaUnidadeShouldReturnCreated() throws Exception {
   
    UnidadeRequest request = new UnidadeRequest("Unidade 03", "Endereco 03", "Tratamento");

    UnidadeResponse expectedResponse = UnidadeResponse
      .builder()
      .id(1)
      .nome("Unidade 03")
      .endereco("Endereco 03")
      .build();
    when(unidadeService.cadastraUnidade(any(UnidadeRequest.class)))
      .thenReturn(expectedResponse);
    mockMvc
      .perform(
        post("/unidade/add")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request))
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.nome").value("Unidade 03"))
      .andExpect(jsonPath("$.endereco").value("Endereco 03"));
  }

  @Test
  void adicionaUnidadeShouldReturnBadRequest() throws Exception {

    UnidadeRequest request = new UnidadeRequest("Unidade 03", null, null);
    ErroResponse erroResponse = ErroResponse
      .builder()
      .status(HttpStatus.BAD_REQUEST.value())
      .erro("Id e Endere\u00e7o obrigat\u00f3rios")
      .build();

    when(unidadeService.cadastraUnidade(request)).thenReturn(erroResponse);

    ResponseEntity<Response> responseEntity = unidadeController.adicionaUnidade(
      request
    );
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    ErroResponse response = (ErroResponse) responseEntity.getBody();
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals("Id e Endere\u00e7o obrigat\u00f3rios", response.getErro());
  }

  @Test
  void adicionaUnidadeShouldReturnConflict() throws Exception {

    UnidadeRequest request = new UnidadeRequest("Unidade 03", "Endereço 03","Tratamento");

    ErroResponse erroResponse = ErroResponse
      .builder()
      .status(HttpStatus.CONFLICT.value())
      .erro("Unidade j\u00e1 existe")
      .build();

    when(unidadeService.cadastraUnidade(request)).thenReturn(erroResponse);

    ResponseEntity<Response> responseEntity = unidadeController.adicionaUnidade(
      request
    );
    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    ErroResponse response = (ErroResponse) responseEntity.getBody();
    assertNotNull(response);
    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    assertEquals("Unidade j\u00e1 existe", response.getErro());
  }
}
