package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;

public class UsuarioTest {

  @Test
  public void testGetAuthorities() {
    Usuario usuario = Usuario.builder().funcao(Papel.ADMIN).build();

    Usuario usuario2 = Usuario.builder().funcao(Papel.USER).build();

    Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();
    Collection<? extends GrantedAuthority> authorities2 = usuario2.getAuthorities();

    Assertions.assertTrue(
      authorities.contains(new SimpleGrantedAuthority("ADMIN"))
    );
    Assertions.assertFalse(
      authorities.contains(new SimpleGrantedAuthority("USER"))
    );
    Assertions.assertTrue(
      authorities2.contains(new SimpleGrantedAuthority("USER"))
    );
    Assertions.assertFalse(
      authorities2.contains(new SimpleGrantedAuthority("ADMIN"))
    );
  }

  @Test
  public void testSetCargo() {
    Usuario usuario = new Usuario();

    usuario.setCargo(null);

    Assertions.assertEquals(null, usuario.getCargo());
    usuario.setCargo(Cargo.ASSESSOR_TECNICO);
    Assertions.assertEquals("Assessor Técnico", usuario.getCargo().getDescricao());
  }

  @Test
  public void testSetNome() {
    Usuario usuario = new Usuario();

    usuario.setNome("Exemplo Exemplo");

    Assertions.assertEquals("Exemplo Exemplo", usuario.getNome());
  }

  @Test
  public void testSetEmail() {
    Usuario usuario = new Usuario();

    usuario.setEmail("exemplo@exemplo.com");

    Assertions.assertEquals("exemplo@exemplo.com", usuario.getEmail());
    usuario.setEmail("exemplo.com");
    Assertions.assertEquals(null, usuario.getEmail());
  }

  @Test
  public void testSetSenha() {
    Usuario usuario = new Usuario();

    usuario.setSenha("se3nha");

    Assertions.assertEquals("se3nha", usuario.getSenha());
  }

  @Test
  public void testSetId() {
    Usuario usuario = new Usuario();

    usuario.setId(1);

    Assertions.assertEquals(1, usuario.getId());
  }

  @Test
  public void testSetDate() {
    Usuario usuario = new Usuario();

    LocalDateTime mockedTime = LocalDateTime.now();
    try (MockedStatic<LocalDateTime> utilities = Mockito.mockStatic(LocalDateTime.class)) {
        utilities.when(LocalDateTime::now).thenReturn(mockedTime);
        Assertions.assertEquals(LocalDateTime.now(), mockedTime);
        usuario.setDate();
        Assertions.assertEquals(LocalDateTime.now(), usuario.getDataCriacao());
        usuario.setDataCriacao(LocalDateTime.now());
        Assertions.assertEquals(LocalDateTime.now(), usuario.getDataCriacao());
    }
  }

  @Test
  public void testIsAccountNonExpired() {
    Usuario usuario = new Usuario();

    Assertions.assertTrue(usuario.isAccountNonExpired());
  }

  @Test
  public void testIsAccountNonLocked() {
    Usuario usuario = new Usuario();

    Assertions.assertTrue(usuario.isAccountNonLocked());
  }

  @Test
  public void testIsCredentialsNonExpired() {
    Usuario usuario = new Usuario();

    Assertions.assertTrue(usuario.isCredentialsNonExpired());
  }

  @Test
  public void testIsEnabled() {
    Usuario usuario = new Usuario();

    Assertions.assertTrue(usuario.isEnabled());
  }

  @Test
  public void testTokens() {
    Usuario usuario = new Usuario();

    Token token1 = new Token();
    Token token2 = new Token();

    usuario.setTokens(Arrays.asList(token1, token2));

    Assertions.assertEquals(2, usuario.getTokens().size());
    Assertions.assertTrue(usuario.getTokens().contains(token1));
    Assertions.assertTrue(usuario.getTokens().contains(token2));
  }

  @Test
  public void testBuilderTokens() {
    Token token1 = new Token();
    Token token2 = new Token();
    Usuario.UsuarioBuilder builder = Usuario
      .builder()
      .tokens(Arrays.asList(token1, token2));
    Usuario usuario = builder.build();

    Assertions.assertEquals(
      "Usuario.UsuarioBuilder(id=0, nome=null, email=null, senha=null, dataCriacao=null, cargo=null, funcao=null, tokens=[Token(id=null, token=null, tokenType=BEARER, revoked=false, expired=false, usuario=null), Token(id=null, token=null, tokenType=BEARER, revoked=false, expired=false, usuario=null)])",
      builder.toString()
    );
    Assertions.assertEquals(2, usuario.getTokens().size());
    Assertions.assertTrue(usuario.getTokens().contains(token1));
    Assertions.assertTrue(usuario.getTokens().contains(token2));
  }
}
