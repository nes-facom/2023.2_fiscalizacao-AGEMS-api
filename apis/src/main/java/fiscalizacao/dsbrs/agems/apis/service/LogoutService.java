package fiscalizacao.dsbrs.agems.apis.service;

import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.repositorio.TokenRepositorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepositorio REPOSITORIO_TOKEN;

  @Override
  public void logout(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) {
    final String AUTH_HEADER = request.getHeader("Authorization");
    final String JWT;
    if (AUTH_HEADER == null || !AUTH_HEADER.startsWith("Bearer ")) {
      return;
    }
    JWT = AUTH_HEADER.substring(7);
    Token storedToken = REPOSITORIO_TOKEN.findByToken(JWT).orElse(null);
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      REPOSITORIO_TOKEN.save(storedToken);
      SecurityContextHolder.clearContext();
    }
  }
}
