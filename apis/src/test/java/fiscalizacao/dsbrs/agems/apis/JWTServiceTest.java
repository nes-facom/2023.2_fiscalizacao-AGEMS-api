package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.repositorio.TokenRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.JwtService;

@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class JWTServiceTest {

  @Mock
  private UsuarioRepositorio usuarioRepositorio;

  @Mock
  private TokenRepositorio tokenRepositorio;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    jwtService =
      new JwtService(
        usuarioRepositorio,
        tokenRepositorio,
        passwordEncoder,
        authenticationManager
      );
  }

  @Test
  public void testCadastrar() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setNome("Julia Alves Corazza");
    registerRequest.setEmail("juliaacorazza@gmail.com");
    registerRequest.setSenha("fiscaliza-agems");
    registerRequest.setCargo(Cargo.ANALISTA_DE_REGULACAO);
    registerRequest.setDataCriacao(LocalDateTime.of(2000, 7, 12, 0, 0, 0));

    Usuario savedUsuario = Usuario
      .builder()
      .id(1)
      .nome(registerRequest.getNome())
      .email(registerRequest.getEmail())
      .senha("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6")
      .cargo(registerRequest.getCargo())
      .funcao(Papel.USER)
      .dataCriacao(LocalDateTime.now())
      .build();
    when(usuarioRepositorio.findByEmail(registerRequest.getEmail()))
      .thenReturn(java.util.Optional.empty());
    when(usuarioRepositorio.save(any(Usuario.class))).thenReturn(savedUsuario);
    when(passwordEncoder.encode(anyString()))
      .thenReturn(
        "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
      );

    Response response = jwtService.cadastrar(registerRequest);

    assertTrue(response instanceof AuthenticationResponse);
    assertTrue(
      ((AuthenticationResponse) response).getAccessToken() instanceof String
    );
    assertTrue(((AuthenticationResponse) response).getAccessToken() != null);
    assertTrue(
      ((AuthenticationResponse) response).getRefreshToken() instanceof String
    );
    assertTrue(((AuthenticationResponse) response).getRefreshToken() != null);
    verify(usuarioRepositorio).save(any(Usuario.class));
    verify(passwordEncoder).encode(eq("fiscaliza-agems"));
    verify(tokenRepositorio).save(any(Token.class));
  }

  @Test
  public void testCadastrarShouldReturnConflict() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setDataCriacao(LocalDateTime.now());
    registerRequest.setNome("Julia Alves Corazza");
    registerRequest.setEmail("juliaacorazza@gmail.com");
    registerRequest.setSenha("fiscaliza-agems");
    registerRequest.setCargo(Cargo.ANALISTA_DE_REGULACAO);

    Usuario usuarioEncontrado = Usuario
      .builder()
      .id(1)
      .nome(registerRequest.getNome())
      .email(registerRequest.getEmail())
      .senha("$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6")
      .cargo(registerRequest.getCargo())
      .funcao(Papel.USER)
      .dataCriacao(LocalDateTime.now())
      .build();
    when(usuarioRepositorio.findByEmail(registerRequest.getEmail()))
      .thenReturn(java.util.Optional.of(usuarioEncontrado));

    Response response = jwtService.cadastrar(registerRequest);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.CONFLICT.value(), erroResponse.getStatus());
    assertEquals("Usuário já cadastrado!", erroResponse.getErro());
  }

  @Test
  public void testAutenticar() {
    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setEmail("juliaacorazza@gmail.com");
    authenticationRequest.setSenha("fiscaliza-agems");

    UserDetails userDetails = Usuario
      .builder()
      .id(1)
      .nome("Julia Alves Corazza")
      .email("juliaacorazza@gmail.com")
      .senha("$2a$10$TzZ2hgNnjdUW13juIwr/Xu/vKc.PoONxvSI.ljWJYBuKX7UVfd4Yy")
      .cargo(Cargo.ANALISTA_DE_REGULACAO)
      .funcao(Papel.USER)
      .dataCriacao(LocalDateTime.now())
      .build();

    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(usuarioRepositorio.findByEmail(eq("juliaacorazza@gmail.com")))
      .thenReturn(Optional.of((Usuario) userDetails));
    when(passwordEncoder.encode("fiscaliza-agems"))
      .thenReturn(
        "$2a$10$TzZ2hgNnjdUW13juIwr/Xu/vKc.PoONxvSI.ljWJYBuKX7UVfd4Yy"
      );

    AuthenticationResponse response = jwtService.autenticar(
      authenticationRequest
    );

    assertTrue(response instanceof AuthenticationResponse);
    assertTrue(response.getAccessToken() instanceof String);
    assertTrue(response.getAccessToken() != null);
    assertTrue(response.getRefreshToken() instanceof String);
    assertTrue(response.getRefreshToken() != null);
    verify(usuarioRepositorio).findByEmail(eq("juliaacorazza@gmail.com"));

    verify(tokenRepositorio).save(any(Token.class));
  }
}
