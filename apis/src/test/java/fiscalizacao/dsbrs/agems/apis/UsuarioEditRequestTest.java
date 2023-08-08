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

  @Test
  public void testConstructor() {
    String nomeNovo = "Zezinho da Silva Sauro";
    String senha = "zezinho123%";
    String senhaNova = "zezinho123$";

    UsuarioEditRequest request = new UsuarioEditRequest(
      nomeNovo,
      senha,
      senhaNova
    );

    Assertions.assertEquals(nomeNovo, request.getNomeNovo());
    Assertions.assertEquals(senha, request.getSenha());
    Assertions.assertEquals(senhaNova, request.getSenhaNova());
  }

  @Test
  public void testBuilder() {
    String nomeNovo = "Zezinho da Silva Sauro";
    String senha = "zezinho123%";
    String senhaNova = "zezinho123$";

    UsuarioEditRequest request = UsuarioEditRequest
      .builder()
      .nomeNovo(nomeNovo)
      .senha(senha)
      .senhaNova(senhaNova)
      .build();

    Assertions.assertEquals(nomeNovo, request.getNomeNovo());
    Assertions.assertEquals(senha, request.getSenha());
    Assertions.assertEquals(senhaNova, request.getSenhaNova());
  }

  @Test
  public void testToString() {
    UsuarioEditRequest request = new UsuarioEditRequest();

    String expectedToString =
      "UsuarioEditRequest(nomeNovo=null, senha=null, senhaNova=null)";
    String actualToString = request.toString();

    Assertions.assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testBuilderToString() {
    String nomeNovo = "Zezinho da Silva Sauro";
    String senha = "zezinho123%";
    String senhaNova = "zezinho123$";

    UsuarioEditRequest.UsuarioEditRequestBuilder builder = UsuarioEditRequest
      .builder()
      .nomeNovo(nomeNovo)
      .senha(senha)
      .senhaNova(senhaNova);

    String expectedBuilderToString =
      "UsuarioEditRequest.UsuarioEditRequestBuilder(nomeNovo=Zezinho da Silva Sauro, senha=zezinho123%, senhaNova=zezinho123$)";
    String actualBuilderToString = builder.toString();

    Assertions.assertEquals(expectedBuilderToString, actualBuilderToString);
  }

  @Test
  public void testSettersEGetters() {
    String nomeNovo = "Zezinho da Silva Sauro";
    String senha = "zezinho123%";
    String senhaNova = "zezinho123$";

    UsuarioEditRequest request = UsuarioEditRequest.builder().build();
    request.setNomeNovo(nomeNovo);
    request.setSenha(senha);
    request.setSenhaNova(senhaNova);
    Assertions.assertTrue(request.getNomeNovo().equals(nomeNovo));
    Assertions.assertTrue(request.getSenha().equals(senha));
    Assertions.assertTrue(request.getSenhaNova().equals(senhaNova));
  }
}
