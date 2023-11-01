package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ModeloTest {

  @Autowired
  private ModeloRepositorio modeloRepositorio;
  
  @Test
  public void testGetId() {
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 1");
    Modelo modeloSalvo = modeloRepositorio.save(modelo);

    assertNotNull(modeloSalvo.getId());
  }
}
