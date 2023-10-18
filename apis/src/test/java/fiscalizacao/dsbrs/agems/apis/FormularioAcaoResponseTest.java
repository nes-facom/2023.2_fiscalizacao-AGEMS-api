package fiscalizacao.dsbrs.agems.apis;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.responses.FormularioAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;

public class FormularioAcaoResponseTest {
   @Test
    public void testEquals() {
        FormularioAcaoResponse response1 = FormularioAcaoResponse.builder()
                .acao("edit")
                .formulario(new FormularioResponse())
                .build();

        FormularioAcaoResponse response2 = FormularioAcaoResponse.builder()
                .acao("edit")
                .formulario(new FormularioResponse())
                .build();

        FormularioAcaoResponse response3 = FormularioAcaoResponse.builder()
                .acao("delete")
                .formulario(new FormularioResponse())
                .build();

        assertEquals(response1, response2);

        assertNotEquals(response1, response3);
    }

    @Test
    public void testToString() {
        FormularioAcaoResponse response = FormularioAcaoResponse.builder()
                .acao("edit")
                .formulario(new FormularioResponse())
                .build();

        String expectedToString = "FormularioAcaoResponse(acao=edit, formulario=FormularioResponse(dataCriacao=null, id=0, usuario=null, modelo=null, unidade=null, respostas=null, imagens=null, observacao=null))";
        assertEquals(expectedToString, response.toString());
    }

    @Test
    public void testHashCode() {
        FormularioAcaoResponse response1 = FormularioAcaoResponse.builder()
                .acao("edit")
                .formulario(new FormularioResponse())
                .build();

        FormularioAcaoResponse response2 = FormularioAcaoResponse.builder()
                .acao("edit")
                .formulario(new FormularioResponse())
                .build();
FormularioAcaoResponse response3 = FormularioAcaoResponse.builder()
                .acao("delete")
                .formulario(new FormularioResponse())
                .build();
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(),response3.hashCode());
    }

    @Test
    public void testConstructor() {
        FormularioAcaoResponse response = new FormularioAcaoResponse();

        assertEquals(null, response.getAcao());
        assertNull(response.getFormulario());
    }

    @Test
    public void testBuilder() {
        FormularioAcaoResponse response = FormularioAcaoResponse.builder()
                .acao("edit")
                .formulario(new FormularioResponse())
                .build();

        assertEquals("edit", response.getAcao());
        assertNotNull(response.getFormulario());
    }

    @Test
    public void testSettes() {
        FormularioAcaoResponse response = new FormularioAcaoResponse();

        response.setAcao("edit");
        response.setFormulario(new FormularioResponse());

       assertEquals("edit", response.getAcao());
        assertNotNull(response.getFormulario());
    }

    @Test
    public void testGetAcao() {
      String expectedAcao = "edit";
      FormularioAcaoResponse response = FormularioAcaoResponse.builder()
          .acao(expectedAcao)
          .build();
  
      String actualAcao = response.getAcao();
  
      assertEquals(expectedAcao, actualAcao);
    }
  
    @Test
    public void testGetFormulario() {
      FormularioResponse expectedFormulario = new FormularioResponse();
      FormularioAcaoResponse response = FormularioAcaoResponse.builder()
          .formulario(expectedFormulario)
          .build();
  
      FormularioResponse actualFormulario = response.getFormulario();
  
      assertEquals(expectedFormulario, actualFormulario);
    }
  
    @Test
    public void testEqualsAndHashCode() {
      FormularioAcaoResponse response1 = FormularioAcaoResponse.builder()
          .acao("edit")
          .formulario(new FormularioResponse())
          .build();
      FormularioAcaoResponse response2 = FormularioAcaoResponse.builder()
          .acao("edit")
          .formulario(new FormularioResponse())
          .build();
      FormularioAcaoResponse response3 = FormularioAcaoResponse.builder()
          .acao("delete")
          .formulario(new FormularioResponse())
          .build();
  
      assertEquals(response1, response2);
      assertEquals(response1.hashCode(), response2.hashCode());
      assertNotEquals(response1, response3);
      assertNotEquals(response1.hashCode(), response3.hashCode());
    }
  }