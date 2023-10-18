package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.AlternativaRespostaRepositorio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TipoRespostaTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private AlternativaRespostaRepositorio tipoRespostaRepositorio;

  @Autowired
  private QuestaoRepositorio questaoRepositorio;

  private Questao questaoSalva;

  private Modelo modeloSalvo;

  @Before
  public void setup() {
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 16");
    modeloSalvo = modeloRepositorio.save(modelo);
    assertNotEquals(modeloSalvo.getId(), 0);
    Questao questao = new Questao();
    questao.setModelo(modeloSalvo);
    questao.setObjetiva(true);
    questao.setPergunta("Pergunta 01");
    questao.setPortaria("Portaria 01");
    questaoSalva = questaoRepositorio.save(questao);
    assertNotEquals(questaoSalva.getId(), 0);
  }

  @Test
  public void testSetAndGetResposta() {
    TipoResposta tipoResposta = new TipoResposta();

    tipoResposta.setResposta("Resposta1");

    assertEquals("Resposta1", tipoResposta.getResposta());
  }

  @Test
  public void testSetAndGetQuestao() {
    TipoResposta tipoResposta = new TipoResposta();

    tipoResposta.setQuestao(questaoSalva);

    assertEquals(questaoSalva, tipoResposta.getQuestao());
  }

  @Test
  public void testConstructor() {
    TipoResposta tipoResposta = new TipoResposta("1");

    assertEquals(1,tipoResposta.getId());
  }


}
