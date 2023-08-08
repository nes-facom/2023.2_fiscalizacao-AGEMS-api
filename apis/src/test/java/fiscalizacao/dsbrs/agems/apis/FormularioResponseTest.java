package fiscalizacao.dsbrs.agems.apis;


import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ImagemResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloFormResponse;
import fiscalizacao.dsbrs.agems.apis.responses.RespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UsuarioFormResponse;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;

public class FormularioResponseTest {
    @Test
    public void testNoArgsBuilder(){
        FormularioResponse formulario = FormularioResponse.builder().build();
        assertNotNull(formulario);
        assertEquals(0, formulario.getId());
        assertNull(formulario.getRespostas());
        assertNull(formulario.getUsuario());
        assertNull(formulario.getModelo());
        assertNull(formulario.getImagens());
        assertNull(formulario.getUnidade());
        assertNull(formulario.getObservacao());
    }
    @Test
    public void testConstructorNoArgs(){
        FormularioResponse formulario = new FormularioResponse();
        assertNotNull(formulario);
        assertEquals(0, formulario.getId());
        assertNull(formulario.getRespostas());
        assertNull(formulario.getUsuario());
        assertNull(formulario.getModelo());
        assertNull(formulario.getImagens());
        assertNull(formulario.getUnidade());
        assertNull(formulario.getObservacao());
    }
    @Test
    public void testGettersAndSetters() {
       
        UsuarioFormResponse usuario = new UsuarioFormResponse();
        ModeloFormResponse modelo = new ModeloFormResponse();
        UnidadeResponse unidade = new UnidadeResponse();
        List<RespostaResponse> respostas = Arrays.asList(new RespostaResponse(), new RespostaResponse());
        List<ImagemResponse> imagens = Arrays.asList(new ImagemResponse(), new ImagemResponse());
        String observacao = "Test observacao";

     
        FormularioResponse formulario = new FormularioResponse();
        formulario.setId(1);
        formulario.setUsuario(usuario);
        formulario.setModelo(modelo);
        formulario.setUnidade(unidade);
        formulario.setRespostas(respostas);
        formulario.setImagens(imagens);
        formulario.setObservacao(observacao);

      
        assertEquals(1, formulario.getId());
        assertEquals(usuario, formulario.getUsuario());
        assertEquals(modelo, formulario.getModelo());
        assertEquals(unidade, formulario.getUnidade());
        assertEquals(respostas, formulario.getRespostas());
        assertEquals(imagens, formulario.getImagens());
        assertEquals(observacao, formulario.getObservacao());
    }

    @Test
    public void testEqualsAndHashCode() {
     
        FormularioResponse formulario1 = FormularioResponse.builder().id(1).build();
        FormularioResponse formulario2 = FormularioResponse.builder().id(1).build();

        
        assertEquals(formulario1, formulario2);

        
        assertEquals(formulario1.hashCode(), formulario2.hashCode());
    }
}
