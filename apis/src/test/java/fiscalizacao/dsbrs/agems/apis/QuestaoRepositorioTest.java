package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.AlternativaRespostaRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.IterableUtils;
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
    questao.setModelo(modeloSalvo);
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
    questao.setModelo(modeloSalvo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    assertNotNull(questaoSalva.getId());
  }

  @Test
  public void testUpdate() {
    Questao questao = new Questao();
    questao.setModelo(modeloSalvo);
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
    questao.setModelo(modeloSalvo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    questaoRepositorio.save(questao);

    Questao questao2 = new Questao();
    questao2.setModelo(modeloSalvo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("7654321");

    questaoRepositorio.save(questao2);

    Questao questao3 = new Questao();
    questao3.setModelo(modeloSalvo);
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
    questao.setModelo(modeloSalvo);
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
    questao.setModelo(modeloSalvo);
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
    questao.setModelo(modeloSalvo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    TipoResposta tipoResposta1 = new TipoResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setResposta("Resposta1");

    List<TipoResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    TipoResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);

    assertEquals(tipoResposta1, tipoRespostaSalva);

    listRespostas.add(tipoRespostaSalva);
    questaoSalva.setRespostas(listRespostas);
    questaoRepositorio.delete(questaoSalva);

    TipoResposta tipoRespostaDeletada = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);

    assertNull(tipoRespostaDeletada);
  }
}
