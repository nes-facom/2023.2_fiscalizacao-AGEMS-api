package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import org.junit.jupiter.api.Test;

public class UnidadeTest {

  @Test
  public void testSetAndGetEndereco() {
    Unidade unidade = new Unidade();
    String endereco = "Rua das Neves, 114";

    unidade.setEndereco(endereco);
    String enderecoRecuperado = unidade.getEndereco();

    assertEquals(endereco, enderecoRecuperado);
  }

  @Test
  public void testSetEGetUnidade() {
    Unidade unidade = new Unidade();
    String idUnidade = "Unidade 1 de Tratamento de Esgoto de Dourados";

    unidade.setIdUnidade(idUnidade);
    String idUnidadeRecuperado = unidade.getIdUnidade();

    assertEquals(idUnidade, idUnidadeRecuperado);
  }

  @Test
  public void testBuilderAndGetters() {
    int id = 1;
    String idUnidade = "Unidade 1 de Tratamento de Esgoto de Dourados";
    String endereco = "Rua das Neves, 114";
    String tipo = "Tratamento de Esgoto";

    Unidade.UnidadeBuilder builder = Unidade
      .builder()
      .id(id)
      .idUnidade(idUnidade)
      .endereco(endereco)
      .tipo(tipo);
    Unidade unidade = builder.build();

    assertEquals(id, unidade.getId());
    assertEquals(idUnidade, unidade.getIdUnidade());
    assertEquals(endereco, unidade.getEndereco());
    assertEquals(tipo, unidade.getTipo());
    assertEquals(
      "Unidade.UnidadeBuilder(id=1, idUnidade=Unidade 1 de Tratamento de Esgoto de Dourados, endereco=Rua das Neves, 114, tipo=Tratamento de Esgoto)",
      builder.toString()
    );
  }

  @Test
  public void testEqualsAndHashCode() {
    Unidade unidade1 = new Unidade(
      1,
      "Unidade 1 de Tratamento de Esgoto de Dourados",
      "Rua das Neves, 114",
      "Tratamento de Esgoto"
    );
    Unidade unidade2 = new Unidade(
      1,
      "Unidade 2 de Tratamento de Esgoto de Dourados",
      "Rua das Neves, 114",
      "Tratamento de Esgoto"
    );
    Unidade unidade3 = new Unidade(
      2,
      "Unidade 3 de Tratamento de Esgoto de Dourados",
      "Rua das Pedras, 111",
      "Tratamento de Esgoto"
    );

    assertNotEquals(unidade1, unidade2);
    assertNotEquals(unidade1, unidade3);
    assertNotEquals(unidade1.hashCode(), unidade2.hashCode());
    assertNotEquals(unidade1.hashCode(), unidade3.hashCode());
  }

  @Test
  public void testToString() {
    Unidade unidade = new Unidade(
      1,
      "Unidade 1 de Tratamento de Esgoto de Dourados",
      "Rua das Neves, 114",
      "Tratamento de Esgoto"
    );
    String expectedToString =
      "Unidade(id=1, idUnidade=Unidade 1 de Tratamento de Esgoto de Dourados, endereco=Rua das Neves, 114, tipo=Tratamento de Esgoto)";

    String toString = unidade.toString();

    assertEquals(expectedToString, toString);
  }
}
