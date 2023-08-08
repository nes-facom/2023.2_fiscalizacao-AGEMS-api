package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaRegisterRequest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModeloRegisterRequestTest {

  @Test
  public void testModeloRegisterRequest() {
    String modelo = "Modelo 01";
    List<TipoRespostaRegisterRequest> tipoRespostas = Arrays.asList(
      new TipoRespostaRegisterRequest("Resposta 1"),
      new TipoRespostaRegisterRequest("Resposta 2"),
      new TipoRespostaRegisterRequest("Resposta 3")
    );

    List<TipoRespostaRegisterRequest> tipoRespostas2 = Arrays.asList(
      new TipoRespostaRegisterRequest("Resposta 1"),
      new TipoRespostaRegisterRequest("Resposta 2"),
      new TipoRespostaRegisterRequest("Resposta 3")
    );
    List<TipoRespostaRegisterRequest> tipoRespostas3 = Arrays.asList(
      new TipoRespostaRegisterRequest("Resposta 1"),
      new TipoRespostaRegisterRequest("Resposta 2"),
      new TipoRespostaRegisterRequest("Resposta 3")
    );

    List<QuestaoRegisterRequest> questoes = Arrays.asList(
      new QuestaoRegisterRequest(
        "Questão 1",
        true,
        "Portaria 1",
        tipoRespostas
      ),
      new QuestaoRegisterRequest(
        "Questão 2",
        true,
        "Portaria 2",
        tipoRespostas2
      ),
      new QuestaoRegisterRequest(
        "Questão 3",
        true,
        "Portaria 3",
        tipoRespostas3
      )
    );

    ModeloRegisterRequest modeloRegisterRequest = new ModeloRegisterRequest(
      modelo,
      questoes
    );

    Assertions.assertEquals(modelo, modeloRegisterRequest.getModelo());
    Assertions.assertEquals(questoes, modeloRegisterRequest.getQuestoes());
  }

  @Test
  public void testModeloRegisterRequestEmptyQuestoes() {
    String modelo = "Modelo 02";
    List<QuestaoRegisterRequest> questoes = Arrays.asList();

    ModeloRegisterRequest modeloRegisterRequest = new ModeloRegisterRequest(
      modelo,
      questoes
    );

    Assertions.assertEquals(modelo, modeloRegisterRequest.getModelo());
    Assertions.assertEquals(questoes, modeloRegisterRequest.getQuestoes());
  }

  @Test
  public void testModeloRegisterRequestNullQuestoes() {
    String modelo = "Modelo 03";
    List<QuestaoRegisterRequest> questoes = null;

    ModeloRegisterRequest modeloRegisterRequest = new ModeloRegisterRequest(
      modelo,
      questoes
    );

    Assertions.assertEquals(modelo, modeloRegisterRequest.getModelo());
    Assertions.assertNull(modeloRegisterRequest.getQuestoes());
  }

  @Test
  public void testBuilder() {
    String modelo = "Modelo 01";
    List<TipoRespostaRegisterRequest> tipoRespostas = Arrays.asList(
      new TipoRespostaRegisterRequest("Resposta 1"),
      new TipoRespostaRegisterRequest("Resposta 2"),
      new TipoRespostaRegisterRequest("Resposta 3")
    );

    List<TipoRespostaRegisterRequest> tipoRespostas2 = Arrays.asList(
      new TipoRespostaRegisterRequest("Resposta 1"),
      new TipoRespostaRegisterRequest("Resposta 2"),
      new TipoRespostaRegisterRequest("Resposta 3")
    );
    List<TipoRespostaRegisterRequest> tipoRespostas3 = Arrays.asList(
      new TipoRespostaRegisterRequest("Resposta 1"),
      new TipoRespostaRegisterRequest("Resposta 2"),
      new TipoRespostaRegisterRequest("Resposta 3")
    );

    List<QuestaoRegisterRequest> questoes = Arrays.asList(
      new QuestaoRegisterRequest(
        "Questão 1",
        true,
        "Portaria 1",
        tipoRespostas
      ),
      new QuestaoRegisterRequest(
        "Questão 2",
        true,
        "Portaria 2",
        tipoRespostas2
      ),
      new QuestaoRegisterRequest(
        "Questão 3",
        true,
        "Portaria 3",
        tipoRespostas3
      )
    );

    ModeloRegisterRequest modeloRegisterRequest = ModeloRegisterRequest
      .builder()
      .modelo(modelo)
      .questoes(questoes)
      .build();

    Assertions.assertEquals(modelo, modeloRegisterRequest.getModelo());
    Assertions.assertEquals(questoes, modeloRegisterRequest.getQuestoes());
  }

  @Test
  public void testToString() {
    ModeloRegisterRequest modeloRegisterRequest = ModeloRegisterRequest
      .builder()
      .build();
    String expectedString = "ModeloRegisterRequest(modelo=null, questoes=null)";
    Assertions.assertEquals(expectedString, modeloRegisterRequest.toString());
  }

  @Test
  public void testBuilderToString() {
    ModeloRegisterRequest.ModeloRegisterRequestBuilder modeloRegisterRequestBuilder = ModeloRegisterRequest.builder();
    String expectedString =
      "ModeloRegisterRequest.ModeloRegisterRequestBuilder(modelo=null, questoes=null)";
    Assertions.assertEquals(
      expectedString,
      modeloRegisterRequestBuilder.toString()
    );
  }
}
