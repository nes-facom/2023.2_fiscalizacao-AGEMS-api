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
}
