package fiscalizacao.dsbrs.agems.apis;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;

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
  public void testSetEmail() {
    Usuario usuario = new Usuario();

    usuario.setEmail("exemplo@exemplo.com");

    Assertions.assertEquals("exemplo@exemplo.com", usuario.getEmail());
    usuario.setEmail("exemplo.com");
    Assertions.assertEquals(null, usuario.getEmail());
  }
}
