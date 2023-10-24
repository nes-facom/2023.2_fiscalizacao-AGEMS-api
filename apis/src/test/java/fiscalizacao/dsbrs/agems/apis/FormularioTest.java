package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FormularioTest {

  @Test
  public void testConstrutorNoArgs() {
    Formulario formulario = new Formulario();
    assertTrue(formulario != null);

    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);
  }

  @Test
  public void testConstrutorAllArgs() {
    Formulario formulario = new Formulario(
      1,
      new Usuario(),
      new Modelo(),
      new Unidade(),
      LocalDateTime.now(),
      ""
    );
    assertTrue(formulario != null);

    assertNotEquals(formulario.getId(), 0);
    assertNotEquals(formulario.getId(), null);
    assertNotEquals(formulario.getUsuario(), null);
    assertNotEquals(formulario.getModelo(), null);
    assertNotEquals(formulario.getUnidade(), null);
    assertNotEquals(formulario.getDataCriacao(), null);

    assertEquals(formulario.getId(), 1);
    assertTrue(formulario.getUsuario() instanceof Usuario);
    assertTrue(formulario.getModelo() instanceof Modelo);
    assertTrue(formulario.getUnidade() instanceof Unidade);
    assertEquals(formulario.getDataCriacao(), LocalDate.now());
  }

  @Test
  public void testSetEGetUsuario() {
    Formulario formulario = new Formulario();
    Usuario usuario = new Usuario();
    Usuario usuarioNovo = new Usuario(
      1,
      "Zezinho Zé da Silva Sauro",
      "zezinho.silva@gmail.com",
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6",
      LocalDateTime.now(),
      "Coordenador",
      Papel.USER,
      new ArrayList<>()
    );

    formulario.setUsuario(usuario);

    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);

    assertTrue(formulario.getUsuario() != null);
    assertTrue(formulario.getUsuario().equals(usuario));
    assertFalse(usuario.equals(usuarioNovo));

    formulario.setUsuario(usuarioNovo);
    assertTrue(formulario.getUsuario() != null);

    assertTrue(
      (formulario.getUsuario().getNome()).equals("Zezinho Zé da Silva Sauro")
    );

    assertTrue(
      (formulario.getUsuario().getEmail()).equals("zezinho.silva@gmail.com")
    );

    assertTrue(
      (formulario.getUsuario().getSenha()).equals(
          "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
        )
    );

    assertTrue(
      (formulario.getUsuario().getPassword()).equals(
          "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
        )
    );

    assertTrue(
      (formulario.getUsuario().getDataCriacao()).equals(LocalDate.now())
    );

    assertTrue((formulario.getUsuario().getFuncao()).equals(Papel.USER));
    assertTrue((formulario.getUsuario().getCargo()).equals("Coordenador"));
  }

  @Test
  public void testSetEGetModelo() {
    Formulario formulario = new Formulario();
    Modelo modelo = new Modelo();
    formulario.setModelo(modelo);
    assertTrue(formulario.getModelo() != null);

    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);

    Modelo modeloNovo = new Modelo();
    modeloNovo.setModeloNome("Modelo 1");
    modeloNovo.setPerguntas(new ArrayList<>());
    assertFalse(modeloNovo.equals(modelo));
    assertFalse(modelo.equals(null));
    formulario.setModelo(modeloNovo);
    assertFalse(formulario.getModelo() == null);
    assertTrue(formulario.getModelo().getId() == 0);
    assertTrue(formulario.getModelo().getModeloNome().equals("Modelo 1"));
    assertTrue(formulario.getModelo().getPerguntas() instanceof ArrayList);
    assertTrue(formulario.getModelo().getPerguntas() instanceof List);
  }

  @Test
  public void testSetEGetUnidade() {
    Formulario formulario = new Formulario();
    Unidade unidade = new Unidade();
    formulario.setUnidade(unidade);

    assertTrue(formulario.getUnidade() != null);
    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getDataCriacao(), null);

    Unidade unidadeNova = new Unidade(
      1,
      "Unidade 01",
      "Endereço 01",
      "Tratamento de Esgoto"
    );
    assertFalse(unidadeNova.equals(unidade));
    assertFalse(formulario.getUnidade().equals(unidadeNova));
    formulario.setUnidade(unidadeNova);
    assertTrue(formulario.getUnidade().equals(unidadeNova));
  }

  @Test
  public void testBuilderNoArgs() {
    Formulario formulario = Formulario.builder().build();
    assertTrue(formulario != null);

    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);
  }

  @Test
  public void testBuilderAllArgs() {
    Modelo modelo = new Modelo();
    Formulario formulario = Formulario
      .builder()
      .id(1)
      .usuario(new Usuario())
      .modelo(modelo)
      .unidade(new Unidade())
      .observacao("Em conformidade")
      .dataCriacao(LocalDateTime.now())
      .build();

    assertTrue(formulario != null);

    assertNotEquals(formulario.getId(), 0);
    assertNotEquals(formulario.getId(), null);
    assertNotEquals(formulario.getUsuario(), null);
    assertNotEquals(formulario.getModelo(), null);
    assertNotEquals(formulario.getUnidade(), null);
    assertNotEquals(formulario.getDataCriacao(), null);

    assertEquals(formulario.getId(), 1);
    assertTrue(formulario.getUsuario() instanceof Usuario);
    assertTrue(formulario.getModelo() instanceof Modelo);
    assertTrue(formulario.getUnidade() instanceof Unidade);
    assertEquals(formulario.getDataCriacao(), LocalDate.now());
    assertEquals(
      "Formulario(id=1, usuario=Usuario(id=0, nome=null, email=null, senha=null, dataCriacao=null, cargo=null, funcao=null, tokens=null), modelo=" +
      modelo.toString() +
      ", unidade=Unidade(id=0, idUnidade=null, endereco=null, tipo=null), dataCriacao=" +
      LocalDate.now().toString() +
      ", observacao=Em conformidade)",
      formulario.toString()
    );
  }

  @Test
  public void testBuildertoString() {
    Modelo modelo = new Modelo();
    Formulario.FormularioBuilder builder = Formulario
      .builder()
      .id(1)
      .usuario(new Usuario())
      .modelo(modelo)
      .unidade(new Unidade())
      .observacao("Em conformidade")
      .dataCriacao(LocalDateTime.now());

    assertTrue(builder != null);

    assertEquals(
      "Formulario.FormularioBuilder(id=1, usuario=Usuario(id=0, nome=null, email=null, senha=null, dataCriacao=null, cargo=null, funcao=null, tokens=null), modelo=" +
      modelo.toString() +
      ", unidade=Unidade(id=0, idUnidade=null, endereco=null, tipo=null), dataCriacao=" +
      LocalDate.now().toString() +
      ", observacao=Em conformidade)",
      builder.toString()
    );
  }

  @Test
  public void testBuilderArgId() {
    Formulario formulario = Formulario.builder().id(1).build();
    assertTrue(formulario != null);

    assertNotEquals(formulario.getId(), 0);

    assertEquals(formulario.getId(), 1);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);
  }

  @Test
  public void testBuilderArgUser() {
    Formulario formulario = Formulario.builder().usuario(new Usuario()).build();
    assertTrue(formulario != null);

    assertEquals(formulario.getId(), 0);
    assertTrue(formulario.getUsuario() instanceof Usuario);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);
  }

  @Test
  public void testBuilderArgUnidade() {
    Formulario formulario = Formulario.builder().unidade(new Unidade()).build();
    assertTrue(formulario != null);

    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getModelo(), null);
    assertTrue(formulario.getUnidade() instanceof Unidade);
    assertEquals(formulario.getDataCriacao(), null);
  }

  @Test
  public void testBuilderArgModelo() {
    Formulario formulario = Formulario.builder().modelo(new Modelo()).build();
    assertTrue(formulario != null);

    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertTrue(formulario.getModelo() instanceof Modelo);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), null);
  }

  @Test
  public void testBuilderArgDataCriacao() {
    Formulario formulario = Formulario
      .builder()
      .dataCriacao(LocalDateTime.now())
      .build();
    assertTrue(formulario != null);
    assertEquals(formulario.getId(), 0);
    assertEquals(formulario.getUsuario(), null);
    assertEquals(formulario.getModelo(), null);
    assertEquals(formulario.getUnidade(), null);
    assertEquals(formulario.getDataCriacao(), LocalDate.now());
  }

  @Test
  public void testSetDateEGetDataCriacao() {
    Formulario formulario = new Formulario();
    formulario.setDataCriacao(LocalDateTime.now());
    assertTrue(formulario != null);
    assertTrue(formulario.getDataCriacao() != null);
    assertTrue(formulario.getDataCriacao() instanceof LocalDateTime);
    assertEquals(formulario.getDataCriacao(), LocalDateTime.now());
  }

  @Test
  public void testSeteGetObservacao() {
    Formulario formulario = new Formulario();
    formulario.setObservacao("Em conformidade");
    assertTrue(formulario != null);
    assertTrue(formulario.getObservacao() != null);
    assertTrue(formulario.getObservacao() instanceof String);
    assertEquals("Em conformidade", formulario.getObservacao());
  }

  @Test
  public void testSetEGetDataCriacao() {
    Formulario formulario = new Formulario();
    formulario.setDataCriacao(LocalDateTime.now());
    assertTrue(formulario.getDataCriacao() != null);
    assertTrue(formulario.getDataCriacao() instanceof LocalDateTime);
    assertEquals(formulario.getDataCriacao(), LocalDateTime.now());
  }
}
