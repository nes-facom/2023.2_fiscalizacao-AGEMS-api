package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.FormularioRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ImagemAcaoRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormularioRequestTest {

  @Test
  public void testFormularioRequest() {
    int id = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemAcaoRequest> imagens = new ArrayList<>();

    FormularioRequest formularioRequest = new FormularioRequest(
      id,
      respostas,
      imagens,
      ""
    );

    Assertions.assertEquals(id, formularioRequest.getId());
    Assertions.assertEquals(respostas, formularioRequest.getRespostas());
    Assertions.assertEquals(imagens, formularioRequest.getImagens());
  }

  @Test
  public void testFormularioRequestRespostaEImagem() {
    int id = 1;

    List<ImagemAcaoRequest> imagens = new ArrayList<>();

    List<RespostaRequest> respostas = new ArrayList<>();
    respostas.add(new RespostaRequest(1, "Resposta 1", "Observação 1"));
    respostas.add(
      new RespostaRequest(2, "Resposta 2", "Observa\u00E7\u00E3o 2")
    );

    ImagemAcaoRequest imagem1 = new ImagemAcaoRequest(
      "edit",
      1,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    ImagemAcaoRequest imagem2 = new ImagemAcaoRequest(
      "edit",
      2,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    ImagemAcaoRequest imagem3 = new ImagemAcaoRequest(
      "edit",
      3,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    ImagemAcaoRequest imagem4 = new ImagemAcaoRequest(
      "edit",
      4,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    imagens.add(imagem1);
    imagens.add(imagem2);
    imagens.add(imagem3);
    imagens.add(imagem4);
    FormularioRequest formularioRequest = new FormularioRequest(
      id,
      respostas,
      imagens,
      ""
    );

    Assertions.assertEquals(id, formularioRequest.getId());
    Assertions.assertEquals(respostas, formularioRequest.getRespostas());
    Assertions.assertEquals(imagens, formularioRequest.getImagens());
  }

  @Test
  public void testBuilderAllArgs() {
    int id = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemAcaoRequest> imagens = new ArrayList<>();

    FormularioRequest formularioRequest = FormularioRequest
      .builder()
      .id(id)
      .respostas(respostas)
      .imagens(imagens)
      .observacao("")
      .build();

    String toStringResult = formularioRequest.toString();

    String expectedToString =
      "FormularioRequest(id=1, respostas=[], imagens=[], observacao=)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }

  @Test
  public void testBuilderNoArgs() {
    FormularioRequest formularioRequest = FormularioRequest.builder().build();

    String toStringResult = formularioRequest.toString();

    String expectedToString =
      "FormularioRequest(id=0, respostas=null, imagens=null, observacao=null)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }

  @Test
  public void testConstructorNoArgs() {
    FormularioRequest formularioRequest = new FormularioRequest();

    String toStringResult = formularioRequest.toString();
    String expectedToString =
      "FormularioRequest(id=0, respostas=null, imagens=null, observacao=null)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }

  @Test
  public void testSetterEGetters() {
    FormularioRequest formularioRequest = new FormularioRequest();

    int id = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemAcaoRequest> imagens = new ArrayList<>();
    respostas.add(new RespostaRequest(1, "Resposta 1", "Observação 1"));
    respostas.add(
      new RespostaRequest(2, "Resposta 2", "Observa\u00E7\u00E3o 2")
    );

    ImagemAcaoRequest imagem1 = new ImagemAcaoRequest(
      "edit",
      1,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    ImagemAcaoRequest imagem2 = new ImagemAcaoRequest(
      "edit",
      2,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    ImagemAcaoRequest imagem3 = new ImagemAcaoRequest(
      "edit",
      3,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    ImagemAcaoRequest imagem4 = new ImagemAcaoRequest(
      "edit",
      4,
      "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
    );
    imagens.add(imagem1);
    imagens.add(imagem2);
    imagens.add(imagem3);
    imagens.add(imagem4);
    formularioRequest.setId(id);
    formularioRequest.setImagens(imagens);
    formularioRequest.setObservacao("ok");
    formularioRequest.setRespostas(respostas);
    Assertions.assertEquals(id, formularioRequest.getId());
    Assertions.assertEquals(imagens, formularioRequest.getImagens());
    Assertions.assertEquals("ok", formularioRequest.getObservacao());
    Assertions.assertEquals(respostas, formularioRequest.getRespostas());
  }

   @Test
  public void testBuilderToString() {
    FormularioRequest.FormularioRequestBuilder builder = FormularioRequest
      .builder();

    String expectedToString =
      "FormularioRequest.FormularioRequestBuilder(id=0, respostas=null, imagens=null, observacao=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }
}
