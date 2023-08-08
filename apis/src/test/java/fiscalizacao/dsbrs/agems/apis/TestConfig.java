package fiscalizacao.dsbrs.agems.apis;

import org.junit.ClassRule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.testcontainers.containers.PostgreSQLContainer;



@Configuration
@EnableAutoConfiguration
public class TestConfig {

  @ClassRule
  public static PostgreSQLContainer<?> postgreSQLContainer = PostgresContainerAgems.getInstance();

  @Bean
  public HandlerMappingIntrospector customMvcHandlerMappingIntrospector() {
    return new HandlerMappingIntrospector();
  }
}
