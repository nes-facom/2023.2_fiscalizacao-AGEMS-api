package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.dominio.TokenType;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import org.junit.jupiter.api.Test;

public class TokenTest {

  @Test
  public void testAllArgsConstructor() {
    Token token = new Token(
      1,
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE",
      TokenType.BEARER,
      false,
      false,
      new Usuario()
    );
    assertEquals(TokenType.BEARER, token.getTokenType());
    assertEquals(false, token.isExpired());
    assertEquals(false, token.isRevoked());
    assertEquals(new Usuario(), token.getUsuario());
    assertNotNull(null, token);
  }

  @Test
  public void testNoArgsConstructor() {
    Token token = new Token();
    token.setExpired(false);
    token.setRevoked(false);
    token.setTokenType(TokenType.BEARER);
    token.setUsuario(new Usuario());
    token.setId(1);
    token.setToken(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE"
    );
    assertEquals(TokenType.BEARER, token.getTokenType());
    assertEquals(false, token.isExpired());
    assertEquals(false, token.isRevoked());
    assertEquals(new Usuario(), token.getUsuario());
    assertEquals(1, token.getId());
    assertEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE",
      token.getToken()
    );
    assertNotNull(null, token);
  }

  @Test
  public void testTokenExpired() {
    Token.TokenBuilder builder = Token
      .builder()
      .id(1)
      .token(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE"
      )
      .expired(true);
    Token token = builder.build();
    Token tokenNovo = Token
      .builder()
      .token(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
      )
      .expired(false)
      .build();
    assertEquals(1, token.getId());
    assertEquals(true, token.isExpired());
    assertEquals(false, tokenNovo.isExpired());
    assertNotEquals(token, tokenNovo);
    assertNotEquals(token.isExpired(), tokenNovo.isExpired());
    assertEquals("Token.TokenBuilder(id=1, token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE, tokenType$value=null, revoked=false, expired=true, usuario=null)",builder.toString());
  }

  @Test
  public void testTokenRevoked() {
    Token token = Token
      .builder()
      .token(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE"
      )
      .revoked(true)
      .build();
    Token tokenNovo = Token
      .builder()
      .token(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
      )
      .revoked(false)
      .build();

    assertEquals(false, tokenNovo.isRevoked());
    assertNotEquals(token, tokenNovo);
    assertNotEquals(token.isRevoked(), tokenNovo.isRevoked());

    assertEquals(true, token.isRevoked());
  }
}
