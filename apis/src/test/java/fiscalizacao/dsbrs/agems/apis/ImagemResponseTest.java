package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.responses.ImagemResponse;

public class ImagemResponseTest {

    @Test
    public void testGettersAndSetters() {
        ImagemResponse imagemResponse = new ImagemResponse();
        imagemResponse.setId(1);
        imagemResponse.setFormulario(1);
        imagemResponse.setQuestao(1);
        imagemResponse.setImagem("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==");

        int id = imagemResponse.getId();
        int formulario = imagemResponse.getFormulario();
        int questao = imagemResponse.getQuestao();
        String imagem = imagemResponse.getImagem();

        assertEquals(1, id);
       assertEquals(1, formulario);
        assertEquals(1, questao);
        assertEquals("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==", imagem);
    }

    @Test
    public void testEqualsAndHashCode() {
        ImagemResponse response1 = ImagemResponse.builder()
                .id(1)
                .formulario(1)
                .questao(1)
                .imagem("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==")
                .build();

        ImagemResponse response2 = ImagemResponse.builder()
                .id(1)
                .formulario(1)
                .questao(1)
                .imagem("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==")
                .build();

        ImagemResponse response3 = ImagemResponse.builder()
                .id(2)
                .formulario(2)
                .questao(2)
                .imagem("abcdefg")
                .build();

        
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);

        
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    public void testToString() {
        ImagemResponse response = ImagemResponse.builder()
                .id(1)
                .formulario(1)
                .questao(1)
                .imagem("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==")
                .build();

        String expectedToString = "ImagemResponse(id=1, formulario=1, questao=1, imagem=iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAA/eRvcmELCYyAZWcnMjExg5GOCDCe5yimWz3G2wAAAABJRU5ErkJggg==)";
        assertEquals(expectedToString, response.toString());
    }
}