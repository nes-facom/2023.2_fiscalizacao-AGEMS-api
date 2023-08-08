package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.QuestaoEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaEditRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuestaoEditRequestTest {

  @Test
  public void testConstructor() {
    QuestaoEditRequest request = new QuestaoEditRequest();
    Assertions.assertNotNull(request);
    String expectedToString =
      "QuestaoEditRequest(acao=null, id=0, pergunta=null, objetiva=null, portaria=null, tipoRespostas=null)";
    String actualToString = request.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testBuilderToString() {
    QuestaoEditRequest.QuestaoEditRequestBuilder builder = QuestaoEditRequest.builder();
    Assertions.assertNotNull(builder);
    String expectedToString =
      "QuestaoEditRequest.QuestaoEditRequestBuilder(acao=null, id=0, pergunta=null, objetiva=null, portaria=null, tipoRespostas=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testSetters() {
    TipoRespostaEditRequest resposta1 = TipoRespostaEditRequest
      .builder()
      .build();
    TipoRespostaEditRequest resposta2 = TipoRespostaEditRequest
      .builder()
      .build();
    List<TipoRespostaEditRequest> tipoRespostas = new ArrayList<>();
    tipoRespostas.add(resposta1);
    tipoRespostas.add(resposta2);
    QuestaoEditRequest request = QuestaoEditRequest
      .builder().build();
      request.setId(1);
      request.setAcao("edit");
      request.setPergunta("Sample question");
      request.setPortaria("Portaria 01");
      request.setObjetiva(true);
      request.setTipoRespostas(tipoRespostas);
    Assertions.assertEquals("edit", request.getAcao());
    Assertions.assertEquals("Sample question", request.getPergunta());
    Assertions.assertTrue(request.getObjetiva());
    Assertions.assertEquals("Portaria 01", request.getPortaria());
    
    Assertions.assertNotNull(request);
    String expectedToString =
      "QuestaoEditRequest(acao=edit, id=1, pergunta=Sample question, objetiva=true, portaria=Portaria 01, tipoRespostas=[TipoRespostaEditRequest(acao=null, id=0, resposta=null), TipoRespostaEditRequest(acao=null, id=0, resposta=null)])";
    String actualToString = request.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testGetAcao() {
    QuestaoEditRequest request = QuestaoEditRequest
      .builder()
      .acao("edit")
      .build();

    Assertions.assertEquals("edit", request.getAcao());
  }

  @Test
  public void testGetId() {
    QuestaoEditRequest request = QuestaoEditRequest.builder().id(1).build();

    Assertions.assertEquals(1, request.getId());
  }

  @Test
  public void testGetPergunta() {
    QuestaoEditRequest request = QuestaoEditRequest
      .builder()
      .pergunta("Sample question")
      .build();

    Assertions.assertEquals("Sample question", request.getPergunta());
  }

  @Test
  public void testGetObjetiva() {
    QuestaoEditRequest request = QuestaoEditRequest
      .builder()
      .objetiva(true)
      .build();

    Assertions.assertTrue(request.getObjetiva());
  }

  @Test
  public void testGetPortaria() {
    QuestaoEditRequest request = QuestaoEditRequest
      .builder()
      .portaria("Portaria 01")
      .build();

    Assertions.assertEquals("Portaria 01", request.getPortaria());
  }

  @Test
  public void testGetTipoRespostas() {
    TipoRespostaEditRequest resposta1 = TipoRespostaEditRequest
      .builder()
      .build();
    TipoRespostaEditRequest resposta2 = TipoRespostaEditRequest
      .builder()
      .build();
    List<TipoRespostaEditRequest> tipoRespostas = new ArrayList<>();
    tipoRespostas.add(resposta1);
    tipoRespostas.add(resposta2);

    QuestaoEditRequest request = QuestaoEditRequest
      .builder()
      .tipoRespostas(tipoRespostas)
      .build();

    Assertions.assertEquals(tipoRespostas, request.getTipoRespostas());
  }
}
