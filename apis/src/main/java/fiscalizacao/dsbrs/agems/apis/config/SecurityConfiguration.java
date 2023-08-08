package fiscalizacao.dsbrs.agems.apis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter JWT_AUTH_FILTER;
  private final PatternFilter PATTERN_FILTER;
  private final AuthenticationProvider PROVEDOR_AUTENTICACAO;
  private final RequestBodyCaptureFilter CAPTURA_CORPO_FILTER;
  private final LogoutHandler LOGOUT_HANDLER;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .csrf()
      .disable()
      .authorizeHttpRequests()
      .requestMatchers("/usuarios/**", "/swagger-ui/**", "/api-docs/**", "/api-docs.yaml**")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authenticationProvider(PROVEDOR_AUTENTICACAO)
      .addFilterBefore(
        JWT_AUTH_FILTER,
        UsernamePasswordAuthenticationFilter.class
      )
      .addFilterAfter(CAPTURA_CORPO_FILTER, JwtAuthenticationFilter.class)
      .addFilterAfter(PATTERN_FILTER, RequestBodyCaptureFilter.class)
      .logout()
      .logoutUrl("/usuarios/logout")
      .addLogoutHandler(LOGOUT_HANDLER)
      .logoutSuccessHandler((request, response, authentication) ->
        SecurityContextHolder.clearContext()
      );

    return http.build();
  }
}
