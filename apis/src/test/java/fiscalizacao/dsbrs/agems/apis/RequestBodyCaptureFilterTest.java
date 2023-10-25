package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import fiscalizacao.dsbrs.agems.apis.config.RequestBodyCaptureFilter;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

class RequestBodyCaptureFilterTest {

  private RequestBodyCaptureFilter filter;

  @Mock
  private HttpServletRequest request;

  @Mock
  private ServletRequest servletRequest;

  @Mock
  private ServletResponse servletResponse;

  @Mock
  private FilterChain filterChain;
  
  private AutoCloseable mocksOpener;

  @BeforeEach
  void setUp() {
    mocksOpener = MockitoAnnotations.openMocks(this);
    filter = new RequestBodyCaptureFilter();
    servletResponse = mock(HttpServletResponse.class);
    request = mock(HttpServletRequest.class);
    servletRequest = mock(HttpServletRequest.class);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocksOpener.close();
  }

  @Test
  void testDoFilterWithException() throws IOException, ServletException {
    when(request.getServletPath()).thenReturn("/form");
    when(request.getMethod()).thenReturn("POST");
    when(request.getReader()).thenThrow(new IOException());

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter writer = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(writer);

    filter.doFilter(request, servletResponse, filterChain);

    verify(response.getWriter(), times(0)).write(any(String.class));
  }

  @Test
  void testRequestWrapperGetInputStream() throws IOException {
    HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(
      request
    );
    BufferedReader reader = mock(BufferedReader.class);
    when(request.getReader()).thenReturn(reader);
    when(reader.readLine()).thenReturn("request", " body", null);
    ServletInputStream inputStream = mock(ServletInputStream.class);
    when(requestWrapper.getInputStream()).thenReturn(inputStream);
    when(inputStream.read())
      .thenReturn(
        (int) 'r',
        (int) 'e',
        (int) 'q',
        (int) 'u',
        (int) 'e',
        (int) 's',
        (int) 't',
        (int) ' ',
        (int) 'b',
        (int) 'o',
        (int) 'd',
        (int) 'y',
        -1
      );

    int bytesRead = inputStream.read();
    StringBuilder requestBody = new StringBuilder();
    while (bytesRead != -1) {
      requestBody.append((char) bytesRead);
      bytesRead = inputStream.read();
    }

    assertEquals("request body", requestBody.toString());
  }

  @Test
  void testRequestWrapperGetReader() throws IOException {
    HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(
      request
    );
    when(request.getReader())
      .thenReturn(new BufferedReader(new StringReader("request body")));

    BufferedReader reader = requestWrapper.getReader();

    String line = reader.readLine();
    StringBuilder requestBody = new StringBuilder();
    while (line != null) {
      requestBody.append(line);
      line = reader.readLine();
    }

    assert requestBody.toString().equals("request body");
  }

  @Test
  void testConvertObjectToJson()
    throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ErroResponse errorResponse = new ErroResponse(
      HttpStatus.BAD_REQUEST.value(),
      "Erro na requisição"
    );

    RequestBodyCaptureFilter filter = new RequestBodyCaptureFilter();
    Method convertObjectToJsonMethod =
      RequestBodyCaptureFilter.class.getDeclaredMethod(
          "convertObjectToJson",
          Object.class
        );
    convertObjectToJsonMethod.setAccessible(true);
    String json = (String) convertObjectToJsonMethod.invoke(
      filter,
      errorResponse
    );

    assertEquals("{\"status\":400,\"error\":\"Erro na requisição\"}", json);
  }
  @Test
    public void testDoFilter_RequestBodyCaptured() throws ServletException, IOException {

        String requestBody = "Sample request body";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(requestBody.getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();


        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

      
        String capturedRequestBody = (String) request.getAttribute("REQUEST_BODY_ATTRIBUTE");
        assertEquals(requestBody, capturedRequestBody);
    }

   
}
