package fiscalizacao.dsbrs.agems.apis;

import fiscalizacao.dsbrs.agems.apis.requests.FormularioRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ImagemRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormularioRegisterRequestTest {

  @Test
  public void testFormularioRegisterRequest() {
    int modelo = 1;
    int unidade = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemRegisterRequest> imagens = new ArrayList<>();

    FormularioRegisterRequest formulario = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .respostas(respostas)
      .imagens(imagens)
      .observacao("")
      .build();

    Assertions.assertEquals(modelo, formulario.getModelo());
    Assertions.assertEquals(unidade, formulario.getUnidade());
    Assertions.assertEquals(respostas, formulario.getRespostas());
    Assertions.assertEquals(imagens, formulario.getImagens());
    Assertions.assertEquals("", formulario.getObservacao());
  }

  @Test
  public void testFormularioRegisterRequestRequiredFields() {
    FormularioRegisterRequest formulario = new FormularioRegisterRequest();

    Assertions.assertEquals(0, formulario.getModelo());
    Assertions.assertEquals(0, formulario.getUnidade());
    Assertions.assertNull(formulario.getRespostas());
    Assertions.assertNull(formulario.getImagens());
  }

  @Test
  public void testFormularioRegisterRequestWithRespostas() {
    int modelo = 1;
    int unidade = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    respostas.add(new RespostaRequest(1, "Resposta 1", "Observação 1"));
    respostas.add(
      new RespostaRequest(2, "Resposta 2", "Observa\u00E7\u00E3o 2")
    );

    FormularioRegisterRequest formulario = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .respostas(respostas)
      .build();

    Assertions.assertEquals(modelo, formulario.getModelo());
    Assertions.assertEquals(unidade, formulario.getUnidade());
    Assertions.assertEquals(respostas, formulario.getRespostas());
    Assertions.assertNull(formulario.getImagens());
  }

  @Test
  public void testFormularioRegisterRequestWithImagens() {
    int modelo = 1;
    int unidade = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    respostas.add(new RespostaRequest(1, "Resposta 1", "Observação 1"));
    respostas.add(
      new RespostaRequest(2, "Resposta 2", "Observa\u00E7\u00E3o 2")
    );

    List<ImagemRegisterRequest> imagens = new ArrayList<>();
    imagens.add(
      new ImagemRegisterRequest(
        1,
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
      )
    );
    imagens.add(
      new ImagemRegisterRequest(
        1,
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
      )
    );
    imagens.add(
      new ImagemRegisterRequest(
        1,
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
      )
    );
    imagens.add(
      new ImagemRegisterRequest(
        1,
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg=="
      )
    );

    FormularioRegisterRequest formulario = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .imagens(imagens)
      .build();

    Assertions.assertEquals(modelo, formulario.getModelo());
    Assertions.assertEquals(unidade, formulario.getUnidade());
    Assertions.assertNull(formulario.getRespostas());
    Assertions.assertEquals(imagens, formulario.getImagens());
  }

  @Test
  public void testFormularioRegisterRequestEqualsAndHashCode() {
    int modelo = 1;
    int unidade = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemRegisterRequest> imagens = new ArrayList<>();

    FormularioRegisterRequest formulario1 = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .respostas(respostas)
      .imagens(imagens)
      .build();

    FormularioRegisterRequest formulario2 = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .respostas(respostas)
      .imagens(imagens)
      .build();

    Assertions.assertEquals(formulario1, formulario2);
    Assertions.assertEquals(formulario1.hashCode(), formulario2.hashCode());
  }

  @Test
  public void testFormularioRegisterRequestNotEquals() {
    int modelo1 = 1;
    int unidade1 = 1;
    List<RespostaRequest> respostas1 = new ArrayList<>();
    List<ImagemRegisterRequest> imagens1 = new ArrayList<>();

    int modelo2 = 2;
    int unidade2 = 2;
    List<RespostaRequest> respostas2 = new ArrayList<>();
    List<ImagemRegisterRequest> imagens2 = new ArrayList<>();

    FormularioRegisterRequest formulario1 = FormularioRegisterRequest
      .builder()
      .modelo(modelo1)
      .unidade(unidade1)
      .respostas(respostas1)
      .imagens(imagens1)
      .build();

    FormularioRegisterRequest formulario2 = FormularioRegisterRequest
      .builder()
      .modelo(modelo2)
      .unidade(unidade2)
      .respostas(respostas2)
      .imagens(imagens2)
      .build();

    Assertions.assertNotEquals(formulario1, formulario2);
    Assertions.assertNotEquals(formulario1.hashCode(), formulario2.hashCode());
  }

  @Test
   public void testFormularioRegisterRequestToString() {
    int modelo = 1;
    int unidade = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemRegisterRequest> imagens = new ArrayList<>();

    FormularioRegisterRequest formulario = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .respostas(respostas)
      .imagens(imagens)
      .build();
      formulario.setObservacao("");

    String toStringResult = formulario.toString();

    String expectedToString =
      "FormularioRegisterRequest(dataCriacao=null, modelo=1, unidade=1, respostas=[], imagens=[], observacao=)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }
  @Test
   public void testFormularioRegisterBuilderRequestToString() {
    int modelo = 1;
    int unidade = 1;
    List<RespostaRequest> respostas = new ArrayList<>();
    List<ImagemRegisterRequest> imagens = new ArrayList<>();

    FormularioRegisterRequest.FormularioRegisterRequestBuilder builder = FormularioRegisterRequest
      .builder()
      .modelo(modelo)
      .unidade(unidade)
      .respostas(respostas)
      .imagens(imagens)
      .observacao("");

    String toStringResult = builder.toString();

    String expectedToString =
      "FormularioRegisterRequest.FormularioRegisterRequestBuilder(dataCriacao=null, modelo=1, unidade=1, respostas=[], imagens=[], observacao=)";
    Assertions.assertEquals(expectedToString, toStringResult);
  }
}
