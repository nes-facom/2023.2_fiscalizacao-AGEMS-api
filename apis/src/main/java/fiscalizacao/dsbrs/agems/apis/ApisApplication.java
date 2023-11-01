package fiscalizacao.dsbrs.agems.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import fiscalizacao.dsbrs.agems.apis.service.FormularioService;

@SpringBootApplication
@ComponentScan("fiscalizacao.dsbrs.agems.apis")
public class ApisApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApisApplication.class, args);
  }
}
