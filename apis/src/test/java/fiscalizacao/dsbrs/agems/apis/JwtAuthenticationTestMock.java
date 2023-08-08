package fiscalizacao.dsbrs.agems.apis;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import fiscalizacao.dsbrs.agems.apis.config.JwtAuthenticationFilter;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtAuthenticationTestMock {

  @Test
  public void testUsernameNotFoundException() throws Exception {
    JwtService jwtServiceMock = mock(JwtService.class);
    UserDetailsService userDetailsServiceMock = mock(UserDetailsService.class);

    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
      jwtServiceMock,
      userDetailsServiceMock,
      null
    );

    HttpServletRequest requestMock = mock(HttpServletRequest.class);
    HttpServletResponse responseMock = mock(HttpServletResponse.class);

    String invalidUsername = "invalidUsername";
    when(jwtServiceMock.extrairUsername(anyString()))
      .thenReturn(invalidUsername);
    when(userDetailsServiceMock.loadUserByUsername(invalidUsername))
      .thenThrow(new UsernameNotFoundException("User not found"));

    Method protectedMethod =
      JwtAuthenticationFilter.class.getDeclaredMethod(
          "doFilterInternal",
          HttpServletRequest.class,
          HttpServletResponse.class,
          FilterChain.class
        );
    when(requestMock.getServletPath()).thenReturn("/usuario/");
    FilterChain filterChainMock = mock(FilterChain.class);
    PrintWriter printWriterMock = mock(PrintWriter.class);
    when(responseMock.getWriter()).thenReturn(printWriterMock);

    protectedMethod.setAccessible(true);
    protectedMethod.invoke(filter, requestMock, responseMock, filterChainMock);

    verify(responseMock).setContentType("application/json");
  }
}
