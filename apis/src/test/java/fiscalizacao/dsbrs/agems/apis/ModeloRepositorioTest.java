package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
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
public class ModeloRepositorioTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private AlternativaRespostaRepositorio tipoRespostaRepositorio;

  @Autowired
  private QuestaoRepositorio questaoRepositorio;

  @Test
  public void testFindById() {
    Modelo modelo = new Modelo();
    modelo.setNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);
    Modelo modeloRecuperado = modeloRepositorio
      .findById(modeloSalvo.getId())
      .orElse(null);

    assertEquals(modeloSalvo, modeloRecuperado);
  }

  @Test
  public void testSave() {
    Modelo modelo = new Modelo();
    modelo.setNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    assertNotNull(modeloSalvo.getId());
  }

  @Test
  public void testUpdate() {
    Modelo modelo = new Modelo();
    modelo.setNome("mdelo1");
    Modelo modeloEdit = modeloRepositorio.save(modelo);

    modeloEdit.setNome("modelo1");
    modeloRepositorio.save(modeloEdit);
    Modelo modeloSalvo = modeloRepositorio
      .findById(modeloEdit.getId())
      .orElse(null);

    assertEquals("modelo1", modeloSalvo.getNome());
  }

  @Test
  public void testDeleteByEntidade() {
    Modelo modelo = new Modelo();
    modelo.setNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    modeloRepositorio.delete(modeloSalvo);
    assertNull(modeloRepositorio.findById(modeloSalvo.getId()).orElse(null));
  }

  @Test
  public void testDeleteById() {
    Modelo modelo = new Modelo();
    modelo.setNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    modeloRepositorio.deleteById(modeloSalvo.getId());
    assertNull(modeloRepositorio.findById(modeloSalvo.getId()).orElse(null));
  }

  @Test
  public void testFindAll() {
    Modelo modelo = new Modelo();
    modelo.setNome("modelo1");
    modeloRepositorio.save(modelo);
    Modelo modelo2 = new Modelo();
    modelo2.setNome("modelo2");
    modeloRepositorio.save(modelo2);
    Modelo modelo3 = new Modelo();
    modelo3.setNome("modelo3");
    modeloRepositorio.save(modelo3);

    Iterable<Modelo> modelos = modeloRepositorio.findAll();
    assertEquals(3, IterableUtils.size(modelos));
  }

  @Test
  public void testDeletCascade() {
    Modelo modelo = new Modelo();
    modelo.setNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();
    Questao questao = new Questao();
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setModelo(modelo);
    questaoModelo.setQuestao(questao);
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
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setAlternativasResposta(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    QuestaoModelo questao2Modelo = new QuestaoModelo();
    questao2Modelo.setModelo(modelo);
    questao2Modelo.setQuestao(questao2);
    questao2.setModelos(Collections.singletonList(questao2Modelo));
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    AlternativaResposta tipoResposta2 = new AlternativaResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setDescricao("Resposta2");

    List<AlternativaResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    AlternativaResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setAlternativasResposta(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setQuestoes(List.of(questaoModelo, questao2Modelo));

    modeloRepositorio.delete(modeloSalvo);

    assertNull(modeloRepositorio.findById(modeloSalvo.getId()).orElse(null));
    assertNull(questaoRepositorio.findById(questaoSalva.getId()).orElse(null));
    assertNull(questaoRepositorio.findById(questaoSalva2.getId()).orElse(null));
    assertNull(
      tipoRespostaRepositorio.findById(tipoRespostaSalva.getId()).orElse(null)
    );
    assertNull(
      tipoRespostaRepositorio.findById(tipoRespostaSalva2.getId()).orElse(null)
    );
  }
}
