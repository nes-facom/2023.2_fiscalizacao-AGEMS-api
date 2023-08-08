package fiscalizacao.dsbrs.agems.apis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.requests.QuestaoRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaRegisterRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class QuestaoRegisterRequestTest {

    @Test
    public void testGetPergunta() {
        String pergunta = "Qual é a sua pergunta?";
        QuestaoRegisterRequest request = QuestaoRegisterRequest.builder()
                .pergunta(pergunta)
                .build();

        Assertions.assertEquals(pergunta, request.getPergunta());
    }

    @Test
    public void testGetObjetiva() {
        Boolean objetiva = true;
        QuestaoRegisterRequest request = QuestaoRegisterRequest.builder()
                .objetiva(objetiva)
                .build();

        Assertions.assertEquals(objetiva, request.getObjetiva());
    }

    @Test
    public void testGetPortaria() {
        String portaria = "Portaria 01";
        QuestaoRegisterRequest request = QuestaoRegisterRequest.builder()
                .portaria(portaria)
                .build();

        Assertions.assertEquals(portaria, request.getPortaria());
    }

    @Test
    public void testGetTipoRespostas() {
        TipoRespostaRegisterRequest resposta1 = TipoRespostaRegisterRequest.builder().build();
        TipoRespostaRegisterRequest resposta2 = TipoRespostaRegisterRequest.builder().build();
        List<TipoRespostaRegisterRequest> tipoRespostas = Arrays.asList(resposta1, resposta2);
        QuestaoRegisterRequest request = QuestaoRegisterRequest.builder()
                .tipoRespostas(tipoRespostas)
                .build();

        Assertions.assertEquals(tipoRespostas, request.getTipoRespostas());
    }

    
  @Test
  public void testBuilderToString() {
    QuestaoRegisterRequest.QuestaoRegisterRequestBuilder builder = QuestaoRegisterRequest.builder();
    Assertions.assertNotNull(builder);
    String expectedToString =
      "QuestaoRegisterRequest.QuestaoRegisterRequestBuilder(pergunta=null, objetiva=null, portaria=null, tipoRespostas=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

   @Test
  public void testToString() {
    QuestaoRegisterRequest request = QuestaoRegisterRequest.builder().build();
    Assertions.assertNotNull(request);
    String expectedToString =
      "QuestaoRegisterRequest(pergunta=null, objetiva=null, portaria=null, tipoRespostas=null)";
    String actualToString = request.toString();

    assertEquals(expectedToString, actualToString);
  }
}
