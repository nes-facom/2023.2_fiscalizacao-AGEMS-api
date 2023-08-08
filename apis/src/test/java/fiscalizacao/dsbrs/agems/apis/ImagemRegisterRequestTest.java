package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.ImagemRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImagemRegisterRequestTest {

  @Test
  public void testGetSetQuestao() {
    int questao = 1;
    ImagemRegisterRequest request = new ImagemRegisterRequest();
    request.setQuestao(questao);
    Assertions.assertEquals(questao, request.getQuestao());
  }

  @Test
  public void testGetSetImagem() {
    String imagem =
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==";
    ImagemRegisterRequest request = new ImagemRegisterRequest();
    request.setImagem(imagem);
    Assertions.assertEquals(imagem, request.getImagem());
  }

  @Test
  public void testConstructorWithArgs() {
    int questao = 1;
    String imagem =
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==";
    ImagemRegisterRequest request = new ImagemRegisterRequest(questao, imagem);
    Assertions.assertEquals(questao, request.getQuestao());
    Assertions.assertEquals(imagem, request.getImagem());
  }

  @Test
  public void testNoArgsConstructor() {
    ImagemRegisterRequest request = new ImagemRegisterRequest();
    Assertions.assertEquals(0, request.getQuestao());
    Assertions.assertNull(request.getImagem());
  }

  @Test
  public void testBuilder() {
    int questao = 1;
    String imagem =
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==";
    ImagemRegisterRequest request = ImagemRegisterRequest
      .builder()
      .questao(questao)
      .imagem(imagem)
      .build();
    Assertions.assertEquals(questao, request.getQuestao());
    Assertions.assertEquals(imagem, request.getImagem());
  }

  @Test
  public void testToString() {
    int questao = 1;
    String imagem =
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==";
    ImagemRegisterRequest request = ImagemRegisterRequest
      .builder()
      .questao(questao)
      .imagem(imagem)
      .build();
    String expectedToString =
      "ImagemRegisterRequest(questao=1, imagem=iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==)";
    String actualToString = request.toString();

    Assertions.assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testBuilderToString() {
    int questao = 1;
    String imagem =
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==";
    ImagemRegisterRequest.ImagemRegisterRequestBuilder builder = ImagemRegisterRequest
      .builder()
      .questao(questao)
      .imagem(imagem);
    String expectedToString =
      "ImagemRegisterRequest.ImagemRegisterRequestBuilder(questao=1, imagem=iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==)";
    String actualToString = builder.toString();

    Assertions.assertEquals(expectedToString, actualToString);
  }
}
