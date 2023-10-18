package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.ModeloRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModeloRequestTest {

  @Test
  public void testGettersAndSetters() {
    int id = 1;
    String modeloNome = "Modelo 01";

    ModeloRequest modeloRequest = new ModeloRequest();
    modeloRequest.setId(id);
    modeloRequest.setNome(modeloNome);

    Assertions.assertEquals(id, modeloRequest.getId());
    Assertions.assertEquals(modeloNome, modeloRequest.getModeloNome());
  }

  @Test
  public void testEmptyConstructor() {
    ModeloRequest modeloRequest = new ModeloRequest();
    Assertions.assertEquals(0, modeloRequest.getId());
    Assertions.assertNull(modeloRequest.getModeloNome());
  }

  @Test
  public void testRequiredFields() {
    int id = 1;
    String modeloNome = "Modelo 01";

    ModeloRequest modeloRequest = ModeloRequest
      .builder()
      .id(id)
      .modeloNome(modeloNome)
      .build();
    String expectedToString =
      "ModeloRequest(id=1, modeloNome=Modelo 01)";
    String actualToString = modeloRequest.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testBuilderToString() {
    int id = 1;
    String modeloNome = "Modelo 01";

    ModeloRequest.ModeloRequestBuilder modeloRequest = ModeloRequest
      .builder()
      .id(id)
      .modeloNome(modeloNome);
    String expectedToString =
      "ModeloRequest.ModeloRequestBuilder(id=1, modeloNome=Modelo 01)";
    String actualToString = modeloRequest.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testExampleValues() {
    int id = 1;
    String modeloNome = "Modelo 01";

    ModeloRequest modeloRequest = new ModeloRequest(id, modeloNome);

    Assertions.assertEquals(id, modeloRequest.getId());
    Assertions.assertEquals(modeloNome, modeloRequest.getModeloNome());
  }
}
