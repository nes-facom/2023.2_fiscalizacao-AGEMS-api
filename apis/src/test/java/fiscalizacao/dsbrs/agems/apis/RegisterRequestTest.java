package fiscalizacao.dsbrs.agems.apis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;

public class RegisterRequestTest {
  @Test
  public void testValidEmail() {
    RegisterRequest request = RegisterRequest
      .builder()
      .email("test@example.com").build();
    Assertions.assertEquals("test@example.com", request.getEmail());
  }

  @Test
  public void testInvalidEmail() {
    RegisterRequest request = new RegisterRequest();
    request.setEmail("invalid_email");
    Assertions.assertNull(request.getEmail());
  }
}
