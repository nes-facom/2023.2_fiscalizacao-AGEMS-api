package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TipoRespostaRegisterRequestTest {

  @Test
  public void testConstructorAndGetters() {
    String resposta = "Sim";
    AlternativaRespostaRegisterRequest request = new AlternativaRespostaRegisterRequest(
      resposta
    );

    Assertions.assertEquals(resposta, request.getResposta());
  }

  @Test
  public void testSetters() {
    String resposta = "Não";
    AlternativaRespostaRegisterRequest request = new AlternativaRespostaRegisterRequest();
    request.setResposta(resposta);

    Assertions.assertEquals(resposta, request.getResposta());
  }

  @Test
  public void testBuilder() {
    String resposta = "Talvez";
    AlternativaRespostaRegisterRequest request = AlternativaRespostaRegisterRequest
      .builder()
      .resposta(resposta)
      .build();

    Assertions.assertEquals(resposta, request.getResposta());
  }

  @Test
  public void testBuilderToString() {
    AlternativaRespostaRegisterRequest.TipoRespostaRegisterRequestBuilder builder = AlternativaRespostaRegisterRequest.builder();

    String expectedToString =
      "TipoRespostaRegisterRequest.TipoRespostaRegisterRequestBuilder(resposta=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testToString() {
    AlternativaRespostaRegisterRequest tipoRespostaEditRequest = AlternativaRespostaRegisterRequest
      .builder()
      .build();
    String expectedToString = "TipoRespostaRegisterRequest(resposta=null)";
    String actualToString = tipoRespostaEditRequest.toString();
    assertEquals(expectedToString, actualToString);
  }
}
