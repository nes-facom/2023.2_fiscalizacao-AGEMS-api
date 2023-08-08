package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fiscalizacao.dsbrs.agems.apis.config.ApplicationConfig;
import fiscalizacao.dsbrs.agems.apis.config.JwtAuthenticationFilter;
import fiscalizacao.dsbrs.agems.apis.config.PatternFilter;
import fiscalizacao.dsbrs.agems.apis.config.RequestBodyCaptureFilter;
import fiscalizacao.dsbrs.agems.apis.controller.AuthenticationController;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
@ContextConfiguration(
  classes = {
    TestConfig.class,
    ApplicationConfig.class,
    JwtService.class,
    AuthenticationController.class,
    JwtAuthenticationFilter.class,
    RequestBodyCaptureFilter.class,
    PatternFilter.class,
  }
)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AutenticarTest {


  @Autowired
  private AuthenticationController authenticationControllerAutowired;

  public void cadastro() {
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setSenha("fiscaliza-agems1#");
    request.setEmail("juliaacorazza@outlook.com");
    request.setCargo("Analista de Regulação");

    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(
      ((AuthenticationResponse) body).getRefreshToken()
    );
    assertNotNull(
      ((AuthenticationResponse) body).getAccessToken()
    );
  }

  @Test
  public void testAutenticarEmailESenhaInValidos() {
   
    AuthenticationRequest authenticationRequest = new AuthenticationRequest(
      "juliaacorazza@outlook.com",
      "fiscaliza-agems1#"
    );

    ResponseEntity<?> response =  authenticationControllerAutowired.autenticar(authenticationRequest);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }
}
