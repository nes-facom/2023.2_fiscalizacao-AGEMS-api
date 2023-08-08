package fiscalizacao.dsbrs.agems.apis.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiscalizacao.dsbrs.agems.apis.repositorio.TokenRepositorio;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService JWT_SERVICE;
  private final UserDetailsService USER_DETAILS_SERVICE;
  private final TokenRepositorio REPOSITORIO_TOKEN;

  @Override
  protected void doFilterInternal(
    HttpServletRequest requisicao,
    HttpServletResponse resposta,
    FilterChain filterChain
  ) throws ServletException, IOException {
    if (
      requisicao.getServletPath().contains("/usuarios/cadastro") ||
      requisicao.getServletPath().contains("/usuarios/autenticar") ||
      requisicao.getServletPath().contains("/swagger-ui") ||
      requisicao.getServletPath().contains("/api-docs") ||
      requisicao.getServletPath().contains("/usuarios/logout")
    ) {
      filterChain.doFilter(requisicao, resposta);
      return;
    }

    final String AUTH_HEADER = requisicao.getHeader("Authorization");
    final String JWT;
    final String EMAIL_USUARIO;
    if (AUTH_HEADER == null || !AUTH_HEADER.startsWith("Bearer ")) {
      resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      ErroResponse errorResponse = new ErroResponse(
        HttpStatus.UNAUTHORIZED.value(),
        "Token inv\u00e1lido ou inexistente"
      );
      resposta.setStatus(HttpStatus.UNAUTHORIZED.value());
      resposta.setContentType("application/json");
      resposta.getWriter().write(convertObjectToJson(errorResponse));
      return;
    }
    JWT = AUTH_HEADER.substring(7);
    try {
      EMAIL_USUARIO = JWT_SERVICE.extrairUsername(JWT);
      if (EMAIL_USUARIO == null) {
        resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.UNAUTHORIZED.value(),
          "Erro em recuperar e-mail pelo token."
        );
        resposta.setStatus(HttpStatus.UNAUTHORIZED.value());
        resposta.setContentType("application/json");
        resposta.getWriter().write(convertObjectToJson(errorResponse));
        return;
      }
      if (requisicao.getServletPath().contains("/usuarios/renovar-token")) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
          try {
            UserDetails detalhesUsuario =
              this.USER_DETAILS_SERVICE.loadUserByUsername(EMAIL_USUARIO);

            if (JWT_SERVICE.isTokenValid(JWT, detalhesUsuario)) {
              UsernamePasswordAuthenticationToken tokenAutorizado = new UsernamePasswordAuthenticationToken(
                detalhesUsuario,
                null,
                detalhesUsuario.getAuthorities()
              );
              tokenAutorizado.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(requisicao)
              );
              SecurityContextHolder
                .getContext()
                .setAuthentication(tokenAutorizado);
            }
          } catch (UsernameNotFoundException e) {
            ErroResponse errorResponse = new ErroResponse(
              HttpStatus.NOT_FOUND.value(),
              e.getMessage()
            );
            resposta.setStatus(HttpStatus.NOT_FOUND.value());
            resposta.setContentType("application/json");
            resposta.getWriter().write(convertObjectToJson(errorResponse));
            return;
          }
        }
      } else {
        boolean isTokenValid = REPOSITORIO_TOKEN
          .findByToken(JWT)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);
        if (!isTokenValid) {
          resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          ErroResponse errorResponse = new ErroResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "O Token fornecido não é válido"
          );
          resposta.setStatus(HttpStatus.UNAUTHORIZED.value());
          resposta.setContentType("application/json");
          resposta.getWriter().write(convertObjectToJson(errorResponse));
          return;
        } else {
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
              UserDetails detalhesUsuario =
                this.USER_DETAILS_SERVICE.loadUserByUsername(EMAIL_USUARIO);

              if (JWT_SERVICE.isTokenValid(JWT, detalhesUsuario)) {
                UsernamePasswordAuthenticationToken tokenAutorizado = new UsernamePasswordAuthenticationToken(
                  detalhesUsuario,
                  null,
                  detalhesUsuario.getAuthorities()
                );
                tokenAutorizado.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(requisicao)
                );
                SecurityContextHolder
                  .getContext()
                  .setAuthentication(tokenAutorizado);
              }
            } catch (UsernameNotFoundException e) {
              ErroResponse errorResponse = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
              );
              resposta.setStatus(HttpStatus.NOT_FOUND.value());
              resposta.setContentType("application/json");
              resposta.getWriter().write(convertObjectToJson(errorResponse));
              return;
            }
          }
        }
      }
    } catch (ExpiredJwtException | MalformedJwtException e) {
      if (e instanceof ExpiredJwtException) {
        resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.UNAUTHORIZED.value(),
          "Token Expirado"
        );
        resposta.setStatus(HttpStatus.UNAUTHORIZED.value());
        resposta.setContentType("application/json");
        resposta.getWriter().write(convertObjectToJson(errorResponse));
        return;
      } else {
        resposta.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.FORBIDDEN.value(),
          "Token malformado"
        );
        resposta.setStatus(HttpStatus.FORBIDDEN.value());
        resposta.setContentType("application/json");
        resposta.getWriter().write(convertObjectToJson(errorResponse));
        return;
      }
    }

    requisicao.setAttribute("EMAIL_USUARIO", EMAIL_USUARIO);
    filterChain.doFilter(requisicao, resposta);
  }

  private String convertObjectToJson(Object object)
    throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }
}
