package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import fiscalizacao.dsbrs.agems.apis.config.ApplicationConfig;
import fiscalizacao.dsbrs.agems.apis.config.JwtAuthenticationFilter;
import fiscalizacao.dsbrs.agems.apis.config.PatternFilter;
import fiscalizacao.dsbrs.agems.apis.config.RequestBodyCaptureFilter;
import fiscalizacao.dsbrs.agems.apis.controller.AuthenticationController;
import fiscalizacao.dsbrs.agems.apis.controller.FormularioController;
import fiscalizacao.dsbrs.agems.apis.controller.ModeloController;
import fiscalizacao.dsbrs.agems.apis.controller.UnidadeController;
import fiscalizacao.dsbrs.agems.apis.controller.UsuarioController;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.FormularioService;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import fiscalizacao.dsbrs.agems.apis.service.ModeloService;
import fiscalizacao.dsbrs.agems.apis.service.UnidadeService;
import fiscalizacao.dsbrs.agems.apis.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    UsuarioController.class,
    FormularioController.class,
    ModeloController.class,
    UserDetailsService.class,
    JwtService.class,
    FormularioService.class,
    UsuarioService.class,
    ModeloService.class,
    UnidadeService.class,
    UnidadeController.class,
    AuthenticationRequest.class,
    HttpServletRequest.class,
  }
)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JWTAuthenticationFilterTest {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private AuthenticationController authenticationController;

  @Mock
  private FilterChain filterChain;

  @Autowired
  private HttpServletRequest httpServletRequest;

  private MutableHttpServletRequest requestValidToken;

  private MutableHttpServletRequest invalidServletRequest;

  @Autowired
  private HttpServletResponse responseServ;

  @Autowired
  private HttpServletResponse responseInvalidServ;

  @Order(1)
  @Test
  public void testDoFilterInternalInvalidEmail()
    throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);
    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer " +
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4NzEyODI2NSwiZXhwIjoxNjg3MjE0NjY1fQ.mHG2yTYp2mOHV6Bznj4OmMLYI2jfhsTOzYQjq7WflDI"
    );
    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseServ.getStatus());
  }

  @Order(2)
  @Test
  public void testDoFilterInternalWithValidTokenShouldSetAuthentication()
    throws Exception {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);
    RegisterRequest request = new RegisterRequest();
    request.setNome("Julia Alves Corazza");
    request.setSenha("fiscaliza-agems1#");
    request.setEmail("juliaacorazza@gmail.com");
    request.setCargo("Analista de Regulação");
    ResponseEntity<Response> response = authenticationController.cadastrar(
      request
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body).getAccessToken());

    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer " + ((AuthenticationResponse) body).getAccessToken()
    );
    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());
    assertEquals(
      "juliaacorazza@gmail.com",
      requestValidToken.getAttribute("EMAIL_USUARIO")
    );
    assertEquals(
      true,
      SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
    );
    verify(filterChain).doFilter(requestValidToken, responseServ);
  }

  @Order(3)
  @Test
  public void testDoFilterInternalWithInvalidValidTokenShouldNotSetAuthentication()
    throws Exception {
    invalidServletRequest = new MutableHttpServletRequest(httpServletRequest);
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setNome("Luiza Alves Corazza");
    registerRequest.setSenha("fiscaliza-agems1#");
    registerRequest.setEmail("luucorazza@gmail.com");
    registerRequest.setCargo("Coordenador");
    ResponseEntity<Response> registerResponse = authenticationController.cadastrar(
      registerRequest
    );

    assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
    Response body = registerResponse.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body).getAccessToken());
    AuthenticationRequest authenticationRequest = new AuthenticationRequest(
      "luucorazza@gmail.com",
      "fiscaliza-agems1#"
    );
    synchronized (this) {
      wait(3000);
    }
    ResponseEntity<?> authenticationResponse = authenticationController.autenticar(
      authenticationRequest
    );
    assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
    Object body2 = authenticationResponse.getBody();
    assertTrue(body2 instanceof Response);
    assertNotNull(body2);

    assertNotNull(((AuthenticationResponse) body2).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body2).getAccessToken());

    invalidServletRequest.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer " + ((AuthenticationResponse) body).getAccessToken()
    );
    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) invalidServletRequest,
      responseInvalidServ,
      filterChain
    );
    assertEquals(
      HttpServletResponse.SC_UNAUTHORIZED,
      responseInvalidServ.getStatus()
    );
    assertNotEquals(
      "luucorazza@gmail.com",
      invalidServletRequest.getAttribute("EMAIL_USUARIO")
    );
    assertNull(invalidServletRequest.getAttribute("EMAIL_USUARIO"));
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Order(4)
  @Test
  public void testDoFilterInternalWithInvalidValidTokenShouldSetAuthentication()
    throws Exception {
    invalidServletRequest = new MutableHttpServletRequest(httpServletRequest);
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);
    synchronized (this) {
      wait(1000);
    }
    AuthenticationRequest authenticationRequest = new AuthenticationRequest(
      "luucorazza@gmail.com",
      "fiscaliza-agems1#"
    );

    ResponseEntity<?> authenticationResponse = authenticationController.autenticar(
      authenticationRequest
    );
    assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
    Object body = authenticationResponse.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);

    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body).getAccessToken());

    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) invalidServletRequest,
      responseInvalidServ,
      filterChain
    );

    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer " +
      (
        (AuthenticationResponse) body
      ).getAccessToken()
    );
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );

    assertEquals(
      "luucorazza@gmail.com",
      requestValidToken.getAttribute("EMAIL_USUARIO")
    );
    assertEquals(
      true,
      SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
    );
    verify(filterChain).doFilter(requestValidToken, responseServ);
  }

  @Order(5)
  @Test
  public void testDoFilterInternalWithValidRefreshTokenShouldSetAuthentication()
    throws Exception {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);
    RegisterRequest request = new RegisterRequest();
    request.setNome("Jandira Ferreira Jorge");
    request.setSenha("fiscaliza-agems1#");
    request.setEmail("jandireira@gmail.com");
    request.setCargo("Analista de Regulação");
    ResponseEntity<Response> response = authenticationController.cadastrar(
      request
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Response body = response.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body).getAccessToken());

    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer " + ((AuthenticationResponse) body).getRefreshToken()
    );
    requestValidToken.setServletPath("/usuarios/renovar-token");

    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );

    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());
    assertEquals(
      "jandireira@gmail.com",
      requestValidToken.getAttribute("EMAIL_USUARIO")
    );
    assertEquals(
      true,
      SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
    );
    synchronized (this) {
      wait(2000);
    }
    ResponseEntity<Response> responseRenovarToken = authenticationController.renovarToken(
      requestValidToken,
      responseServ
    );
    assertEquals(HttpStatus.OK, responseRenovarToken.getStatusCode());
    Response body2 = responseRenovarToken.getBody();
    assertTrue(body2 instanceof Response);
    assertNotNull(body2);
    assertNotNull(
      (
        (AuthenticationResponse) body2
      ).getRefreshToken()
    );
    assertNotNull(
      ((AuthenticationResponse) body2).getAccessToken()
    );

    verify(filterChain).doFilter(requestValidToken, responseServ);
  }

  @Order(6)
  @Test
  public void testDoFilterInternalWithValidTokenShouldSetAuthenticationAutenticar()
    throws Exception {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);

    AuthenticationRequest authenticationRequest = new AuthenticationRequest(
      "juliaacorazza@gmail.com",
      "fiscaliza-agems1#"
    );

    ResponseEntity<?> authenticationResponse = authenticationController.autenticar(
      authenticationRequest
    );
    assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
    Object body = authenticationResponse.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(((AuthenticationResponse) body).getRefreshToken());
    assertNotNull(((AuthenticationResponse) body).getAccessToken());

    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer " + ((AuthenticationResponse) body).getAccessToken()
    );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );

    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());
    assertEquals(
      "juliaacorazza@gmail.com",
      requestValidToken.getAttribute("EMAIL_USUARIO")
    );
    assertEquals(
      true,
      SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
    );
    verify(filterChain).doFilter(requestValidToken, responseServ);
  }

  @Order(7)
  @Test
  public void testDoFilterInternalWithInValidHeader() throws Exception {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);

    AuthenticationRequest authenticationRequest = new AuthenticationRequest(
      "juliaacorazza@gmail.com",
      "fiscaliza-agems1#"
    );
    synchronized (this) {
      wait(1000);
    }
    ResponseEntity<?> authenticationResponse = authenticationController.autenticar(
      authenticationRequest
    );
    assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
    Object body = authenticationResponse.getBody();
    assertTrue(body instanceof Response);
    assertNotNull(body);
    assertNotNull(
      (
        (AuthenticationResponse) body
      ).getRefreshToken()
    );
    assertNotNull(
      (
        (AuthenticationResponse) body
      ).getAccessToken()
    );

    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      (
        (AuthenticationResponse) body
      ).getAccessToken()
    );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );

    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseServ.getStatus());
  }

  @Order(9)
  @Test
  public void testDoFilterInternalWithMalformedToken() throws Exception {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);

    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    requestValidToken.putHeader(
      HttpHeaders.AUTHORIZATION,
      "Bearer nvkdfnvkn.vkdfnvjk"
    );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );

    assertEquals(HttpServletResponse.SC_FORBIDDEN, responseServ.getStatus());
  }

  @Order(8)
  @Test
  public void testDoFilterInternalShouldIgnore() throws Exception {
    requestValidToken = new MutableHttpServletRequest(httpServletRequest);
    requestValidToken.setServletPath("/usuarios/cadastro");
    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    protectedMethod.setAccessible(true);
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());
    verify(filterChain).doFilter(requestValidToken, responseServ);
    requestValidToken.setServletPath("/usuarios/autenticar");
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());

    requestValidToken.setServletPath("/swagger-ui");
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());

    requestValidToken.setServletPath("/api-docs");
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());

    requestValidToken.setServletPath("/usuarios/logout");
    protectedMethod.invoke(
      jwtAuthenticationFilter,
      (HttpServletRequest) requestValidToken,
      responseServ,
      filterChain
    );
    assertEquals(HttpServletResponse.SC_OK, responseServ.getStatus());
  }
}
