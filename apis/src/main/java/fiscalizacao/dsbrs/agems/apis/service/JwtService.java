package fiscalizacao.dsbrs.agems.apis.service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.dominio.TokenType;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.repositorio.TokenRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final UsuarioRepositorio REPOSITORIO_USUARIO;
  private final TokenRepositorio REPOSITORIO_TOKEN;
  private final PasswordEncoder CODIFICADOR_SENHA;
  private final AuthenticationManager GERENCIADOR_AUTH;

  private static final String SECRET_KEY =
    "8230A49179E733802C8ADAE9D13BBA15F33EF5E20ACE71A231EE85349CC3CC64";

  private static final long JWT_EXPIRACAO = 86400000;

  private static final long EXPIRACAO_ATUALIZADA = 604800000;

  public String extrairUsername(String token) {
    return extrairClaim(token, Claims::getSubject);
  }

  public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims CLAIMS = extractAllClaims(token);
    return claimsResolver.apply(CLAIMS);
  }

  public String gerarToken(UserDetails detalhesUsuario) {
    return gerarToken(new HashMap<>(), detalhesUsuario);
  }

  public String gerarToken(
    Map<String, Object> extraClaims,
    UserDetails detalhesUsuario
  ) {
    return buildToken(extraClaims, detalhesUsuario, JWT_EXPIRACAO);
  }

  public String gerarTokenAtualizado(UserDetails detalhesUsuario) {
    return buildToken(new HashMap<>(), detalhesUsuario, EXPIRACAO_ATUALIZADA);
  }

  private String buildToken(
    Map<String, Object> extraClaims,
    UserDetails detalhesUsuario,
    long expiracao
  ) {
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(detalhesUsuario.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + expiracao))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isTokenValid(String token, UserDetails detalhesUsuario) {
    final String USERNAME = extrairUsername(token);
    return (
      (USERNAME.equals(detalhesUsuario.getUsername())) && !tokenExpirou(token)
    );
  }

  private boolean tokenExpirou(String token) {
    return extrairExpiracao(token).before(new Date());
  }

  private Date extrairExpiracao(String token) {
    return extrairClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    byte[] bytesChave = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(bytesChave);
  }

  public Response cadastrar(RegisterRequest request) {
    if (
      request.getCargo() == null ||
      request.getEmail() == null ||
      request.getNome() == null ||
      request.getSenha() == null ||
      request.getDataCriacao() == null
    ) {
      return ErroResponse.builder().status(400).erro("Faltam dados.").build();
    }

    Usuario usuarioEncontrado = REPOSITORIO_USUARIO
      .findByEmail(request.getEmail())
      .orElse(null);
    if (usuarioEncontrado != null) {
      return ErroResponse
        .builder()
        .status(409)
        .erro("Usuário já cadastrado!")
        .build();
    }
    Usuario usuario = Usuario
      .builder()
      .nome(request.getNome())
      .email(request.getEmail())
      .senha(CODIFICADOR_SENHA.encode(request.getSenha()))
      .cargo(request.getCargo())
      .funcao(Papel.USER)
      .dataCriacao(request.getDataCriacao())
      .build();

    Usuario usuarioSalvo = REPOSITORIO_USUARIO.save(usuario);
    String jwtToken = gerarToken(usuario);
    String tokenRenovado = gerarTokenAtualizado(usuario);
    salvarTokenUsuario(usuarioSalvo, jwtToken);

    return AuthenticationResponse
      .builder()
      .accessToken(jwtToken)
      .refreshToken(tokenRenovado)
      .build();
  }

  public AuthenticationResponse autenticar(AuthenticationRequest request) {
    GERENCIADOR_AUTH.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getSenha()
      )
    );
    Usuario usuario =
      (REPOSITORIO_USUARIO.findByEmail(request.getEmail())).orElseThrow();
    String jwtToken = gerarToken(usuario);
    String refreshToken = gerarTokenAtualizado(usuario);
    revocarTodosTokensUsuarios(usuario);
    salvarTokenUsuario(usuario, jwtToken);
    return AuthenticationResponse
      .builder()
      .accessToken(jwtToken)
      .refreshToken(refreshToken)
      .build();
  }

  private void salvarTokenUsuario(Usuario user, String jwtToken) {
    Token token = Token
      .builder()
      .usuario(user)
      .token(jwtToken)
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .build();
    REPOSITORIO_TOKEN.save(token);
  }

  private void revocarTodosTokensUsuarios(Usuario usuario) {
    List<Token> tokensValidos = REPOSITORIO_TOKEN.findAllValidTokenByUser(
      usuario.getId()
    );
    if (tokensValidos.isEmpty()) return;
    tokensValidos.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    REPOSITORIO_TOKEN.saveAll(tokensValidos);
  }

  public Response renovarToken(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws IOException, StreamWriteException, DatabindException, java.io.IOException {
    final String AUTH_HEADER = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String REFRESH_TOKEN;
    final String EMAIL_HEADER = (String) request.getAttribute("EMAIL_USUARIO");
    final String EMAIL_USUARIO = EMAIL_HEADER
      .substring(EMAIL_HEADER.indexOf(":") + 1)
      .trim();
    REFRESH_TOKEN = AUTH_HEADER.substring(7);
    if (EMAIL_USUARIO != null) {
      Usuario usuario =
        this.REPOSITORIO_USUARIO.findByEmail(EMAIL_USUARIO).orElseThrow();
      if (isTokenValid(REFRESH_TOKEN, usuario)) {
        String tokenDeAcesso = gerarToken(usuario);
        revocarTodosTokensUsuarios(usuario);
        salvarTokenUsuario(usuario, tokenDeAcesso);
        AuthenticationResponse respostaAutenticacao = AuthenticationResponse
          .builder()
          .accessToken(tokenDeAcesso)
          .refreshToken(REFRESH_TOKEN)
          .build();
        return respostaAutenticacao;
      }
    }
    return ErroResponse
      .builder()
      .status(404)
      .erro("E-mail não encontrado.")
      .build();
  }
}
