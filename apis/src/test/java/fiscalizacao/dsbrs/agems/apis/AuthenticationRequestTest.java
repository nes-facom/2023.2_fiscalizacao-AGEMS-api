package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationRequestTest {

  @Autowired
  private Validator validador = Validation
    .buildDefaultValidatorFactory()
    .getValidator();

  @Test
  public void testConstrutorNoArgs() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    assertNotNull(authenticationRequest);
    assertNull(authenticationRequest.getEmail());
    assertNull(authenticationRequest.getSenha());
    Set<ConstraintViolation<AuthenticationRequest>> violacoes = new HashSet<>();

    violacoes.addAll(validador.validate(authenticationRequest));
    assertTrue(violacoes.size() == 0);
  }

  @Test
  public void testConstrutorAllArgs() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest(
      "juliaacorazza@gmail.com",
      "fiscaliza-agems"
    );
    assertNotNull(authenticationRequest);
    assertNotNull(authenticationRequest.getEmail());
    assertNotNull(authenticationRequest.getSenha());
    assertNotEquals(
      authenticationRequest.getEmail(),
      authenticationRequest.getSenha()
    );
    Set<ConstraintViolation<AuthenticationRequest>> violacoes = new HashSet<>();

    violacoes.addAll(validador.validate(authenticationRequest));
    assertFalse(violacoes.size() == 0);
    for (int i = 0; i <= violacoes.size(); i++) {
      ConstraintViolation<AuthenticationRequest> violation = violacoes
        .iterator()
        .next();
      assertNotNull(violation.getMessage());
      assertEquals("Senha de formato inv\u00E1lido", violation.getMessage());
    }
    assertEquals("juliaacorazza@gmail.com", authenticationRequest.getEmail());
    assertEquals("fiscaliza-agems", authenticationRequest.getSenha());
  }

  @Test
  public void testSetEGetEmail() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setEmail("juliaacorazza@gmail.com");
    assertNotNull(authenticationRequest.getEmail());
    assertEquals("juliaacorazza@gmail.com", authenticationRequest.getEmail());
    assertNull(authenticationRequest.getSenha());
    assertNotEquals("jalves@gmail.com", authenticationRequest.getEmail());
    Set<ConstraintViolation<AuthenticationRequest>> violacoes = new HashSet<>();

    violacoes.addAll(validador.validate(authenticationRequest));
    assertTrue(violacoes.size() == 0);
  }

  @Test
  public void testSetEGetSenha() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setSenha("fiscaliza-agems1#");
    assertNotNull(authenticationRequest.getSenha());
    assertNull(authenticationRequest.getEmail());
    assertEquals("fiscaliza-agems1#", authenticationRequest.getSenha());
    assertNotEquals("jujuba-23", authenticationRequest.getSenha());
  }

  @Test
  public void testBuilderNoArgs() {
    AuthenticationRequest authenticationRequest = AuthenticationRequest
      .builder()
      .build();
    assertNotNull(authenticationRequest);
    assertNull(authenticationRequest.getEmail());
    assertNull(authenticationRequest.getSenha());
  }

  @Test
  public void testBuilderAllArgs() {
    AuthenticationRequest authenticationRequest = AuthenticationRequest
      .builder()
      .email("juliaacorazza@gmail.com")
      .senha("fiscaliza-agems")
      .build();
    assertNotNull(authenticationRequest);
    assertNotNull(authenticationRequest.getEmail());
    assertNotNull(authenticationRequest.getSenha());
    assertEquals("juliaacorazza@gmail.com", authenticationRequest.getEmail());
    assertEquals("fiscaliza-agems", authenticationRequest.getSenha());
    assertNotEquals("jalves@gmail.com", authenticationRequest.getEmail());
    assertNotEquals("jujuba-23", authenticationRequest.getSenha());
  }

  @Test
  public void testBuilderSenhaArgs() {
    AuthenticationRequest authenticationRequest = AuthenticationRequest
      .builder()
      .senha("fiscaliza-agems1#")
      .build();
    assertNotNull(authenticationRequest);
    assertNull(authenticationRequest.getEmail());
    assertNotNull(authenticationRequest.getSenha());
    assertEquals("fiscaliza-agems1#", authenticationRequest.getSenha());
    assertNotEquals("jujuba-23", authenticationRequest.getSenha());
  }

  @Test
  public void testBuilderEmailArgs() {
    AuthenticationRequest authenticationRequest = AuthenticationRequest
      .builder()
      .email("juliaacorazza@gmail.com")
      .build();
    assertNotNull(authenticationRequest);
    assertNotNull(authenticationRequest.getEmail());
    assertNull(authenticationRequest.getSenha());
    assertNotEquals("jalves@gmail.com", authenticationRequest.getEmail());
    assertEquals("juliaacorazza@gmail.com", authenticationRequest.getEmail());
  }

  @Test
  public void testEquals() {
    AuthenticationRequest request1 = AuthenticationRequest
      .builder()
      .email("zezinho.silva@gmail.com")
      .senha("zezinho123%")
      .build();

    AuthenticationRequest request2 = AuthenticationRequest
      .builder()
      .email("zezinho.silva@gmail.com")
      .senha("zezinho123%")
      .build();

    AuthenticationRequest request3 = AuthenticationRequest
      .builder()
      .email("joao.silva@gmail.com")
      .senha("joao123%")
      .build();

    assertEquals(request1, request2);

    assertNotEquals(request1, request3);
  }

  @Test
  public void testToString() {
    AuthenticationRequest request = AuthenticationRequest
      .builder()
      .email("zezinho.silva@gmail.com")
      .senha("zezinho123%")
      .build();

    String expectedToString =
      "AuthenticationRequest(email=zezinho.silva@gmail.com, senha=zezinho123%)";
    assertEquals(expectedToString, request.toString());
  }

  @Test
  public void testHashCode() {
    AuthenticationRequest request1 = AuthenticationRequest
      .builder()
      .email("zezinho.silva@gmail.com")
      .senha("zezinho123%")
      .build();

    AuthenticationRequest request2 = AuthenticationRequest
      .builder()
      .email("zezinho.silva@gmail.com")
      .senha("zezinho123%")
      .build();

    assertEquals(request1.hashCode(), request2.hashCode());
  }

  @Test
  public void testBuilderToString() {
    AuthenticationRequest.AuthenticationRequestBuilder builder = AuthenticationRequest
      .builder()
      .email("zezinho.silva@gmail.com")
      .senha("zezinho123%");

    String expectedToString =
      "AuthenticationRequest.AuthenticationRequestBuilder(email=zezinho.silva@gmail.com, senha=zezinho123%)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }
}
