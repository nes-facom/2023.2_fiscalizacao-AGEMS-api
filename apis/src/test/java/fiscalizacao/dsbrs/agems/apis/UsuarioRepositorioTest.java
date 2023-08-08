package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
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
public class UsuarioRepositorioTest {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Before
  @Test
  @Order(1)
  public void testSave() {
    Usuario usuario = new Usuario();
    usuario.setNome("Exemplo Exemplificado Exemplificando");
    usuario.setEmail("exemplo@exemplo.com");
    usuario.setSenha("exemplinho");
    usuario.setDate();
    usuario.setFuncao(Papel.ADMIN);
    usuario.setCargo("Coordenador");

    Usuario usuarioSalvo = usuarioRepositorio.save(usuario);
    assertNotNull(usuarioSalvo.getId());
    assertEquals(usuario.getNome(), usuarioSalvo.getNome());
    assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
    assertEquals(usuario.getSenha(), usuarioSalvo.getSenha());
    assertEquals(usuario.getCargo(), usuarioSalvo.getCargo());
    assertEquals(usuario.getFuncao(), usuarioSalvo.getFuncao());
  }

  @Test
  @Order(2)
  public void testFindById() {
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("alvesju@gmail.com");
    usuario.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo("Analista de Regula\u00E7\u00E3o");
    Usuario usuarioSalvo = usuarioRepositorio.save(usuario);
    assertNotNull(usuarioSalvo.getId());
    Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findById(usuarioSalvo.getId());
    assertTrue(usuarioEncontrado.isPresent());
    assertEquals("Júlia Alves Corazza", usuarioEncontrado.get().getNome());
    assertEquals("alvesju@gmail.com", usuarioEncontrado.get().getEmail());
    assertEquals(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu",
      usuarioEncontrado.get().getSenha()
    );
    assertEquals("Analista de Regulação", usuarioEncontrado.get().getCargo());
    assertEquals(Papel.USER, usuarioEncontrado.get().getFuncao());
  }

  @Test
  @Order(4)
  public void testFindByNome() {
    Usuario usuario = new Usuario();
    usuario.setNome("Exemplo Exemplificado Exemplificando");
    usuario.setEmail("exemplo@exemplo.com");
    usuario.setSenha("exemplinho");
    usuario.setDate();
    usuario.setFuncao(Papel.ADMIN);
    usuario.setCargo("Coordenador");
    usuarioRepositorio.save(usuario);
    Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByNome(
      "Exemplo Exemplificado Exemplificando"
    );
    assertTrue(usuarioEncontrado.isPresent());
  }

  @Test
  @Order(5)
  public void testFindAllByNome() {
    Usuario usuario = new Usuario();
    usuario.setNome("Lu\u00EDza Alves Corazza");
    usuario.setEmail("lanvels@gmail.com");
    usuario.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo("Analista de Regula\u00E7\u00E3o");
    usuarioRepositorio.save(usuario);
    Usuario usuario2 = new Usuario();
    usuario2.setNome("Lu\u00EDza Alves Corazza");
    usuario2.setEmail("l.alves@gmail.com");
    usuario2.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
    usuario2.setDate();
    usuario2.setFuncao(Papel.USER);
    usuario2.setCargo("Analista de Regula\u00E7\u00E3o");
    usuarioRepositorio.save(usuario2);
    List<Usuario> usuariosEncontrado = usuarioRepositorio.findAllByNome(
      "Lu\u00EDza Alves Corazza"
    );
    assertTrue(!usuariosEncontrado.isEmpty());
    assertEquals(2, usuariosEncontrado.size());
  }

  @Test
  @Order(6)
  public void testFindByEmail() {
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juahdsj@gmail.com");
    usuario.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo("Analista de Regula\u00E7\u00E3o");
    usuarioRepositorio.save(usuario);
    Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(
      "juahdsj@gmail.com"
    );
    assertTrue(usuarioEncontrado.isPresent());
  }

  @Test
  @Order(3)
  public void testDelete() {
    Usuario usuario = new Usuario();
    usuario.setNome("Exemplo Exemplificado Exemplificando");
    usuario.setEmail("exemplo@exemplo.com");
    usuario.setSenha("exemplinho");
    usuario.setDate();
    usuario.setFuncao(Papel.ADMIN);
    usuario.setCargo("Coordenador");
    usuarioRepositorio.save(usuario);
    Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(
      "exemplo@exemplo.com"
    );
    assertTrue(usuarioEncontrado.isPresent());
    usuarioRepositorio.delete(usuarioEncontrado.get());
    Usuario usuarioDeletado = usuarioRepositorio
      .findByNome(usuarioEncontrado.get().getNome())
      .orElse(null);
    assertNull(usuarioDeletado);
  }
}
