package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fiscalizacao.dsbrs.agems.apis.responses.FormularioResumoResponse;
import org.junit.Test;

public class FormularioResumoResponseTest {

  @Test
  public void testAllArgsConstructor() {
    FormularioResumoResponse formularioResumoResponse = new FormularioResumoResponse(
      1
    );
    assertNotNull(formularioResumoResponse);
    assertEquals(1, formularioResumoResponse.getId());
    assertNotEquals(0, formularioResumoResponse.getId());
  }

  @Test
  public void testBuilderNoArgs() {
    FormularioResumoResponse formularioResumoResponse = FormularioResumoResponse
      .builder()
      .build();
    assertNotNull(formularioResumoResponse);
    assertNotEquals(1, formularioResumoResponse.getId());
    assertEquals(0, formularioResumoResponse.getId());
  }

  @Test
  public void testBuilderAllArgs() {
    FormularioResumoResponse formularioResumoResponse = FormularioResumoResponse
      .builder()
      .id(1)
      .build();
    assertNotNull(formularioResumoResponse);
    assertNotEquals(0, formularioResumoResponse.getId());
    assertEquals(1, formularioResumoResponse.getId());
  }

  @Test
  public void testGetESetId(){
    FormularioResumoResponse formularioResumoResponse = new FormularioResumoResponse();
    assertNotEquals(1, formularioResumoResponse.getId());
    assertEquals(0, formularioResumoResponse.getId());
    formularioResumoResponse.setId(50);
    assertNotEquals(0, formularioResumoResponse.getId());
    assertEquals(50, formularioResumoResponse.getId());
  }

  @Test
  public void testConstructorNoArgs(){
    FormularioResumoResponse formularioResumoResponse = new FormularioResumoResponse();
    assertNotNull(formularioResumoResponse);
    assertNotEquals(1, formularioResumoResponse.getId());
    assertEquals(0, formularioResumoResponse.getId());
  }
}
