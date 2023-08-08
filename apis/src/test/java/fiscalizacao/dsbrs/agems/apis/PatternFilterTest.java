package fiscalizacao.dsbrs.agems.apis;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fiscalizacao.dsbrs.agems.apis.config.PatternFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PatternFilterTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  private PatternFilter filter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    filter = new PatternFilter();
  }

  @Test
  void testDoFilterInternalSwaggerUIPath()
    throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/swagger-ui");

    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalApiDocs() throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/api-docs");

    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalUSuariosLogout()
    throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/usuario-logout");

    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalRenovarToken() throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/usuarios/renovar-token");
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: test@example.com");
    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalForm() throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/form");
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: test@example.com");
    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalModelo() throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/modelo");
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: test@example.com");
    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalUnidade() throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/unidade");
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: test@example.com");
    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalUsuarioCadastro()
    throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/usuarios/cadastro");
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: test@example.com");
    when(request.getAttribute("REQUEST_BODY_ATTRIBUTE"))
      .thenReturn(
        "{\"nome\": \"teste\",\"email\":\"test@example.com\",\"senha\":\"senha1234%\",\"cargo\": \"Coordenador\"}"
      );
    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalValidUsuarioPathWithEmailHeader()
    throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/usuario/");
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: test@example.com");
    when(request.getMethod()).thenReturn("GET");
    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }
}
