package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;

import org.apache.commons.collections4.IterableUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import fiscalizacao.dsbrs.agems.apis.repositorio.AlternativaRespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AlternativaRespostaRepositorioTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private AlternativaRespostaRepositorio AlternativaRespostaRepositorio;

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
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(true);
    questao.setPergunta("Pergunta 01");
    questao.setPortaria("Portaria 01");
    questaoSalva = questaoRepositorio.save(questao);
    assertNotEquals(questaoSalva.getId(), 0);
  }

  @Test
  public void testFindById() {
    AlternativaResposta AlternativaResposta = new AlternativaResposta();
    AlternativaResposta.setDescricao("Resposta1");
    AlternativaResposta.setQuestao(questaoSalva);
    AlternativaResposta savedAlternativaResposta = AlternativaRespostaRepositorio.save(AlternativaResposta);

    AlternativaResposta foundAlternativaResposta = AlternativaRespostaRepositorio
      .findById(savedAlternativaResposta.getId())
      .orElse(null);
    assertNotNull(foundAlternativaResposta);
    assertEquals(savedAlternativaResposta.getId(), foundAlternativaResposta.getId());
    assertEquals(
      savedAlternativaResposta.getDescricao(),
      foundAlternativaResposta.getDescricao()
    );
  }

  @Test
  public void testSave() {
    AlternativaResposta AlternativaResposta = new AlternativaResposta();
    AlternativaResposta.setDescricao("Resposta1");
    AlternativaResposta.setQuestao(questaoSalva);

    AlternativaResposta AlternativaRespostaSaved = AlternativaRespostaRepositorio.save(AlternativaResposta);

    assertNotNull(AlternativaRespostaSaved.getId());
  }

  @Test
  public void testFindAll() {
    AlternativaResposta AlternativaResposta1 = new AlternativaResposta();
    AlternativaResposta AlternativaResposta2 = new AlternativaResposta();

    AlternativaResposta1.setDescricao("Resposta1");
    AlternativaResposta1.setQuestao(questaoSalva);
    AlternativaRespostaRepositorio.save(AlternativaResposta1);

    AlternativaResposta2.setDescricao("Resposta2");
    AlternativaResposta2.setQuestao(questaoSalva);
    AlternativaRespostaRepositorio.save(AlternativaResposta2);

    Iterable<AlternativaResposta> respostas = AlternativaRespostaRepositorio.findAll();
    assertEquals(2, IterableUtils.size(respostas));
  }

  @Test
  public void testUpdate() {
    AlternativaResposta AlternativaResposta = new AlternativaResposta();
    AlternativaResposta.setDescricao("Resposta1");
    AlternativaResposta.setQuestao(questaoSalva);

    AlternativaResposta AlternativaRespostaSaved = AlternativaRespostaRepositorio.save(AlternativaResposta);

    AlternativaResposta AlternativaRespostaEdit = AlternativaRespostaSaved;

    AlternativaRespostaEdit.setDescricao("Resposta2");

    AlternativaRespostaRepositorio.save(AlternativaRespostaEdit);

    AlternativaResposta AlternativaRespostaRecuperada = AlternativaRespostaRepositorio
      .findById(AlternativaRespostaSaved.getId())
      .orElse(null);

    assertEquals(AlternativaRespostaEdit.getId(), AlternativaRespostaRecuperada.getId());
    assertEquals(
      AlternativaRespostaEdit.getDescricao(),
      AlternativaRespostaRecuperada.getDescricao()
    );
  }

  @Test
  public void testDeleteById() {
    AlternativaResposta AlternativaResposta = new AlternativaResposta();
    AlternativaResposta.setDescricao("Resposta1");
    AlternativaResposta.setQuestao(questaoSalva);

    AlternativaResposta savedAlternativaResposta = AlternativaRespostaRepositorio.save(AlternativaResposta);

    AlternativaRespostaRepositorio.deleteById(savedAlternativaResposta.getId());

    AlternativaResposta deletedResposta = AlternativaRespostaRepositorio
      .findById(savedAlternativaResposta.getId())
      .orElse(null);

    assertNull(deletedResposta);
  }

  @Test
  public void testDeleteByEntidade() {
    AlternativaResposta AlternativaResposta = new AlternativaResposta();
    AlternativaResposta.setDescricao("Resposta1");
    AlternativaResposta.setQuestao(questaoSalva);

    AlternativaResposta savedAlternativaResposta = AlternativaRespostaRepositorio.save(AlternativaResposta);

    AlternativaRespostaRepositorio.delete(savedAlternativaResposta);

    AlternativaResposta deletedResposta = AlternativaRespostaRepositorio
      .findById(savedAlternativaResposta.getId())
      .orElse(null);

    assertNull(deletedResposta);
  }
}
