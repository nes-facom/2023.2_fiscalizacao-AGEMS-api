package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fiscalizacao.dsbrs.agems.apis.controller.AuthenticationController;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private AuthenticationController authenticationController;

  @BeforeEach
  public void setUp() {}

  @Test
  public void testCadastrarValidRequestReturnsCreatedResponse() {
    RegisterRequest request = new RegisterRequest();
    Response expectedResponse = new AuthenticationResponse();
    when(jwtService.cadastrar(request)).thenReturn(expectedResponse);

    ResponseEntity<Response> response = authenticationController.cadastrar(
      request
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  public void testCadastrarBadRequestRequestReturnsErroResponse() {
    RegisterRequest registerRequest = new RegisterRequest();

    Response expectedResponse = ErroResponse
      .builder()
      .erro("Faltam dados.")
      .status(400)
      .build();
    when(jwtService.cadastrar(registerRequest)).thenReturn(expectedResponse);
    ResponseEntity<Response> responseEntity = authenticationController.cadastrar(
      registerRequest
    );
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody() instanceof ErroResponse);
    verify(jwtService).cadastrar(registerRequest);
  }

   @Test
    public void testRenovarTokenErrorResponse() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ErroResponse erroResponse = ErroResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .erro("E-mail não encontrado")
                .build();
        when(jwtService.renovarToken(request, response)).thenReturn(erroResponse);

        ResponseEntity<Response> responseEntity = authenticationController.renovarToken(request, response);

        verify(jwtService).renovarToken(request, response);

       
        assert responseEntity != null;
        assert responseEntity.getStatusCode() == HttpStatus.NOT_FOUND;
        assert responseEntity.getBody() == erroResponse;
    }
}
