package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaEditRequest;


public class TipoRespostaEditRequestTest {

    @Test
    public void testGetSetAcao() {
       
        String expectedAcao = "edit";
        AlternativaRespostaEditRequest request = new AlternativaRespostaEditRequest();

        
        request.setAcao(expectedAcao);
        String actualAcao = request.getAcao();

       
        Assert.assertEquals(expectedAcao, actualAcao);
    }

    @Test
    public void testGetSetId() {
       
        int expectedId = 1;
        AlternativaRespostaEditRequest request = new AlternativaRespostaEditRequest();

       
        request.setId(expectedId);
        int actualId = request.getId();

     
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetSetResposta() {
     
        String expectedResposta = "Sim";
        AlternativaRespostaEditRequest request = new AlternativaRespostaEditRequest();

        
        request.setResposta(expectedResposta);
        String actualResposta = request.getResposta();

      
        Assert.assertEquals(expectedResposta, actualResposta);
    }

    @Test
    public void testBuilder() {
        String resposta = "Talvez";
        AlternativaRespostaEditRequest request = AlternativaRespostaEditRequest.builder()
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
    AlternativaRespostaEditRequest.TipoRespostaEditRequestBuilder builder = AlternativaRespostaEditRequest
      .builder();

    String expectedToString =
      "TipoRespostaEditRequest.TipoRespostaEditRequestBuilder(acao=null, id=0, resposta=null)";
    String actualToString = builder.toString();

    assertEquals(expectedToString, actualToString);
  }

  @Test
  public void testToString(){
    AlternativaRespostaEditRequest tipoRespostaEditRequest = AlternativaRespostaEditRequest
     .builder().build();
      String expectedToString =
      "TipoRespostaEditRequest(acao=null, id=0, resposta=null)";
    String actualToString = tipoRespostaEditRequest.toString();
     assertEquals(expectedToString, actualToString);
    

  }
}
