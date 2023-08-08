package fiscalizacao.dsbrs.agems.apis.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class PatternFilter extends OncePerRequestFilter {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final Pattern EMAIL_PATTERN = Pattern.compile(
    "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$"
  );

  private static final Pattern PASSWORD_PATTERN = Pattern.compile(
    "^(?=.*[0-9])(?=.*[-+.\\[\\]~_?:!@#$%^&*])[a-zA-Z0-9-+.\\[\\]~_?:!@#$%^&*]{8,}$"
  );

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  )
    throws ServletException, IOException, IncorrectResultSizeDataAccessException, NonUniqueResultException {
    if (
      request.getServletPath().contains("/swagger-ui") ||
      request.getServletPath().contains("/api-docs") ||
      request.getServletPath().contains("/usuarios/logout")
    ) {
      filterChain.doFilter(request, response);
      return;
    }
    if (
      request.getServletPath().contains("/usuario/") ||
      request.getServletPath().contains("/usuarios/renovar-token") ||
      request.getServletPath().contains("/form") ||
      request.getServletPath().contains("/unidade") ||
      request.getServletPath().contains("/modelo")
    ) {
      final String EMAIL_HEADER = (String) request.getAttribute(
        "EMAIL_USUARIO"
      );

      final String EMAIL_USUARIO = EMAIL_HEADER
        .substring(EMAIL_HEADER.indexOf(":") + 1)
        .trim();
      if (
        EMAIL_USUARIO != null && !EMAIL_PATTERN.matcher(EMAIL_USUARIO).matches()
      ) {
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.BAD_REQUEST.value(),
          "Email Inválido"
        );
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
        return;
      }
    }

    if (
      request.getServletPath().contains("/usuarios/cadastro") ||
      request.getServletPath().contains("/usuarios/autenticar")
    ) {
      String requestBody = (String) request.getAttribute(
        "REQUEST_BODY_ATTRIBUTE"
      );
      JsonNode jsonBody = OBJECT_MAPPER.readTree(requestBody);

      String email = jsonBody.get("email").asText();
      if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.BAD_REQUEST.value(),
          "Email Inválido"
        );
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
        return;
      }
      String senha = jsonBody.get("senha").asText();
      if (senha != null && !PASSWORD_PATTERN.matcher(senha).matches()) {
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.BAD_REQUEST.value(),
          "Senha Inválida"
        );
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
        return;
      }
    }
    if (
      (
        request.getServletPath().contains("/usuario/") &&
        request.getMethod().equals("PUT")
      )
    ) {
      String requestBody = (String) request.getAttribute(
        "REQUEST_BODY_ATTRIBUTE"
      );
      JsonNode jsonBody = OBJECT_MAPPER.readTree(requestBody);
      
      if (jsonBody.get("senha") != null && jsonBody.get("senhaNova") != null) {
        String senha = jsonBody.get("senha").asText();
        String senhaNova = jsonBody.get("senhaNova").asText();

        if (
          senha != null &&
          !senha.isEmpty() &&
          !senha.isBlank() &&
          !PASSWORD_PATTERN.matcher(senha).matches()
        ) {
          ErroResponse errorResponse = new ErroResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Senha Inválida"
          );
          response.setStatus(HttpStatus.BAD_REQUEST.value());
          response.setContentType("application/json");
          response.getWriter().write(convertObjectToJson(errorResponse));
          return;
        }

        if (
          senhaNova != null &&
          !senhaNova.isEmpty() &&
          !senhaNova.isBlank() &&
          !PASSWORD_PATTERN.matcher(senhaNova).matches()
        ) {
          ErroResponse errorResponse = new ErroResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Nova senha Inválida"
          );
          response.setStatus(HttpStatus.BAD_REQUEST.value());
          response.setContentType("application/json");
          response.getWriter().write(convertObjectToJson(errorResponse));
          return;
        }
      }
    }

    filterChain.doFilter(request, response);
    return;
  }

  private String convertObjectToJson(Object object)
    throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(object);
  }
}
