package fiscalizacao.dsbrs.agems.apis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import fiscalizacao.dsbrs.agems.apis.dominio.chaves.RespostaKey;

public class RespostaTest {

    private Resposta resposta;
    private Formulario formulario;
    private Questao questao;
    private String respostaTexto;
    private String observacao;

    @BeforeEach
    public void setUp() {
        formulario = new Formulario();
        questao = new Questao();
        respostaTexto = "Resposta 01";
        observacao = "Observação 01";

        resposta = Resposta.builder()
                .formulario(formulario)
                .questao(questao)
                .resposta(respostaTexto)
                .obs(observacao)
                .build();
    }

    @Test
    public void testGetFormulario() {
        Formulario expectedFormulario = formulario;
        Formulario actualFormulario = resposta.getFormulario();
        Assertions.assertEquals(expectedFormulario, actualFormulario);
    }

    @Test
    public void testGetQuestao() {
        Questao expectedQuestao = questao;
        Questao actualQuestao = resposta.getQuestao();
        Assertions.assertEquals(expectedQuestao, actualQuestao);
    }

    @Test
    public void testGetResposta() {
        String expectedResposta = respostaTexto;
        String actualResposta = resposta.getResposta();
        Assertions.assertEquals(expectedResposta, actualResposta);
    }

    @Test
    public void testGetObs() {
        String expectedObs = observacao;
        String actualObs = resposta.getObs();
        Assertions.assertEquals(expectedObs, actualObs);
    }

    @Test
    public void testNoArgsConstructor() {
        Resposta novaResposta = new Resposta();
        Assertions.assertNull(novaResposta.getFormulario());
        Assertions.assertNull(novaResposta.getQuestao());
        Assertions.assertNull(novaResposta.getResposta());
        Assertions.assertNull(novaResposta.getObs());
        String toStringEsperado =
      "Resposta(formulario=null, questao=null, resposta=null, obs=null)";
       Assertions.assertNotEquals(resposta.toString(), novaResposta.toString());
        Assertions.assertEquals(toStringEsperado, novaResposta.toString());
        novaResposta.setFormulario(formulario);
        novaResposta.setQuestao(questao);
        novaResposta.setResposta(respostaTexto);
        novaResposta.setObs(observacao);
        Assertions.assertNotNull(novaResposta.getFormulario());
        Assertions.assertNotNull(novaResposta.getQuestao());
        Assertions.assertNotNull(novaResposta.getResposta());
        Assertions.assertNotNull(novaResposta.getObs());
        Assertions.assertEquals(resposta.toString(), novaResposta.toString());
    }
    @Test
    public void testBuildertoString() {
        Resposta.RespostaBuilder builder = Resposta.builder();
      
        String toStringEsperado =
      "Resposta.RespostaBuilder(formulario=null, questao=null, resposta=null, obs=null)";
       Assertions.assertNotEquals(resposta.toString(), builder.toString());
        Assertions.assertEquals(toStringEsperado, builder.toString());

    }

    @Test
    public void testAllArgsConstructor() {
        Resposta novaResposta = new Resposta(formulario, questao, respostaTexto, observacao);
        
        Assertions.assertEquals(resposta.toString(), novaResposta.toString());
    }

    @Test
    public void testKey(){
        RespostaKey respostaKey = new RespostaKey();
        Assertions.assertNotNull(respostaKey);
        
    }
}
