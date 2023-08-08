package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fiscalizacao.dsbrs.agems.apis.dominio.TokenType;
import org.junit.jupiter.api.Test;

public class TokenTypeTest {

  @Test
  public void testTokenTypeValue() {
    TokenType tokenType = TokenType.BEARER;

    assertEquals("BEARER", tokenType.name());
  }

  @Test
  public void testTokenTypeEquality() {
    TokenType tokenType1 = TokenType.BEARER;
    TokenType tokenType2 = TokenType.BEARER;

    assertSame(tokenType1, tokenType2);
  }

  @Test
  public void testTokenTypeToString() {
    TokenType tokenType = TokenType.BEARER;

    // Assert that the toString() method returns the expected value
    assertEquals("BEARER", tokenType.toString());
  }

  @Test
  public void testTokenTypeOrdinal() {
    TokenType tokenType = TokenType.BEARER;

    // Assert that the ordinal() method returns the expected value
    assertEquals(0, tokenType.ordinal());
  }
}
