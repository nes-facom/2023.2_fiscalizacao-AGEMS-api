package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import fiscalizacao.dsbrs.agems.apis.controller.UsuarioController;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.InfoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

  @Mock
  private UsuarioService usuarioService;

  @InjectMocks
  private UsuarioController usuarioController;

  @Mock
  private HttpServletRequest httpServletRequest;

  @BeforeEach
  void setUp() {
    httpServletRequest = mock(HttpServletRequest.class);
  }

  @Test
  void infoUsuarioPorNomeShouldReturnCreated() throws Exception {
    InfoResponse expectedResponse = InfoResponse
      .builder()
      .nome("Julia Alves Corazza")
      .email("juliaacorazza@gmail.com")
      .cargo("Analista de Regulação")
      .senha("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6")
      .build();
    when(usuarioService.getInfUserToken(httpServletRequest))
      .thenReturn(expectedResponse);

    ResponseEntity<Response> responseEntity = usuarioController.findUsuario(
      httpServletRequest
    );
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), (expectedResponse));
    verify(usuarioService).getInfUserToken(httpServletRequest);
  }

  @Test
  void infoUsuarioPorIDShouldReturnCreated() throws Exception {
    InfoResponse expectedResponse = InfoResponse
      .builder()
      .nome("Julia Alves Corazza")
      .email("juliaacorazza@gmail.com")
      .cargo("Analista de Regulação")
      .senha("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6")
      .build();
    when(usuarioService.getInfUserToken(httpServletRequest))
      .thenReturn(expectedResponse);

    ResponseEntity<Response> responseEntity = usuarioController.findUsuario(
      httpServletRequest
    );
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), (expectedResponse));
    verify(usuarioService).getInfUserToken(httpServletRequest);
  }

  @Test
  void infoUsuarioPorIDShouldReturnNotFound() throws Exception {
    ErroResponse expectedResponse = ErroResponse
      .builder()
      .status(HttpStatus.NOT_FOUND.value())
      .erro("Usuário não existe")
      .build();
    when(usuarioService.getInfUserToken(httpServletRequest))
      .thenReturn(expectedResponse);
    ResponseEntity<Response> responseEntity = usuarioController.findUsuario(
      httpServletRequest
    );
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), (expectedResponse));
    verify(usuarioService).getInfUserToken(httpServletRequest);
  }

  @Test
  void infoUsuarioPorNomeShouldReturnNotFound() throws Exception {
    ErroResponse expectedResponse = ErroResponse
      .builder()
      .status(HttpStatus.NOT_FOUND.value())
      .erro("Usuário não existe")
      .build();
    when(usuarioService.getInfUserToken(httpServletRequest))
      .thenReturn(expectedResponse);
    ResponseEntity<Response> responseEntity = usuarioController.findUsuario(
      httpServletRequest
    );
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), (expectedResponse));
    verify(usuarioService).getInfUserToken(httpServletRequest);
  }
}
