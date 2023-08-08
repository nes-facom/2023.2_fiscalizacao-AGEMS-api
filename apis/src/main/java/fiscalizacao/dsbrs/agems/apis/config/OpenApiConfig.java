package fiscalizacao.dsbrs.agems.apis.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
      .name("BEARER")
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT");
    SecurityRequirement securityRequirement = new SecurityRequirement()
      .addList("BEARER");

    return new OpenAPI()
      .info(new Info().title("Serviços Fiscaliza-Agems"))
      .addSecurityItem(securityRequirement)
      .components(
        new Components().addSecuritySchemes("BEARER", securityScheme)
      );
  }
}
