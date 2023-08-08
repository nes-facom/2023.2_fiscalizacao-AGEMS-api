package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.InfoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuarioServiceTest {

  @Mock
  private PasswordEncoder codificadorSenha;

  @Mock
  private UsuarioRepositorio repositorioUsuario;

  @Mock
  private HttpServletRequest request;

  @InjectMocks
  private UsuarioService usuarioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    usuarioService = new UsuarioService(codificadorSenha, repositorioUsuario);
  }

  @Test
  public void testGetInfUserTokenWithValidIdCodeShouldReturnInfoResponse() {
    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setNome("Julia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    usuario.setFuncao(Papel.USER);

    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("Email: " + userEmail);
    when(repositorioUsuario.findByEmail(userEmail))
      .thenReturn(Optional.of(usuario));

    Response response = usuarioService.getInfUserToken(request);

    assertTrue(response instanceof InfoResponse);
    InfoResponse infoResponse = (InfoResponse) response;
    assertEquals("Julia Alves Corazza", infoResponse.getNome());
    assertEquals(userEmail, infoResponse.getEmail());
    assertEquals("Analista de Regulação", infoResponse.getCargo());
    assertEquals(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6",
      infoResponse.getSenha()
    );

    verify(repositorioUsuario).findByEmail(userEmail);
  }

  @Test
  public void testGetInfUserTokenWithValidStringCodeShouldReturnInfoResponse() {
    String userEmail = "juliaacorazza@gmail.com";
    List<Usuario> usuarios = new ArrayList<>();
    Usuario usuario = new Usuario();
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    usuario.setFuncao(Papel.USER);
    usuarios.add(usuario);
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("Email: " + userEmail);

    when(repositorioUsuario.findByEmail(userEmail))
      .thenReturn(Optional.of(usuario));

    Response response = usuarioService.getInfUserToken(request);

    assertTrue(response instanceof InfoResponse);
    InfoResponse infoResponse = (InfoResponse) response;
    assertEquals("Júlia Alves Corazza", infoResponse.getNome());
    assertEquals(userEmail, infoResponse.getEmail());
    assertEquals("Analista de Regulação", infoResponse.getCargo());
    assertEquals(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6",
      infoResponse.getSenha()
    );

    verify(repositorioUsuario).findByEmail(userEmail);
  }

  @Test
  public void testAtualizarDadosUpdateNameAndPassword() {
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_HEADER: juliaacorazza@gmail.com");

    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    when(repositorioUsuario.findByEmail("juliaacorazza@gmail.com"))
      .thenReturn(Optional.of(usuario));

    when(
      codificadorSenha.matches(
        "senha11#",
        "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
      )
    )
      .thenReturn(true);
    when(codificadorSenha.encode("senha12#"))
      .thenReturn(
        "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv9"
      );

    Response response = usuarioService.atualizarDados(
      request,
      "J\u00FAlia Alves Corazza",
      "senha11#",
      "senha12#"
    );

    assertEquals(
      "J\u00FAlia Alves Corazza",
      ((InfoResponse) response).getNome()
    );
    assertEquals(
      "juliaacorazza@gmail.com",
      ((InfoResponse) response).getEmail()
    );
    assertEquals(
      "Analista de Regula\u00E7\u00E3o",
      ((InfoResponse) response).getCargo()
    );
    assertEquals(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv9",
      ((InfoResponse) response).getSenha()
    );


    verify(repositorioUsuario).findByEmail("juliaacorazza@gmail.com");
    verify(repositorioUsuario).save(usuario);

    verify(codificadorSenha)
      .matches(
        "senha11#",
        "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
      );
    verify(codificadorSenha).encode("senha12#");
  }

  @Test
  public void testAtualizarDadosNotUpdateNameAndPassword() {
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_HEADER: juliaacorazza@gmail.com");

    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    when(repositorioUsuario.findByEmail("juliaacorazza@gmail.com"))
      .thenReturn(Optional.of(usuario));

    when(
      codificadorSenha.matches(
        "senha11#",
        "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
      )
    )
      .thenReturn(false);

    Response response = usuarioService.atualizarDados(
      request,
      "J\u00FAlia Alves Corazza",
      "senha11#",
      "senha12#"
    );

    assertEquals(
      "Senha fornecida incorreta",
      ((ErroResponse) response).getErro()
    );
    assertEquals(
      400,
      ((ErroResponse) response).getStatus()
    );


    verify(repositorioUsuario).findByEmail("juliaacorazza@gmail.com");

    verify(codificadorSenha)
      .matches(
        "senha11#",
        "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
      );
    
  }

  @Test
  public void testAtualizaDadosNome(){
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_HEADER: juliaacorazza@gmail.com");

    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    when(repositorioUsuario.findByEmail("juliaacorazza@gmail.com"))
      .thenReturn(Optional.of(usuario));
      Response response = usuarioService.atualizarDados(
      request,
      "Julia Alves Corazza",null, null
    );
    assertEquals(
      "Julia Alves Corazza",
      ((InfoResponse) response).getNome()
    );
    assertEquals(
      "juliaacorazza@gmail.com",
      ((InfoResponse) response).getEmail()
    );
    assertEquals(
      "Analista de Regula\u00E7\u00E3o",
      ((InfoResponse) response).getCargo()
    );
    assertEquals(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6",
      ((InfoResponse) response).getSenha()
    );

  }
  @Test
  public void testAtualizarDadosWithInvalidStringCodeUsuarioNotFound() {
    String email = "email@example.com";
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: " + email);
    when(repositorioUsuario.findByEmail(email)).thenReturn(Optional.empty());

    Response response = usuarioService.atualizarDados(
      request,
      "New Name",
      "currentPassword",
      "newPassword"
    );

    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals("Usuário não existe.", ((ErroResponse) response).getErro());

    verify(codificadorSenha, never()).encode(anyString());
    verify(repositorioUsuario, never()).save(any(Usuario.class));
  }

  @Test
  public void testAtualizarDadosWithInvalidIdCodeUsuarioNotFound() {
    String userEmail = "email@gmail.com";
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: " + userEmail);
    when(repositorioUsuario.findByEmail(userEmail))
      .thenReturn(Optional.empty());

    Response response = usuarioService.atualizarDados(
      request,
      "New Name",
      "currentPassword",
      "newPassword"
    );

    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals("Usuário não existe.", ((ErroResponse) response).getErro());

    verify(codificadorSenha, never()).encode(anyString());
    verify(repositorioUsuario, never()).save(any(Usuario.class));
  }

  @Test
  public void testGetInfUserTokenWithInvalidIdCodeShouldReturnErroResponse() {
    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setNome("Julia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    usuario.setFuncao(Papel.USER);

    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("Email: " + userEmail);
    when(repositorioUsuario.findByEmail(userEmail))
      .thenReturn(Optional.empty());

    Response response = usuarioService.getInfUserToken(request);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.NOT_FOUND.value(), erroResponse.getStatus());
    assertEquals("Usuário não existe.", erroResponse.getErro());

    verify(repositorioUsuario).findByEmail(userEmail);
  }

  @Test
  public void testGetInfUserTokenWithInvalidStringCodeShouldReturnErroResponse() {
    String userEmail = "jorge@gmail.com";
    List<Usuario> usuarios = new ArrayList<>();

    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("Email: " + userEmail);
    when(repositorioUsuario.findByEmail(userEmail))
      .thenReturn(Optional.empty());
    assertEquals(0, usuarios.size());
    Response response = usuarioService.getInfUserToken(request);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.NOT_FOUND.value(), erroResponse.getStatus());
    assertEquals("Usuário não existe.", erroResponse.getErro());

    verify(repositorioUsuario).findByEmail(userEmail);
  }

  @Test
  public void testAtualizarDados_NoNameAndPasswordUpdate() {
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("email:test@example.com");
    when(repositorioUsuario.findByEmail("test@example.com"))
      .thenReturn(Optional.of(new Usuario()));
   

    Response response = usuarioService.atualizarDados(
      request,
      null,
      null,
      null
    );

    assertEquals(
      "Não é possível atualizar a senha nem o nome",
      ((ErroResponse) response).getErro()
    );

    verifyNoInteractions(codificadorSenha);
    verify(repositorioUsuario).findByEmail("test@example.com");
  }

  @Test
  public void testAtualizarDados_ExistingUsuario_SuccessfullyUpdated() {
    // Mock the HttpServletRequest
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("EMAIL_USUARIO: example@example.com");

    
     Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setNome("Júlia Alves Corazza");
     usuario.setEmail("example@example.com");
    usuario.setCargo("Analista de Regulação");
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
   
    usuario.setSenha("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6");

    // Mock the repository method
    when(repositorioUsuario.findByEmail("example@example.com"))
      .thenReturn(Optional.of(usuario));

    // Mock the passwordEncoder
    when(codificadorSenha.matches("senha123#", "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"))
      .thenReturn(true);
    when(codificadorSenha.encode("senha124#"))
      .thenReturn("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar2wd3");

    Response response = usuarioService.atualizarDados(
      request,
      null,
      "senha123#",
      "senha124#"
    );

    // Verify the repository save method was called
    verify(repositorioUsuario).save(usuario);

    InfoResponse expectedResponse = InfoResponse
      .builder()
      .id(usuario.getId())
      .nome(usuario.getNome())
      .email(usuario.getEmail())
      .cargo(usuario.getCargo())
      .senha("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar2wd3")
      .build();
    assertEquals(expectedResponse, response);
  }
}
