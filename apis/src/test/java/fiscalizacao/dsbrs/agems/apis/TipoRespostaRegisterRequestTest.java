package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TipoRespostaRegisterRequestTest {

  @Test
  public void testConstructorAndGetters() {
    String resposta = "Sim";
    TipoRespostaRegisterRequest request = new TipoRespostaRegisterRequest(
      resposta
    );

    Assertions.assertEquals(resposta, request.getResposta());
  }

  @Test
  public void testSetters() {
    String resposta = "Não";
    TipoRespostaRegisterRequest request = new TipoRespostaRegisterRequest();
    request.setResposta(resposta);

    Assertions.assertEquals(resposta, request.getResposta());
  }

  @Test
  public void testBuilder() {
    String resposta = "Talvez";
    TipoRespostaRegisterRequest request = TipoRespostaRegisterRequest
      .builder()
      .resposta(resposta)
      .build();

    Assertions.assertEquals(resposta, request.getResposta());
  }

  @Test
  public void testBuilderToString() {
    TipoRespostaRegisterRequest.TipoRespostaRegisterRequestBuilder builder = TipoRespostaRegisterRequest.builder();

    String expectedToString =
      "TipoRespostaRegisterRequest.TipoRespostaRegisterRequestBuilder(resposta=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testToString() {
    TipoRespostaRegisterRequest tipoRespostaEditRequest = TipoRespostaRegisterRequest
      .builder()
      .build();
    String expectedToString = "TipoRespostaRegisterRequest(resposta=null)";
    String actualToString = tipoRespostaEditRequest.toString();
    assertEquals(expectedToString, actualToString);
  }
}
