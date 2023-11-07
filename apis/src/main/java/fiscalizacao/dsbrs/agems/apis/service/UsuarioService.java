package fiscalizacao.dsbrs.agems.apis.service;

import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.InfoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

  private final PasswordEncoder CODIFICADOR_SENHA;
  private final UsuarioRepositorio REPOSITORIO_USUARIO;

  public Response atualizarDados(
    HttpServletRequest request,
    String nome,
    String senha,
    String senhaNova
  ) {
    final String EMAIL_HEADER = (String) request.getAttribute("EMAIL_USUARIO");
    final String EMAIL_USUARIO = EMAIL_HEADER
      .substring(EMAIL_HEADER.indexOf(":") + 1)
      .trim();
    Usuario usuario = REPOSITORIO_USUARIO
      .findByEmail(EMAIL_USUARIO)
      .orElse(null);
    if (usuario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Usuário não existe.")
        .build();
    }
    
    if (
      (nome == null || (nome.isEmpty() || nome.isBlank())) &&
      (senhaNova == null || (senhaNova.isEmpty() || senhaNova.isBlank()))
    ) {
      return ErroResponse
        .builder()
        .erro("Não é possível atualizar a senha nem o nome")
        .status(400)
        .build();
    } else if (
      (nome == null || nome.isEmpty() || nome.isBlank()) &&
      (senhaNova != null && !senhaNova.isEmpty() && !senhaNova.isBlank())
    ) {
      if (senha == null || senha.isEmpty() || senha.isBlank()) {
        return ErroResponse
          .builder()
          .erro("Senha atual não fornecida")
          .status(400)
          .build();
      }
      if (CODIFICADOR_SENHA.matches(senha, usuario.getSenha())) {
        usuario.setSenha(CODIFICADOR_SENHA.encode(senhaNova));
        REPOSITORIO_USUARIO.save(usuario);
        return InfoResponse
          .builder()
          .id(usuario.getId())
          .nome(usuario.getNome())
          .email(usuario.getEmail())
          .cargo(usuario.getCargo().getDescricao())
          .senha(usuario.getSenha())
          .build();
      } else {
        return ErroResponse
          .builder()
          .erro("Senha fornecida incorreta")
          .status(400)
          .build();
      }
    } else if (
      (nome != null && !nome.isEmpty() && !nome.isBlank()) &&
      (senhaNova == null || senhaNova.isEmpty() || senhaNova.isBlank())
    ) {
      usuario.setNome(nome);
      REPOSITORIO_USUARIO.save(usuario);
      return InfoResponse
        .builder()
        .id(usuario.getId())
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .cargo(usuario.getCargo().getDescricao())
        .senha(usuario.getSenha())
        .build();
    } else {
      if (nome.isBlank() || senhaNova.isBlank()) {
        return ErroResponse
          .builder()
          .erro("Não é possível atualizar os dados")
          .status(400)
          .build();
      }

      if (senha == null || senha.isEmpty() || senha.isBlank()) {
        return ErroResponse
          .builder()
          .erro("Senha atual não fornecida")
          .status(400)
          .build();
      }
      usuario.setNome(nome);
      if (CODIFICADOR_SENHA.matches(senha, usuario.getSenha())) {
        usuario.setSenha(CODIFICADOR_SENHA.encode(senhaNova));
        REPOSITORIO_USUARIO.save(usuario);
        return InfoResponse
          .builder()
          .id(usuario.getId())
          .nome(usuario.getNome())
          .email(usuario.getEmail())
          .cargo(usuario.getCargo().getDescricao())
          .senha(usuario.getSenha())
          .build();
      } else {
        return ErroResponse
          .builder()
          .erro("Senha fornecida incorreta")
          .status(400)
          .build();
      }
    }
  }

  public Response getInfUserToken(HttpServletRequest request) {
    final String EMAIL_HEADER = (String) request.getAttribute("EMAIL_USUARIO");
    final String EMAIL_USUARIO = EMAIL_HEADER
      .substring(EMAIL_HEADER.indexOf(":") + 1)
      .trim();
    Usuario usuario = REPOSITORIO_USUARIO
      .findByEmail(EMAIL_USUARIO)
      .orElse(null);
    if (usuario != null) {
      return InfoResponse
        .builder()
        .id(usuario.getId())
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .cargo(usuario.getCargo().getDescricao())
        .senha(usuario.getSenha())
        .dataCriacao(usuario.getDataCriacao())
        .build();
    }
    return ErroResponse
      .builder()
      .status(404)
      .erro("Usuário não existe.")
      .build();
  }
}
