package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.RespostaEditRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RespostaEditRequestTest {

  @Test
  public void testRespostaEditRequest() {
    int questao = 1;
    String resposta = "Resposta";
    String obs = "Observação";

    RespostaEditRequest request = new RespostaEditRequest(
      questao,
      resposta,
      obs
    );

    Assertions.assertEquals(questao, request.getQuestao());
    Assertions.assertEquals(resposta, request.getResposta());
    Assertions.assertEquals(obs, request.getObs());
  }

  @Test
  public void testRespostaEditRequestWithoutObservacao() {
    int questao = 1;
    String resposta = "Resposta";

    RespostaEditRequest request = new RespostaEditRequest(
      questao,
      resposta,
      null
    );

    Assertions.assertEquals(questao, request.getQuestao());
    Assertions.assertEquals(resposta, request.getResposta());
    Assertions.assertNull(request.getObs());
  }

  @Test
  public void testRespostaEditRequestWithDefaultConstructor() {
    RespostaEditRequest request = new RespostaEditRequest();

    Assertions.assertEquals(0, request.getQuestao());
    Assertions.assertNull(request.getResposta());
    Assertions.assertNull(request.getObs());
  }

  @Test
  public void testRespostaEditRequestEqualsAndHashCode() {
    RespostaEditRequest request1 = new RespostaEditRequest(
      1,
      "Resposta 1",
      "Observação 1"
    );
    RespostaEditRequest request2 = new RespostaEditRequest(
      1,
      "Resposta 1",
      "Observação 1"
    );
    RespostaEditRequest request3 = new RespostaEditRequest(
      2,
      "Resposta 2",
      "Observação 2"
    );

    Assertions.assertEquals(request1, request2);
    Assertions.assertNotEquals(request1, request3);

    Assertions.assertEquals(request1.hashCode(), request2.hashCode());
    Assertions.assertNotEquals(request1.hashCode(), request3.hashCode());
  }

  @Test
  public void testRespostaEditRequestToString() {
    int questao = 1;
    String resposta = "Resposta";
    String obs = "Observação";

    RespostaEditRequest request = new RespostaEditRequest(
      questao,
      resposta,
      obs
    );

    String expectedString =
      "RespostaEditRequest(questao=1, resposta=Resposta, obs=Observação)";
    Assertions.assertEquals(expectedString, request.toString());
  }

  @Test
  public void testRespostaBuilder() {
    int questao = 1;
    String resposta = "Resposta";
    String obs = "Observação";

    RespostaEditRequest request = RespostaEditRequest
      .builder()
      .questao(questao)
      .resposta(resposta)
      .obs(obs)
      .build();

    String expectedString =
      "RespostaEditRequest(questao=1, resposta=Resposta, obs=Observação)";
    Assertions.assertEquals(expectedString, request.toString());
  }

  @Test
  public void testRespostaBuilderToString() {
    int questao = 1;
    String resposta = "Resposta";
    String obs = "Observação";

    RespostaEditRequest.RespostaEditRequestBuilder builder = RespostaEditRequest
      .builder()
      .questao(questao)
      .resposta(resposta)
      .obs(obs);

    String expectedString =
      "RespostaEditRequest.RespostaEditRequestBuilder(questao=1, resposta=Resposta, obs=Observação)";
    Assertions.assertEquals(expectedString, builder.toString());
  }

  @Test
  public void testRespostaSetters() {
    int questao = 1;
    String resposta = "Resposta";
    String obs = "Observação";

    RespostaEditRequest request = RespostaEditRequest.builder().build();
    request.setQuestao(questao);
    request.setResposta(resposta);
    request.setObs(obs);
    Assertions.assertEquals("Resposta", request.getResposta());
    Assertions.assertEquals("Observação", request.getObs());
    Assertions.assertEquals(questao, request.getQuestao());
  }
}
