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
public class TipoRespostaRepositorioTest {

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
  public void testFindById() {
    TipoResposta tipoResposta = new TipoResposta();
    tipoResposta.setResposta("Resposta1");
    tipoResposta.setQuestao(questaoSalva);
    TipoResposta savedTipoResposta = tipoRespostaRepositorio.save(tipoResposta);

    TipoResposta foundTipoResposta = tipoRespostaRepositorio
      .findById(savedTipoResposta.getId())
      .orElse(null);
    assertNotNull(foundTipoResposta);
    assertEquals(savedTipoResposta.getId(), foundTipoResposta.getId());
    assertEquals(
      savedTipoResposta.getResposta(),
      foundTipoResposta.getResposta()
    );
  }

  @Test
  public void testSave() {
    TipoResposta tipoResposta = new TipoResposta();
    tipoResposta.setResposta("Resposta1");
    tipoResposta.setQuestao(questaoSalva);

    TipoResposta tipoRespostaSaved = tipoRespostaRepositorio.save(tipoResposta);

    assertNotNull(tipoRespostaSaved.getId());
  }

  @Test
  public void testFindAll() {
    TipoResposta tipoResposta1 = new TipoResposta();
    TipoResposta tipoResposta2 = new TipoResposta();

    tipoResposta1.setResposta("Resposta1");
    tipoResposta1.setQuestao(questaoSalva);
    tipoRespostaRepositorio.save(tipoResposta1);

    tipoResposta2.setResposta("Resposta2");
    tipoResposta2.setQuestao(questaoSalva);
    tipoRespostaRepositorio.save(tipoResposta2);

    Iterable<TipoResposta> respostas = tipoRespostaRepositorio.findAll();
    assertEquals(2, IterableUtils.size(respostas));
  }

  @Test
  public void testUpdate() {
    TipoResposta tipoResposta = new TipoResposta();
    tipoResposta.setResposta("Resposta1");
    tipoResposta.setQuestao(questaoSalva);

    TipoResposta tipoRespostaSaved = tipoRespostaRepositorio.save(tipoResposta);

    TipoResposta tipoRespostaEdit = tipoRespostaSaved;

    tipoRespostaEdit.setResposta("Resposta2");

    tipoRespostaRepositorio.save(tipoRespostaEdit);

    TipoResposta tipoRespostaRecuperada = tipoRespostaRepositorio
      .findById(tipoRespostaSaved.getId())
      .orElse(null);

    assertEquals(tipoRespostaEdit.getId(), tipoRespostaRecuperada.getId());
    assertEquals(
      tipoRespostaEdit.getResposta(),
      tipoRespostaRecuperada.getResposta()
    );
  }

  @Test
  public void testDeleteById() {
    TipoResposta tipoResposta = new TipoResposta();
    tipoResposta.setResposta("Resposta1");
    tipoResposta.setQuestao(questaoSalva);

    TipoResposta savedTipoResposta = tipoRespostaRepositorio.save(tipoResposta);

    tipoRespostaRepositorio.deleteById(savedTipoResposta.getId());

    TipoResposta deletedResposta = tipoRespostaRepositorio
      .findById(savedTipoResposta.getId())
      .orElse(null);

    assertNull(deletedResposta);
  }

  @Test
  public void testDeleteByEntidade() {
    TipoResposta tipoResposta = new TipoResposta();
    tipoResposta.setResposta("Resposta1");
    tipoResposta.setQuestao(questaoSalva);

    TipoResposta savedTipoResposta = tipoRespostaRepositorio.save(tipoResposta);

    tipoRespostaRepositorio.delete(savedTipoResposta);

    TipoResposta deletedResposta = tipoRespostaRepositorio
      .findById(savedTipoResposta.getId())
      .orElse(null);

    assertNull(deletedResposta);
  }
}
