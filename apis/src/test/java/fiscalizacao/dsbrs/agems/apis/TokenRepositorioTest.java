package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.dominio.TokenType;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.repositorio.TokenRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepositorioTest {

  @Autowired
  private TokenRepositorio tokenRepositorio;

  @Mock
  private UsuarioRepositorio usuarioRepositorio;

  private Usuario usuarioSalvo;

  @Before
  public void setup() {
    Usuario usuario = new Usuario();
    usuario.setNome("Exemplo Exemplificado Exemplificando");
    usuario.setEmail("exemplo@exemplo.com");
    usuario.setSenha("exemplinho");
    usuario.setDate();
    usuario.setFuncao(Papel.ADMIN);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
  }

  @Test
  public void testSave() {
    Token token = new Token(
      1,
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE",
      TokenType.BEARER,
      false,
      false,
      usuarioSalvo
    );

    Token tokenSalvo = tokenRepositorio.save(token);
    assertEquals(TokenType.BEARER, tokenSalvo.getTokenType());
    assertEquals(false, tokenSalvo.isExpired());
    assertEquals(false, tokenSalvo.isRevoked());
    assertEquals(usuarioSalvo, tokenSalvo.getUsuario());
    assertNotNull(null, tokenSalvo);
  }

  @Test
  public void testSaveAll() {
    Token token = new Token(
      2,
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE",
      TokenType.BEARER,
      false,
      false,
      usuarioSalvo
    );

    Token token2 = new Token(
      3,
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E",
      TokenType.BEARER,
      false,
      false,
      usuarioSalvo
    );

    List<Token> tokens = new ArrayList<>();
    tokens.add(token);
    tokens.add(token2);

    List<Token> tokensSalvos = tokenRepositorio.saveAll(tokens);
    for (Token tokenSalvo : tokensSalvos) {
      assertEquals(TokenType.BEARER, tokenSalvo.getTokenType());
      assertEquals(false, tokenSalvo.isExpired());
      assertEquals(false, tokenSalvo.isRevoked());
      assertEquals(usuarioSalvo, tokenSalvo.getUsuario());
      assertNotNull(null, tokenSalvo);
    }
  }
}
