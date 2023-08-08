package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.TipoRespostaRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.IterableUtils;
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
public class ModeloRepositorioTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private TipoRespostaRepositorio tipoRespostaRepositorio;

  @Autowired
  private QuestaoRepositorio questaoRepositorio;

  @Test
  public void testFindById() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);
    Modelo modeloRecuperado = modeloRepositorio
      .findById(modeloSalvo.getId())
      .orElse(null);

    assertEquals(modeloSalvo, modeloRecuperado);
  }

  @Test
  public void testSave() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    assertNotNull(modeloSalvo.getId());
  }

  @Test
  public void testUpdate() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("mdelo1");
    Modelo modeloEdit = modeloRepositorio.save(modelo);

    modeloEdit.setModeloNome("modelo1");
    modeloRepositorio.save(modeloEdit);
    Modelo modeloSalvo = modeloRepositorio
      .findById(modeloEdit.getId())
      .orElse(null);

    assertEquals("modelo1", modeloSalvo.getModeloNome());
  }

  @Test
  public void testDeleteByEntidade() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    modeloRepositorio.delete(modeloSalvo);
    assertNull(modeloRepositorio.findById(modeloSalvo.getId()).orElse(null));
  }

  @Test
  public void testDeleteById() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    modeloRepositorio.deleteById(modeloSalvo.getId());
    assertNull(modeloRepositorio.findById(modeloSalvo.getId()).orElse(null));
  }

  @Test
  public void testFindAll() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("modelo1");
    modeloRepositorio.save(modelo);
    Modelo modelo2 = new Modelo();
    modelo2.setModeloNome("modelo2");
    modeloRepositorio.save(modelo2);
    Modelo modelo3 = new Modelo();
    modelo3.setModeloNome("modelo3");
    modeloRepositorio.save(modelo3);

    Iterable<Modelo> modelos = modeloRepositorio.findAll();
    assertEquals(3, IterableUtils.size(modelos));
  }

  @Test
  public void testDeletCascade() {
    Modelo modelo = new Modelo();
    modelo.setModeloNome("modelo1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setModelo(modelo);
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
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setRespostas(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    questao2.setModelo(modelo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    TipoResposta tipoResposta2 = new TipoResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setResposta("Resposta2");

    List<TipoResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    TipoResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setRespostas(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setPerguntas(questoes);

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
