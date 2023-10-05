package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaEditRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModeloEditRequestTest {

  @Test
  public void testModeloEditRequest() {
    int id = 1;
    String modeloNome = "Modelo 01";
    List<AlternativaRespostaEditRequest> tipoRespostas = Arrays.asList(
      new AlternativaRespostaEditRequest("edit", 1, "Tipo 1"),
      new AlternativaRespostaEditRequest("edit", 2, "Tipo 2"),
      new AlternativaRespostaEditRequest("edit", 3, "Tipo 3")
    );

    List<AlternativaRespostaEditRequest> tipoRespostas2 = Arrays.asList(
      new AlternativaRespostaEditRequest("edit", 4, "Tipo 1"),
      new AlternativaRespostaEditRequest("edit", 5, "Tipo 2"),
      new AlternativaRespostaEditRequest("edit", 6, "Tipo 3")
    );
    List<AlternativaRespostaEditRequest> tipoRespostas3 = Arrays.asList(
      new AlternativaRespostaEditRequest("edit", 7, "Tipo 1"),
      new AlternativaRespostaEditRequest("edit", 8, "Tipo 2"),
      new AlternativaRespostaEditRequest("edit", 9, "Tipo 3")
    );
    List<QuestaoEditRequest> questoes = Arrays.asList(
      new QuestaoEditRequest(
        "edit",
        1,
        "Questão 1",
        true,
        "Portaria 1",
        tipoRespostas
      ),
      new QuestaoEditRequest(
        "edit",
        1,
        "Questão 1",
        true,
        "Portaria 1",
        tipoRespostas2
      ),
      new QuestaoEditRequest(
        "edit",
        1,
        "Questão 1",
        true,
        "Portaria 1",
        tipoRespostas3
      )
    );

    ModeloEditRequest modeloEditRequest = new ModeloEditRequest();
    modeloEditRequest.setId(id);
    modeloEditRequest.setModeloNome(modeloNome);
    modeloEditRequest.setQuestoes(questoes);

    Assertions.assertEquals(id, modeloEditRequest.getId());
    Assertions.assertEquals(modeloNome, modeloEditRequest.getModeloNome());
    Assertions.assertEquals(questoes, modeloEditRequest.getQuestoes());
  }

  @Test
  public void testModeloEditRequestEmptyQuestoes() {
    int id = 2;
    String modeloNome = "Modelo 02";
    List<QuestaoEditRequest> questoes = new ArrayList<>();

    ModeloEditRequest modeloEditRequest = new ModeloEditRequest(
      id,
      modeloNome,
      questoes
    );

    Assertions.assertEquals(id, modeloEditRequest.getId());
    Assertions.assertEquals(modeloNome, modeloEditRequest.getModeloNome());
    Assertions.assertEquals(questoes, modeloEditRequest.getQuestoes());
  }

  @Test
  public void testModeloEditRequestNullQuestoes() {
    int id = 3;
    String modeloNome = "Modelo 03";
    List<QuestaoEditRequest> questoes = null;

    ModeloEditRequest modeloEditRequest = ModeloEditRequest
      .builder()
      .id(id)
      .modeloNome(modeloNome)
      .questoes(questoes)
      .build();

    String expectedToString =
      "ModeloEditRequest(id=3, modeloNome=Modelo 03, questoes=null)";
    String actualToString = modeloEditRequest.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testModeloEditBuilderToString() {
    ModeloEditRequest.ModeloEditRequestBuilder modeloEditRequest = ModeloEditRequest.builder();

    String expectedToString =
      "ModeloEditRequest.ModeloEditRequestBuilder(id=0, modeloNome=null, questoes=null)";
    String actualToString = modeloEditRequest.toString();

    assertEquals(expectedToString, actualToString);
  }
}
