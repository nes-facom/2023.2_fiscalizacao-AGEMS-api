package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fiscalizacao.dsbrs.agems.apis.config.ApplicationConfig;
import fiscalizacao.dsbrs.agems.apis.config.JwtAuthenticationFilter;
import fiscalizacao.dsbrs.agems.apis.config.PatternFilter;
import fiscalizacao.dsbrs.agems.apis.config.RequestBodyCaptureFilter;
import fiscalizacao.dsbrs.agems.apis.controller.AuthenticationController;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CadastroTest {

  @Autowired
  private AuthenticationController authenticationControllerAutowired;

  @Order(1)
  @Test
  public void testCadastrarWithValidRequestReturnsCreatedResponse() {
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setSenha("fiscaliza-agems1#");
    request.setEmail("juliaacorazza@gmail.com");
    request.setCargo("Analista de Regulação");
    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body).getAccessToken());
  }

  @Order(2)
  @Test
  public void testCadastrarWithValidRequestReturnsConflictResponse() {
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setSenha("fiscaliza-agems1#");
    request.setEmail("juliaacorazza@gmail.com");
    request.setCargo("Analista de Regulação");

    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);

    assertNotNull(body);
    assertNotNull(((ErroResponse) body).getErro());
    assertNotNull(((ErroResponse) body).getStatus());
    assertEquals(
      HttpStatus.CONFLICT.value(),
      ((ErroResponse) body).getStatus()
    );
    assertEquals(
      "Usu\u00E1rio j\u00E1 cadastrado!",
      ((ErroResponse) body).getErro()
    );
    assertTrue(body instanceof ErroResponse);
  }

  @Order(3)
  @Test
  public void testCadastrarWithBadRequestRequestReturnsErroResponseNoContent() {
    RegisterRequest request = new RegisterRequest();
    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((ErroResponse) body).getErro());
    assertNotNull(((ErroResponse) body).getStatus());
    assertEquals(
      HttpStatus.BAD_REQUEST.value(),
      ((ErroResponse) body).getStatus()
    );
    assertEquals("Faltam dados.", ((ErroResponse) body).getErro());
    assertTrue(body instanceof ErroResponse);
  }

  @Order(4)
  @Test
  public void testCadastrarWithBadRequestRequestReturnsErroResponseNoEmail() {
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setSenha("fiscaliza-agems1#");

    request.setCargo("Analista de Regulação");
    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((ErroResponse) body).getErro());
    assertNotNull(((ErroResponse) body).getStatus());
    assertEquals(
      HttpStatus.BAD_REQUEST.value(),
      ((ErroResponse) body).getStatus()
    );
    assertEquals("Faltam dados.", ((ErroResponse) body).getErro());
    assertTrue(body instanceof ErroResponse);
  }

  @Order(5)
  @Test
  public void testCadastrarWithBadRequestRequestReturnsErroResponseNoPassword() {
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setEmail("juliaacorazzagmail.com");
    request.setCargo("Analista de Regulação");
    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((ErroResponse) body).getErro());
    assertNotNull(((ErroResponse) body).getStatus());
    assertEquals(
      HttpStatus.BAD_REQUEST.value(),
      ((ErroResponse) body).getStatus()
    );
    assertEquals("Faltam dados.", ((ErroResponse) body).getErro());
    assertTrue(body instanceof ErroResponse);
  }

  @Order(6)
  @Test
  public void testCadastrarWithBadRequestRequestReturnsErroResponseNoName() {
    RegisterRequest request = new RegisterRequest();

    request.setEmail("juliaacorazza@gmail.com");
    request.setSenha("fiscaliza-agems1#");
    request.setCargo("Analista de Regulação");

    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((ErroResponse) body).getErro());
    assertNotNull(((ErroResponse) body).getStatus());
    assertEquals(
      HttpStatus.BAD_REQUEST.value(),
      ((ErroResponse) body).getStatus()
    );
    assertEquals("Faltam dados.", ((ErroResponse) body).getErro());
    assertTrue(body instanceof ErroResponse);
  }

  @Order(7)
  @Test
  public void testCadastrarWithBadRequestRequestReturnsErroResponseNoCargo() {
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setEmail("juliaacorazza@gmail.com");
    request.setSenha("fiscaliza-agems1#");

    ResponseEntity<Response> response = authenticationControllerAutowired.cadastrar(
      request
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((ErroResponse) body).getErro());
    assertNotNull(((ErroResponse) body).getStatus());
    assertEquals(
      HttpStatus.BAD_REQUEST.value(),
      ((ErroResponse) body).getStatus()
    );
    assertEquals("Faltam dados.", ((ErroResponse) body).getErro());
    assertTrue(body instanceof ErroResponse);
  }
}
