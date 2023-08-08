package fiscalizacao.dsbrs.agems.apis;

import static org.assertj.core.api.Assertions.assertThat;

import fiscalizacao.dsbrs.agems.apis.controller.AuthenticationController;
import fiscalizacao.dsbrs.agems.apis.controller.FormularioController;
import fiscalizacao.dsbrs.agems.apis.controller.ModeloController;
import fiscalizacao.dsbrs.agems.apis.controller.UsuarioController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

  @Autowired
  private AuthenticationController authenticationController;

  @Autowired
  private UsuarioController usuarioController;

  @Autowired
  private ModeloController modeloController;

  @Autowired
  private FormularioController formularioController;

  @Test
  void contextLoads() throws Exception {
    contextLoadsAuthenticationController();
    contextLoadsUsuarioController();
    contextLoadsModeloController();
    contextLoadsFormularioController();
  }

  @Test
  void contextLoadsAuthenticationController() throws Exception {
    assertThat(authenticationController).isNotNull();
  }

  @Test
  void contextLoadsUsuarioController() throws Exception {
    assertThat(usuarioController).isNotNull();
  }

  @Test
  void contextLoadsModeloController() throws Exception {
    assertThat(modeloController).isNotNull();
  }

  @Test
  void contextLoadsFormularioController() throws Exception {
    assertThat(formularioController).isNotNull();
  }
}
