package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.util.Lists;
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
public class QuestaoRepositorioTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private AlternativaRespostaRepositorio tipoRespostaRepositorio;

  @Autowired
  private QuestaoRepositorio questaoRepositorio;

  private Modelo modeloSalvo;

  @Before
  public void setup() {
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 16");
    modeloSalvo = modeloRepositorio.save(modelo);
    assertNotEquals(modeloSalvo.getId(), 0);
  }

  @Test
  public void testFindById() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    Questao questaoRecuperada = questaoRepositorio
      .findById(questaoSalva.getId())
      .orElse(null);

    assertEquals(questaoSalva, questaoRecuperada);
  }

  @Test
  public void testSave() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    assertNotNull(questaoSalva.getId());
  }

  @Test
  public void testUpdate() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Lists.list(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoEdit = questaoRepositorio.save(questao);

    questaoEdit.setPergunta("Pergunta2");
    questaoEdit.setPortaria("7654321");
    questaoEdit.setObjetiva(true);

    questaoRepositorio.save(questaoEdit);

    Questao questaoSalva = questaoRepositorio
      .findById(questaoEdit.getId())
      .orElse(null);

    assertEquals(questaoEdit.getId(), questaoSalva.getId());
    assertEquals(questaoEdit.getPergunta(), questaoSalva.getPergunta());
    assertEquals(questaoEdit.isObjetiva(), questaoSalva.isObjetiva());
    assertEquals(questaoEdit.getPortaria(), questaoSalva.getPortaria());
  }

  @Test
  public void testFindAll() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    questaoRepositorio.save(questao);

    Questao questao2 = new Questao();
    QuestaoModelo questao2Modelo = new QuestaoModelo();
    questao2Modelo.setQuestao(questao2);
    questao2Modelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questao2Modelo));
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("7654321");

    questaoRepositorio.save(questao2);

    Questao questao3 = new Questao();
    QuestaoModelo questao3Modelo = new QuestaoModelo();
    questao3Modelo.setQuestao(questao3);
    questao3Modelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questao3Modelo));
    questao3.setObjetiva(false);
    questao3.setPergunta("Pergunta3");
    questao3.setPortaria("9876543");

    questaoRepositorio.save(questao3);

    Iterable<Questao> questoes = questaoRepositorio.findAll();

    assertEquals(3, IterableUtils.size(questoes));
  }

  @Test
  public void testDeleteByEntidade() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoEdit = questaoRepositorio.save(questao);

    questaoRepositorio.delete(questaoEdit);

    Questao questaoSalva = questaoRepositorio
      .findById(questaoEdit.getId())
      .orElse(null);

    assertNull(questaoSalva);
  }

  @Test
  public void testDeleteById() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoEdit = questaoRepositorio.save(questao);

    questaoRepositorio.deleteById(questaoEdit.getId());

    Questao questaoSalva = questaoRepositorio
      .findById(questaoEdit.getId())
      .orElse(null);

    assertNull(questaoSalva);
  }

  @Test
  public void testDeletCascade() {
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questao.setModelos(Collections.singletonList(questaoModelo));
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    AlternativaResposta tipoResposta1 = new AlternativaResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setDescricao("Resposta1");

    List<AlternativaResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    AlternativaResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);

    assertEquals(tipoResposta1, tipoRespostaSalva);

    listRespostas.add(tipoRespostaSalva);
    questaoSalva.setAlternativasResposta(listRespostas);
    questaoRepositorio.delete(questaoSalva);

    AlternativaResposta tipoRespostaDeletada = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);

    assertNull(tipoRespostaDeletada);
  }
}
