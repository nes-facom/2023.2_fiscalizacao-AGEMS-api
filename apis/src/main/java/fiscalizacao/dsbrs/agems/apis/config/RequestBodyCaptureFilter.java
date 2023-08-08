package fiscalizacao.dsbrs.agems.apis.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestBodyCaptureFilter implements Filter {

  private static String REQUEST_BODY_ATTRIBUTE;

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      if (
        (
          ((HttpServletRequest) request).getServletPath()
            .contains("/usuario/") &&
          ((HttpServletRequest) request).getMethod().equals("GET")
        ) ||
        ((HttpServletRequest) request).getServletPath()
          .contains("/usuarios/renovar-token") ||
        ((HttpServletRequest) request).getServletPath().contains("/form") ||
        ((HttpServletRequest) request).getServletPath().contains("/unidade") ||
        ((HttpServletRequest) request).getServletPath().contains("/modelo") ||
        ((HttpServletRequest) request).getServletPath()
          .contains("/swagger-ui") ||
        ((HttpServletRequest) request).getServletPath().contains("/api-docs") ||
        ((HttpServletRequest) request).getServletPath()
          .contains("/usuarios/logout")
      ) {
        chain.doFilter(request, response);
        return;
      }
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      try {
        RequestWrapper requestWrapper = new RequestWrapper(httpRequest);
        REQUEST_BODY_ATTRIBUTE = requestWrapper.body;
        requestWrapper.setAttribute(
          "REQUEST_BODY_ATTRIBUTE",
          REQUEST_BODY_ATTRIBUTE
        );
        chain.doFilter(requestWrapper, response);

        return;
      } catch (Exception e) {
        ((HttpServletResponse) response).setStatus(
            HttpServletResponse.SC_BAD_REQUEST
          );
        ErroResponse errorResponse = new ErroResponse(
          HttpStatus.BAD_REQUEST.value(),
          "Erro na requisição"
        );
        ((HttpServletResponse) response).setStatus(
            HttpStatus.BAD_REQUEST.value()
          );
        ((HttpServletResponse) response).setContentType("application/json");
        ((HttpServletResponse) response).getWriter()
          .write(convertObjectToJson(errorResponse));
        return;
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  private static class RequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
      super(request);
      body = getRequestBody(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
      final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
        body.getBytes()
      );

      return new ServletInputStream() {
        public int read() throws IOException {
          return byteArrayInputStream.read();
        }

        @Override
        public boolean isFinished() {
          throw new UnsupportedOperationException(
            "Unimplemented method 'isFinished'"
          );
        }

        @Override
        public boolean isReady() {
          throw new UnsupportedOperationException(
            "Unimplemented method 'isReady'"
          );
        }

        @Override
        public void setReadListener(ReadListener listener) {
          throw new UnsupportedOperationException(
            "Unimplemented method 'setReadListener'"
          );
        }
      };
    }

    @Override
    public BufferedReader getReader() throws IOException {
      return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private String getRequestBody(HttpServletRequest request)
      throws IOException {
      StringBuilder requestBody = new StringBuilder();
      try (BufferedReader reader = request.getReader()) {
        String line;
        while ((line = reader.readLine()) != null) {
          requestBody.append(line);
        }
      }
      return requestBody.toString();
    }
  }

  private String convertObjectToJson(Object object)
    throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }
}
