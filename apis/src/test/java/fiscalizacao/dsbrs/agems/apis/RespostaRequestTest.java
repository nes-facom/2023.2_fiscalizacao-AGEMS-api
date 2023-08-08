package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RespostaRequestTest {

  @Test
  public void testRespostaRequestConstructor() {
    RespostaRequest respostaRequest = new RespostaRequest(
      1,
      "Sim",
      "Em conformidade."
    );
    Assertions.assertEquals(1, respostaRequest.getQuestao());
    Assertions.assertEquals("Sim", respostaRequest.getResposta());
    Assertions.assertEquals("Em conformidade.", respostaRequest.getObs());
  }

  @Test
  public void testRespostaRequestBuilder() {
    RespostaRequest respostaRequest = RespostaRequest
      .builder()
      .questao(1)
      .resposta("Sim")
      .obs("Em conformidade")
      .build();
    Assertions.assertEquals(1, respostaRequest.getQuestao());
    Assertions.assertEquals("Sim", respostaRequest.getResposta());
    Assertions.assertEquals("Em conformidade", respostaRequest.getObs());
  }

  @Test
  public void testRespostaRequestBuilderToString() {
    RespostaRequest.RespostaRequestBuilder builder = RespostaRequest
      .builder()
      .questao(1)
      .resposta("Sim")
      .obs("Em conformidade");
    String expectedString =
      "RespostaRequest.RespostaRequestBuilder(questao=1, resposta=Sim, obs=Em conformidade)";
    Assertions.assertEquals(expectedString, builder.toString());
  }
  @Test
  public void testRespostaRequestToString() {
    RespostaRequest request = RespostaRequest
      .builder()
      .questao(1)
      .resposta("Sim")
      .obs("Em conformidade").build();
    String expectedString =
      "RespostaRequest(questao=1, resposta=Sim, obs=Em conformidade)";
    Assertions.assertEquals(expectedString, request.toString());
  }

  @Test
  public void testRespostaRequestGettersAndSetters() {
    RespostaRequest respostaRequest = new RespostaRequest();
    respostaRequest.setQuestao(1);
    respostaRequest.setResposta("Sim");
    respostaRequest.setObs("Em conformidade.");

    Assertions.assertEquals(1, respostaRequest.getQuestao());
    Assertions.assertEquals("Sim", respostaRequest.getResposta());
    Assertions.assertEquals("Em conformidade.", respostaRequest.getObs());
  }
}
