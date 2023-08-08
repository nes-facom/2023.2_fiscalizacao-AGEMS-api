package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaEditRequest;


public class TipoRespostaEditRequestTest {

    @Test
    public void testGetSetAcao() {
       
        String expectedAcao = "edit";
        TipoRespostaEditRequest request = new TipoRespostaEditRequest();

        
        request.setAcao(expectedAcao);
        String actualAcao = request.getAcao();

       
        Assert.assertEquals(expectedAcao, actualAcao);
    }

    @Test
    public void testGetSetId() {
       
        int expectedId = 1;
        TipoRespostaEditRequest request = new TipoRespostaEditRequest();

       
        request.setId(expectedId);
        int actualId = request.getId();

     
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetSetResposta() {
     
        String expectedResposta = "Sim";
        TipoRespostaEditRequest request = new TipoRespostaEditRequest();

        
        request.setResposta(expectedResposta);
        String actualResposta = request.getResposta();

      
        Assert.assertEquals(expectedResposta, actualResposta);
    }

    @Test
    public void testBuilder() {
        String resposta = "Talvez";
        TipoRespostaEditRequest request = TipoRespostaEditRequest.builder()
                .acao("edit")
                .id(1)
                .resposta(resposta)
                .build();

        Assertions.assertEquals(resposta, request.getResposta());
        Assertions.assertEquals("edit", request.getAcao());
        Assertions.assertEquals(1, request.getId());
    }

    @Test
  public void testBuilderToString() {
    TipoRespostaEditRequest.TipoRespostaEditRequestBuilder builder = TipoRespostaEditRequest
      .builder();

    String expectedToString =
      "TipoRespostaEditRequest.TipoRespostaEditRequestBuilder(acao=null, id=0, resposta=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testToString(){
    TipoRespostaEditRequest tipoRespostaEditRequest = TipoRespostaEditRequest
     .builder().build();
      String expectedToString =
      "TipoRespostaEditRequest(acao=null, id=0, resposta=null)";
    String actualToString = tipoRespostaEditRequest.toString();
     assertEquals(expectedToString, actualToString);
    

  }
}
