package fiscalizacao.dsbrs.agems.apis.config;

import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UsuarioRepositorio REPOSITORIO_USUARIO;

  @Bean
  public UserDetailsService userDetailsService() {
    return username ->
      REPOSITORIO_USUARIO
        .findByEmail(username)
        .orElseThrow(() ->
          new UsernameNotFoundException("Usuário não encontrado")
        );
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provedorAutenticacao = new DaoAuthenticationProvider();
    provedorAutenticacao.setUserDetailsService(userDetailsService());
    provedorAutenticacao.setPasswordEncoder(passwordEncoder());
    return provedorAutenticacao;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationProvider authenticationProvider
  ) {
    return new ProviderManager(
      Collections.singletonList(authenticationProvider)
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
