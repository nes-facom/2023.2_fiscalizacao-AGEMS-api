package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.UnidadeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnidadeRequestTest {

  @Test
  public void testUnidadeRequest() {
    UnidadeRequest unidadeRequest = UnidadeRequest
      .builder()
      .idUnidade("Unidade 1 de Tratamento de Esgoto de Dourados")
      .endereco("Rua das Neves, 114")
      .tipo("Tratamento de Esgoto")
      .build();

    Assertions.assertEquals(
      "Unidade 1 de Tratamento de Esgoto de Dourados",
      unidadeRequest.getIdUnidade()
    );
    Assertions.assertEquals("Rua das Neves, 114", unidadeRequest.getEndereco());
    Assertions.assertEquals("Tratamento de Esgoto", unidadeRequest.getTipo());
  }

  @Test
  public void testUnidadeRequestBuilderToString() {
    UnidadeRequest.UnidadeRequestBuilder builder = UnidadeRequest
      .builder()
      .idUnidade("Unidade 1 de Tratamento de Esgoto de Dourados")
      .endereco("Rua das Neves, 114")
      .tipo("Tratamento de Esgoto");

    String expectedToString =
      "UnidadeRequest.UnidadeRequestBuilder(idUnidade=Unidade 1 de Tratamento de Esgoto de Dourados, endereco=Rua das Neves, 114, tipo=Tratamento de Esgoto)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testUnidadeRequestToString() {
    UnidadeRequest request = UnidadeRequest
      .builder()
      .idUnidade("Unidade 1 de Tratamento de Esgoto de Dourados")
      .endereco("Rua das Neves, 114")
      .tipo("Tratamento de Esgoto").build();

    String expectedToString =
      "UnidadeRequest(idUnidade=Unidade 1 de Tratamento de Esgoto de Dourados, endereco=Rua das Neves, 114, tipo=Tratamento de Esgoto)";
    String actualToString = request.toString();

    assertEquals(expectedToString, actualToString);
  }
}
