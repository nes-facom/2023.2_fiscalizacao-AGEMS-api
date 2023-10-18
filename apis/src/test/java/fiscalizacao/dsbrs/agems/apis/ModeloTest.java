package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import java.util.ArrayList;
import java.util.List;
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
public class ModeloTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Test
  public void testGetandSetNome() {
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 1");

    assertEquals(modelo.getModeloNome(), "Modelo 1");
  }

  @Test
  public void testGetandSetPerguntas() {
    Modelo modelo = new Modelo();

    modelo.setNome("Modelo 1");

    List<Questao> questoes = new ArrayList<>();

    Questao questao1 = new Questao();
    Questao questao2 = new Questao();

    questoes.add(questao1);
    questoes.add(questao2);

    modelo.setPerguntas(questoes);

    assertEquals(modelo.getPerguntas().size(), 2);
    assertEquals(modelo.getPerguntas(), questoes);
  }

  @Test
  public void testGetId() {
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    assertNotNull(modeloSalvo.getId());
  }
}
