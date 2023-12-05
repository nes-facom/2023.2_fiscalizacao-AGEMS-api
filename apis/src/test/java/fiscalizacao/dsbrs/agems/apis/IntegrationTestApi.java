package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.UsuarioEditRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.InfoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class IntegrationTestApi {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String accessToken;

  @BeforeEach
  public void setup() {
    restTemplate
      .getRestTemplate()
      .getMessageConverters()
      .add(new MappingJackson2HttpMessageConverter());
    restTemplate
      .getRestTemplate()
      .setInterceptors(Collections.singletonList(acceptHeaderInterceptor()));
  }

  private ClientHttpRequestInterceptor acceptHeaderInterceptor() {
    return (request, body, execution) -> {
      request
        .getHeaders()
        .set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
      return execution.execute(request, body);
    };
  }

  @Test
  @Order(1)
  public void testCadastraUsuario201() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
	  LocalDateTime.parse("2007-12-03T10:15:30"),
	  "Ronaldo Silva da Sousa",
	  "rss@smail.com",
	  "rss1234%",
	  Cargo.ASSESSOR_TECNICO
	);
    ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
      url,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    AuthenticationResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof AuthenticationResponse);
    assertNotNull(((AuthenticationResponse) body).getAccessToken());
    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    accessToken = ((AuthenticationResponse) body).getAccessToken();
    ((AuthenticationResponse) body).getRefreshToken();
  }

  @Test
  @Order(2)
  public void testInfoUsuario200() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
      LocalDateTime.parse("2007-12-03T10:15:30"),
      "Ronaldo Silva da Sousa",
      "ronaldinho.silva@smail.com",
      "rss1234%",
      Cargo.ASSESSOR_TECNICO
    );
    ResponseEntity<AuthenticationResponse> responseRegister = restTemplate.postForEntity(
      url,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.CREATED, responseRegister.getStatusCode());
    AuthenticationResponse bodyAuth = responseRegister.getBody();
    assertNotNull(bodyAuth);
    assertTrue(bodyAuth instanceof Response);
    assertTrue(bodyAuth instanceof AuthenticationResponse);
    assertNotNull(((AuthenticationResponse) bodyAuth).getAccessToken());
    assertNotNull(((AuthenticationResponse) bodyAuth).getRefreshToken());
    accessToken = ((AuthenticationResponse) bodyAuth).getAccessToken();
    ((AuthenticationResponse) bodyAuth).getRefreshToken();
    url = "http://localhost:" + port + "/usuario/";
    assertNotNull(accessToken);
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    ResponseEntity<InfoResponse> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      httpEntity,
      InfoResponse.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
    InfoResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof InfoResponse);
    assertNotNull(((InfoResponse) body).getId());
    assertNotNull(((InfoResponse) body).getNome());
    assertNotNull(((InfoResponse) body).getEmail());
    assertNotNull(((InfoResponse) body).getSenha());
    assertNotNull(((InfoResponse) body).getCargo());
  }

  @Test
  @Order(2)
  public void testEditUsuario200Nome() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
      LocalDateTime.parse("2007-12-03T10:15:30"),
      "Binho Silva da Sousa",
      "binho.silva@smail.com",
      "binho1234%",
      Cargo.ASSESSOR_TECNICO
    );
    ResponseEntity<AuthenticationResponse> responseRegister = restTemplate.postForEntity(
      url,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.CREATED, responseRegister.getStatusCode());
    AuthenticationResponse bodyAuth = responseRegister.getBody();
    assertNotNull(bodyAuth);
    assertTrue(bodyAuth instanceof Response);
    assertTrue(bodyAuth instanceof AuthenticationResponse);
    assertNotNull(((AuthenticationResponse) bodyAuth).getAccessToken());
    assertNotNull(((AuthenticationResponse) bodyAuth).getRefreshToken());
    accessToken = ((AuthenticationResponse) bodyAuth).getAccessToken();
    ((AuthenticationResponse) bodyAuth).getRefreshToken();
    url = "http://localhost:" + port + "/usuario/";
    assertNotNull(accessToken);
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    headers.setContentType(MediaType.APPLICATION_JSON);
    UsuarioEditRequest requestEdit = new UsuarioEditRequest(
      "Binho Silva de Sousa",
      " ",
      " "
    );
    HttpEntity<UsuarioEditRequest> httpEntity = new HttpEntity<>(
      requestEdit,
      headers
    );
    ResponseEntity<InfoResponse> response = restTemplate.exchange(
      url,
      HttpMethod.PUT,
      httpEntity,
      InfoResponse.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
    InfoResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof InfoResponse);
    assertNotNull(((InfoResponse) body).getId());
    assertNotNull(((InfoResponse) body).getNome());
    assertNotNull(((InfoResponse) body).getEmail());
    assertNotNull(((InfoResponse) body).getSenha());
    assertNotNull(((InfoResponse) body).getCargo());
    assertEquals("Binho Silva de Sousa", ((InfoResponse) body).getNome());
    assertNotEquals("Binho Silva da Sousa", ((InfoResponse) body).getNome());
  }

  @Test
  @Order(2)
  public void testEditUsuario200Senha() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
      LocalDateTime.parse("2007-12-03T10:15:30"),
      "Wendy Silva da Sousa",
      "wendy.silva@smail.com",
      "binho1234%",
      Cargo.ASSESSOR_TECNICO
    );
    ResponseEntity<AuthenticationResponse> responseRegister = restTemplate.postForEntity(
      url,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.CREATED, responseRegister.getStatusCode());
    AuthenticationResponse bodyAuth = responseRegister.getBody();
    assertNotNull(bodyAuth);
    assertTrue(bodyAuth instanceof Response);
    assertTrue(bodyAuth instanceof AuthenticationResponse);
    assertNotNull(((AuthenticationResponse) bodyAuth).getAccessToken());
    assertNotNull(((AuthenticationResponse) bodyAuth).getRefreshToken());
    accessToken = ((AuthenticationResponse) bodyAuth).getAccessToken();
    ((AuthenticationResponse) bodyAuth).getRefreshToken();
    url = "http://localhost:" + port + "/usuario/";
    assertNotNull(accessToken);
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    headers.setContentType(MediaType.APPLICATION_JSON);
    UsuarioEditRequest requestEdit = new UsuarioEditRequest(
      " ",
      "binho1234%",
      "wendy1234!"
    );
    HttpEntity<UsuarioEditRequest> httpEntity = new HttpEntity<>(
      requestEdit,
      headers
    );
    ResponseEntity<InfoResponse> response = restTemplate.exchange(
      url,
      HttpMethod.PUT,
      httpEntity,
      InfoResponse.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
    InfoResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof InfoResponse);
    assertNotNull(((InfoResponse) body).getId());
    assertNotNull(((InfoResponse) body).getNome());
    assertNotNull(((InfoResponse) body).getEmail());
    assertNotNull(((InfoResponse) body).getSenha());
    assertNotNull(((InfoResponse) body).getCargo()); 
  }

  @Test
  @Order(3)
  public void testCadastraUsuario409() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
      LocalDateTime.parse("2007-12-03T10:15:30"),
      "Ronaldo Silva da Sousa",
      "rss@smail.com",
      "rss1234%",
      Cargo.ASSESSOR_TECNICO
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(409, ((ErroResponse) body).getStatus());
    assertEquals(
      "Usu\u00E1rio j\u00E1 cadastrado!",
      ((ErroResponse) body).getErro()
    );
  }

  @Test
  @Order(4)
  public void testCadastraUsuarioEmail400() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
      LocalDateTime.parse("2007-12-03T10:15:30"),
      "Ronaldo Silva da Sousa",
      "Ronaldo Silva da Sousa",
      "rss1234%",
      Cargo.ASSESSOR_TECNICO
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(400, ((ErroResponse) body).getStatus());
    assertEquals("Email Inv\u00E1lido", ((ErroResponse) body).getErro());
  }

  @Test
  @Order(5)
  public void testCadastraUsuarioSenha400() {
    String url = "http://localhost:" + port + "/usuarios/cadastro";
    RegisterRequest request = new RegisterRequest(
      LocalDateTime.parse("2007-12-03T10:15:30"),
      "Ronaldo Silva da Sousa",
      "rss@smail.com",
      "rss",
      Cargo.ASSESSOR_TECNICO
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(400, ((ErroResponse) body).getStatus());
    assertEquals("Senha Inv\u00E1lida", ((ErroResponse) body).getErro());
  }

  @Test
  @Order(6)
  public void testAutenticar200() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest(
      "ronaldinho.silva@smail.com",
      "rss1234%"
    );
    synchronized (this) {
      wait(3000);
    }
    ResponseEntity<AuthenticationResponse> responseRegister = restTemplate.postForEntity(
      url,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.OK, responseRegister.getStatusCode());
    AuthenticationResponse body = responseRegister.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof AuthenticationResponse);
    assertNotNull(
      ((AuthenticationResponse) body).getAccessToken()
    );
    assertNotNull(
      ((AuthenticationResponse) body).getRefreshToken()
    );
    accessToken =
      ((AuthenticationResponse) body).getAccessToken();
  }

  @Test
  @Order(7)
  public void testAutenticar400EmailNull() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest();
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(400, ((ErroResponse) body).getStatus());
    assertEquals("Email Inválido", ((ErroResponse) body).getErro());
  }

  @Test
  @Order(8)
  public void testAutenticar403() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest(
      "wesley.silva@smail.com",
      "rss1234%"
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(403, ((ErroResponse) body).getStatus());
    assertEquals(
      "Bad Credentials:Bad credentials",
      ((ErroResponse) body).getErro()
    );
  }

  @Test
  @Order(9)
  public void testAutenticar400() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = null;
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(400, ((ErroResponse) body).getStatus());
    assertEquals(
      "Erro na requisi\u00E7\u00E3o",
      ((ErroResponse) body).getErro()
    );
  }

  @Test
  @Order(10)
  public void testAutenticar400Email() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest(
      "Ronald.com",
      "ronald"
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(400, ((ErroResponse) body).getStatus());
    assertEquals("Email Inválido", ((ErroResponse) body).getErro());
  }

  @Test
  @Order(11)
  public void testAutenticar400Senha() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest(
      "ronaldinho.silva@smail.com",
      "ronald"
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(400, ((ErroResponse) body).getStatus());
    assertEquals("Senha Inválida", ((ErroResponse) body).getErro());
  }

  @Test
  @Order(12)
  public void testAutenticar403Senha() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest(
      "ronaldinho.silva@smail.com",
      "ronald%%12"
    );
    ResponseEntity<ErroResponse> response = restTemplate.postForEntity(
      url,
      request,
      ErroResponse.class
    );
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(403, ((ErroResponse) body).getStatus());
    assertEquals(
      "Bad Credentials:Bad credentials",
      ((ErroResponse) body).getErro()
    );
  }

  @Test
  @Order(13)
  public void testInfo401() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuario/";
    HttpHeaders headers = new HttpHeaders();
    
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    ResponseEntity<ErroResponse> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      httpEntity,
      ErroResponse.class
    );
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(401, ((ErroResponse) body).getStatus());
    assertEquals(
      "Token inv\u00E1lido ou inexistente",
      ((ErroResponse) body).getErro()
    );
  }
  @Test
  @Order(14)
  public void testInfo403() throws InterruptedException {
    String url = "http://localhost:" + port + "/usuario/";
     HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + "vdsjnjkdsnc.vfdgbg");

    HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
     ResponseEntity<ErroResponse> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      httpEntity,
      ErroResponse.class
    );
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(403, ((ErroResponse) body).getStatus());
    assertEquals(
      "Token malformado",
      ((ErroResponse) body).getErro()
    );
  }
  @Test
  @Order(16)
  public void testEditUsuario200NomeSenha() throws InterruptedException{
     String url = "http://localhost:" + port + "/usuarios/autenticar";
    AuthenticationRequest request = new AuthenticationRequest(
      "ronaldinho.silva@smail.com",
      "rss1234%"
    );
   synchronized (this) {
      wait(3000);
    }
    ResponseEntity<AuthenticationResponse> responseRegister = restTemplate.postForEntity(
      url,
      request,
      AuthenticationResponse.class
    );
    assertEquals(HttpStatus.OK, responseRegister.getStatusCode());
    AuthenticationResponse body = responseRegister.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof AuthenticationResponse);
    assertEquals(HttpStatus.OK, responseRegister.getStatusCode());
    AuthenticationResponse body3 = responseRegister.getBody();
    assertNotNull(body3);
    assertTrue(body3 instanceof Response);
    assertTrue(body3 instanceof AuthenticationResponse);
    assertNotNull(
      ((AuthenticationResponse) body3).getAccessToken()
    );
    assertNotNull(
      ((AuthenticationResponse) body3).getRefreshToken()
    );
    accessToken =
      ((AuthenticationResponse) body3).getAccessToken();
    accessToken =
      ((AuthenticationResponse) body3).getAccessToken();
     url = "http://localhost:" + port + "/usuario/";
   HttpHeaders headers = new HttpHeaders();
   headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    headers.setContentType(MediaType.APPLICATION_JSON);
    UsuarioEditRequest requestEdit = new UsuarioEditRequest(
      "Ronaldo Gaucho ",
      "rss1234%",
      "rss1234!"
    );
    HttpEntity<UsuarioEditRequest> httpEntity = new HttpEntity<>(
      requestEdit,
      headers
    );
    ResponseEntity<ErroResponse> response = restTemplate.exchange(
      url,
      HttpMethod.PUT,
      httpEntity,
      ErroResponse.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
   assertEquals(HttpStatus.OK, responseRegister.getStatusCode());
    AuthenticationResponse body2 = responseRegister.getBody();
    assertNotNull(body2);
    assertTrue(body2 instanceof Response);
    assertTrue(body2 instanceof AuthenticationResponse);
    assertNotNull(
      ((AuthenticationResponse) body2).getAccessToken()
    );
    assertNotNull(
      ((AuthenticationResponse) body2).getRefreshToken()
    );
    accessToken =
      ((AuthenticationResponse) body2).getAccessToken();
  }
  @Test
  @Order(17)
  public void testEditUsuario403(){
    String url = "http://localhost:" + port + "/usuario/";
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer bjbjkbjknjkjkjf");

    headers.setContentType(MediaType.APPLICATION_JSON);
    UsuarioEditRequest requestEdit = new UsuarioEditRequest(
      " ",
      "binho1234%",
      "wendy1234!"
    );
    HttpEntity<UsuarioEditRequest> httpEntity = new HttpEntity<>(
      requestEdit,
      headers
    );
    ResponseEntity<ErroResponse> response = restTemplate.exchange(
      url,
      HttpMethod.PUT,
      httpEntity,
      ErroResponse.class
    );
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
     ErroResponse body = response.getBody();
    assertNotNull(body);
    assertTrue(body instanceof Response);
    assertTrue(body instanceof ErroResponse);
    assertNotNull(((ErroResponse) body).getStatus());
    assertNotNull(((ErroResponse) body).getErro());
    assertEquals(403, ((ErroResponse) body).getStatus());
    assertEquals(
      "Token malformado",
      ((ErroResponse) body).getErro()
    );
  }
}
