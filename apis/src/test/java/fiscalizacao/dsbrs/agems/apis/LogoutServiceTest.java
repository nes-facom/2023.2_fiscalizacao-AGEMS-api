package fiscalizacao.dsbrs.agems.apis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import fiscalizacao.dsbrs.agems.apis.config.SecurityConfiguration;
import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.repositorio.TokenRepositorio;
import fiscalizacao.dsbrs.agems.apis.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
  classes = { SecurityConfiguration.class, SecurityContextHolder.class }
)
class LogoutServiceTest {

  @Mock
  private static SecurityContextHolder securityContextHolder;

  @Mock
  private TokenRepositorio tokenRepositorio;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private Authentication authentication;

  private LogoutService logoutService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    logoutService = new LogoutService(tokenRepositorio);
  }

  @Test
  void testLogoutValidTokenRevokesAndClearsContext() {
    final String token = "valid-token";
    final String authHeader = "Bearer " + token;
    Token storedToken = new Token();
    storedToken.setToken(token);

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(tokenRepositorio.findByToken(token))
      .thenReturn(java.util.Optional.of(storedToken));

    logoutService.logout(request, response, authentication);

    verify(tokenRepositorio).findByToken(token);
    verify(tokenRepositorio).save(storedToken);
    SecurityContextHolder.clearContext();
  }

  @Test
  void testLogoutInvalidTokenDoesNotRevokeOrClearContext() {
    final String token = "invalid-token";
    final String authHeader = "Bearer " + token;

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(tokenRepositorio.findByToken(token))
      .thenReturn(java.util.Optional.empty());

    logoutService.logout(request, response, authentication);
    verify(tokenRepositorio).findByToken(token);
    verify(tokenRepositorio, never()).save(any());
    SecurityContextHolder.clearContext();
  }

  @Test
  void testLogoutNoTokenDoesNotRevokeOrClearContext() {
    when(request.getHeader("Authorization")).thenReturn(null);

    logoutService.logout(request, response, authentication);

    verify(tokenRepositorio, never()).findByToken(any());
    verify(tokenRepositorio, never()).save(any());
    SecurityContextHolder.clearContext();
  }
}
