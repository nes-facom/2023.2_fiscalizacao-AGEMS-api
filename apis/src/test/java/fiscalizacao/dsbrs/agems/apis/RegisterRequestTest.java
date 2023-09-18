package fiscalizacao.dsbrs.agems.apis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;

public class RegisterRequestTest {

  @Test
  public void testConstructorAllArgs() {
    RegisterRequest request = new RegisterRequest(
      "Exemplo Exemplinho Exemplificado",
      "test@example.com",
      "examplo1234#",
      "Analista de Regula\u00E7\u00E3o"
    );
    Assertions.assertEquals("test@example.com", request.getEmail());
    Assertions.assertEquals(
      "Exemplo Exemplinho Exemplificado",
      request.getNome()
    );
    Assertions.assertEquals("examplo1234#", request.getSenha());
    Assertions.assertEquals(
      "Analista de Regula\u00E7\u00E3o",
      request.getCargo()
    );
  }

  @Test
  public void testToString() {
    RegisterRequest request = new RegisterRequest(
      "Exemplo Exemplinho Exemplificado",
      "test@example.com",
      "examplo1234#",
      "Analista de Regula\u00E7\u00E3o"
    );

    String toStringResult = request.toString();

    String expectedToString =
      "RegisterRequest(nome=Exemplo Exemplinho Exemplificado, email=test@example.com, senha=examplo1234#, cargo=Analista de Regula\u00E7\u00E3o)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }

  @Test
  public void testBuilderToString() {
    RegisterRequest.RegisterRequestBuilder builder = RegisterRequest
      .builder()
      .nome("Exemplo Exemplinho Exemplificado")
      .email("test@example.com")
      .senha("examplo1234#")
      .cargo("Analista de Regula\u00E7\u00E3o");

    String toStringResult = builder.toString();

    String expectedToString =
      "RegisterRequest.RegisterRequestBuilder(nome=Exemplo Exemplinho Exemplificado, email=test@example.com, senha=examplo1234#, cargo=Analista de Regula\u00E7\u00E3o)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }

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

  @Test
  public void testValidCargo() {
    RegisterRequest request = new RegisterRequest();
    String validCargo = "Analista de Regulação";
    String normalizedCargo = request.getCARGOS(validCargo);
    Assertions.assertEquals(validCargo, normalizedCargo);
  }

  @Test
  public void testInvalidCargo() {
    RegisterRequest request = new RegisterRequest();
    String invalidCargo = "Invalid Cargo";
    String normalizedCargo = request.getCARGOS(invalidCargo);
    Assertions.assertNull(normalizedCargo);
  }
}
