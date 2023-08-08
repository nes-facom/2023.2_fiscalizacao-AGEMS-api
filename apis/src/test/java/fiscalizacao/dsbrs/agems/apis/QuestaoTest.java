package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestaoTest {

  @Test
  public void testGetandSetPergunta() {
    Questao questao = new Questao();
    questao.setPergunta("Pergunta");
    assertEquals("Pergunta", questao.getPergunta());
  }

  @Test
  public void testGetandSetObjetiva() {
    Questao questao = new Questao();

    questao.setObjetiva(true);
    assertEquals(true, questao.isObjetiva());

    questao.setObjetiva(false);
    assertEquals(false, questao.isObjetiva());
  }

  @Test
  public void testGetandSetPortaria() {
    Questao questao = new Questao();
    questao.setPortaria("Portaria");
    assertEquals("Portaria", questao.getPortaria());
  }

  @Test
  public void testGetandSetModelo() {
    Questao questao = new Questao();
    questao.setModelo(new Modelo());
    assertNotNull(questao.getModelo());
  }

  @Test
  public void testGetandSetTipoResposta() {
    Questao questao = new Questao();
    List<TipoResposta> respostas = new ArrayList<>();
    respostas.add(new TipoResposta());
    respostas.add(new TipoResposta());

    questao.setRespostas(respostas);
    assertNotNull(questao.getRespostas());
    assertEquals(2, questao.getRespostas().size());
  }

  @Test
  public void testConstructor() {
    Questao questao = new Questao("1");

    assertEquals(1,questao.getId());
    
  }


}
