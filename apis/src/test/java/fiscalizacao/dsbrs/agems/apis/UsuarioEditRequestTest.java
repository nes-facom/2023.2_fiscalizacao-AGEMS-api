package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.UsuarioEditRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioEditRequestTest {

  @Test
  public void testInvalidPasswordFormat() {
    UsuarioEditRequest request = UsuarioEditRequest
      .builder()
      .nomeNovo("Zezinho da Silva Sauro")
      .senha("invalidpassword")
      .senhaNova("zezinho123$")
      .build();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<UsuarioEditRequest>> violations = validator.validate(
      request
    );
    Assertions.assertTrue(!violations.isEmpty());
  }

  @Test
  public void testValidPasswordFormat() {
    UsuarioEditRequest request = UsuarioEditRequest
      .builder()
      .nomeNovo("Zezinho da Silva Sauro")
      .senha("zezinho123%")
      .senhaNova("zezinho123$")
      .build();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<UsuarioEditRequest>> violations = validator.validate(
      request
    );

    Assertions.assertTrue(violations.isEmpty());
  }
}
