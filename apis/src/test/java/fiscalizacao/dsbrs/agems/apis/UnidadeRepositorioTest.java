package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;

import java.util.Optional;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnidadeRepositorioTest {

  @Autowired
  private UnidadeRepositorio unidadeRepositorio;

  @Test
  @Order(5)
  public void testFindByIdUnidade() {
    Unidade unidade = new Unidade();
    unidade.setNome("Unidade 1 de Tratamento de Esgoto de Dourados");
    unidade.setEndereco("Rua das Neves, 114");
    unidadeRepositorio.save(unidade);

    Optional<Unidade> foundUnidade = unidadeRepositorio.findByNome(
      "Unidade 1 de Tratamento de Esgoto de Dourados"
    );

    assertTrue(foundUnidade.isPresent());
    assertEquals(
      "Unidade 1 de Tratamento de Esgoto de Dourados",
      foundUnidade.get().getNome()
    );
    assertEquals("Rua das Neves, 114", foundUnidade.get().getEndereco());
  }

  @Test
  @Order(1)
  public void testSave() {
    Unidade unidade = Unidade.builder()
        .nome("Unidade 1 de Tratamento de Esgoto de Dourados")
        .endereco("Rua das Neves, 114")
        .build();
    Unidade savedUnidade = unidadeRepositorio.save(unidade);
    assertNotNull(savedUnidade.getId());
  }

  @Test
  @Order(3)
  public void testFindById() {
    Unidade unidade = Unidade.builder()
        .nome("Unidade 1 de Tratamento de Esgoto de Dourados")
        .endereco("Rua das Neves, 114")
        .build();
    Unidade savedUnidade = unidadeRepositorio.save(unidade);

    Unidade foundUnidade = unidadeRepositorio
      .findById(savedUnidade.getId())
      .orElse(null);
    assertNotNull(foundUnidade);
    assertEquals(savedUnidade.getId(), foundUnidade.getId());
    assertEquals(savedUnidade.getNome(), foundUnidade.getNome());
  }

  @Test
  @Order(2)
  public void testFindAll() {
    Unidade unidade1 = Unidade.builder()
        .nome("Unidade 1 de Tratamento de Esgoto de Dourados")
        .endereco("Rua das Constelações, 112")
        .build();
    Unidade unidade2 = Unidade.builder()
        .nome("Unidade 2 de Tratamento de Esgoto de Dourados")
        .endereco("Rua das Pedras, 111")
        .build();
    unidadeRepositorio.save(unidade1);
    unidadeRepositorio.save(unidade2);

    Iterable<Unidade> unidades = unidadeRepositorio.findAll();
    assertNotEquals(0, IterableUtils.size(unidades));
  }

  @Test
  @Order(3)
  public void testUpdate() {
    Unidade unidade = Unidade.builder()
        .nome("Unidade 1 de Tratamento de Esgoto de Dourados")
        .endereco("Rua das Neves, 114")
        .build();
    Unidade savedUnidade = unidadeRepositorio.save(unidade);

    savedUnidade.setNome(
      "Unidade 1 de Tratamento de Esgoto de Pixinguinha"
    );
    Unidade updatedUnidade = unidadeRepositorio.save(savedUnidade);

    assertEquals(
      "Unidade 1 de Tratamento de Esgoto de Pixinguinha",
      updatedUnidade.getNome()
    );
  }

  @Test
  @Order(4)
  public void testDelete() {
    Unidade unidade = Unidade.builder()
        .nome("Unidade 1 de Tratamento de Esgoto de Dourados")
        .endereco("Rua das Neves, 114")
        .build();
    Unidade savedUnidade = unidadeRepositorio.save(unidade);

    unidadeRepositorio.deleteById(savedUnidade.getId());

    Unidade deletedUnidade = unidadeRepositorio
      .findById(savedUnidade.getId())
      .orElse(null);
    assertNull(deletedUnidade);
  }
}
