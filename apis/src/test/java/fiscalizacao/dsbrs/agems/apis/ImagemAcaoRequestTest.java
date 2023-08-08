package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.requests.ImagemAcaoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImagemAcaoRequestTest {

  @Test
  public void testValidImagemAcaoRequest() {
    ImagemAcaoRequest request = ImagemAcaoRequest
      .builder()
      .acao("edit")
      .id(1)
      .imagem(
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
      )
      .build();

    Assertions.assertEquals("edit", request.getAcao());
    Assertions.assertEquals(1, request.getId());
    Assertions.assertEquals(
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==",
      request.getImagem()
    );
  }

  @Test
  public void testInvalidImagemAcaoRequest() {
    ImagemAcaoRequest request = new ImagemAcaoRequest();
    Assertions.assertNull(request.getAcao());
    Assertions.assertEquals(0, request.getId());
    Assertions.assertNull(request.getImagem());

    request.setAcao("invalid");
    Assertions.assertEquals("invalid", request.getAcao());

    request.setId(-1);
    Assertions.assertEquals(-1, request.getId());

    request.setImagem("");
    Assertions.assertEquals("", request.getImagem());
  }

  @Test
  public void testBuilderToString() {
    ImagemAcaoRequest.ImagemAcaoRequestBuilder builder = ImagemAcaoRequest.builder();

    String expectedToString =
      "ImagemAcaoRequest.ImagemAcaoRequestBuilder(acao=null, id=0, imagem=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

   @Test
  public void testToString() {
    ImagemAcaoRequest request = ImagemAcaoRequest.builder().build();

    String expectedToString =
      "ImagemAcaoRequest(acao=null, id=0, imagem=null)";
    String actualToString = request.toString();

    assertEquals(expectedToString, actualToString);
  }
}
