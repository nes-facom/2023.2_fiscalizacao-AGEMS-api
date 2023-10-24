package fiscalizacao.dsbrs.agems.apis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Imagem;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Token;
import fiscalizacao.dsbrs.agems.apis.dominio.TokenType;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.requests.AuthenticationRequest;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ImagemAcaoRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ImagemRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.UnidadeRequest;
import fiscalizacao.dsbrs.agems.apis.requests.UsuarioEditRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResumoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ImagemResponse;
import fiscalizacao.dsbrs.agems.apis.responses.InfoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloFormResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.responses.QuestaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.RespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.TipoRespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UsuarioFormResponse;
import jakarta.annotation.Nonnull;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class DataTest {

  @Test
  public void equalsRequests() {
    EqualsVerifier
      .forClass(AuthenticationRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(FormularioRegisterRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(FormularioRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ImagemAcaoRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ImagemRegisterRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloEditRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloRegisterRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();

    EqualsVerifier
      .forClass(QuestaoEditRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();

    EqualsVerifier
      .forClass(QuestaoRegisterRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(RegisterRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(RespostaEditRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(RespostaRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(TipoRespostaRegisterRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();

    EqualsVerifier
      .forClass(TipoRespostaEditRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(UnidadeRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();

    EqualsVerifier
      .forClass(UsuarioEditRequest.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
  }

  @Test
  public void equalsResponses() {
    EqualsVerifier
      .forClass(AuthenticationResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ErroResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(FormularioAcaoResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(FormularioResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(FormularioResumoResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ImagemResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(InfoResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloAcaoResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloFormResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloListResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(ModeloResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(QuestaoResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(Response.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify();
    EqualsVerifier
      .forClass(RespostaResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(TipoRespostaResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(UnidadeResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(UsuarioFormResponse.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
  }

  @Test
  public void equalsDominio() {
    EqualsVerifier
      .forClass(Unidade.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    Token token1 = new Token(
      1,
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMyMjE0OCwiZXhwIjoxNjg1NDA4NTQ4fQ.r_YrE9VFhrv6iyBgsCu-tb-p2PfLQzQtSbklVRfHgFE",
      TokenType.BEARER,
      false,
      false,
      new Usuario()
    );
    Token token2 = new Token(
      2,
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E",
      TokenType.BEARER,
      false,
      false,
      new Usuario()
    );
    List<Token> list1 = new ArrayList<>();
    list1.add(token1);
    List<Token> list2 = new ArrayList<>();
    list2.add(token2);
    Usuario user1 = new Usuario(
      1,
      "Julia Corazza",
      "jalves@gamil.com",
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6",
      LocalDateTime.now(),
      Cargo.COORDENADOR,
      Papel.USER,
      list1
    );
    Usuario user2 = new Usuario(
      1,
      "Luiza Corazza",
      "lalves@gamil.com",
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv7",
      LocalDateTime.now(),
      Cargo.COORDENADOR,
      Papel.USER,
      list2
    );

    EqualsVerifier
      .forClass(Formulario.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withPrefabValues(Usuario.class, user1, user2)
      .withPrefabValues(Token.class, token1, token2)
      .withPrefabValues(List.class, list1, list2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    Formulario form1 = new Formulario(
      1,
      user1,
      new Modelo(),
      new Unidade(),
      LocalDateTime.now(),
      "obs"
    );
    Formulario form2 = new Formulario(
      2,
      user2,
      new Modelo(),
      new Unidade(),
      LocalDateTime.now(),
      "obs"
    );
    EqualsVerifier
      .forClass(Imagem.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withPrefabValues(Formulario.class, form1, form2)
      .withPrefabValues(Usuario.class, user1, user2)
      .withPrefabValues(Token.class, token1, token2)
      .withPrefabValues(List.class, list1, list2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    Modelo modelo1 = new Modelo();
    modelo1.setModeloNome("Modelo 1");
    List<Questao> questoes1 = new ArrayList<>();

    Questao questao1 = new Questao();
    Questao questao2 = new Questao();

    questoes1.add(questao1);
    

    modelo1.setPerguntas(questoes1);
    Modelo modelo2 = new Modelo();
    modelo2.setModeloNome("Modelo 1");
    List<Questao> questoes2 = new ArrayList<>();

    
    questoes2.add(questao2);
    

    modelo2.setPerguntas(questoes2);

    EqualsVerifier
      .forClass(Modelo.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .suppress( Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .withPrefabValues(Modelo.class, modelo1, modelo2)
      .withPrefabValues(Questao.class, questao1, questao2)
      .withPrefabValues(List.class, questoes1, questoes2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(Papel.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(Questao.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .suppress( Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .withPrefabValues(Modelo.class, modelo1, modelo2)
      .withPrefabValues(Questao.class, questao1, questao2)
      .withPrefabValues(List.class, questoes1, questoes2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(Resposta.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withPrefabValues(Formulario.class, form1, form2)
      .withPrefabValues(Usuario.class, user1, user2)
      .withPrefabValues(Token.class, token1, token2)
      .withPrefabValues(List.class, list1, list2)
      .suppress( Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(TipoResposta.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .suppress( Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .withPrefabValues(Modelo.class, modelo1, modelo2)
      .withPrefabValues(Questao.class, questao1, questao2)
      .withPrefabValues(List.class, questoes1, questoes2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(Token.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withPrefabValues(Usuario.class, user1, user2)
      .withPrefabValues(Token.class, token1, token2)
      .withPrefabValues(List.class, list1, list2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(TokenType.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
    EqualsVerifier
      .forClass(Usuario.class)
      .withRedefinedSuperclass()
      .suppress(Warning.STRICT_INHERITANCE)
      .suppress(Warning.NONFINAL_FIELDS)
      .suppress(Warning.SURROGATE_KEY)
      .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
      .withPrefabValues(Usuario.class, user1, user2)
      .withPrefabValues(Token.class, token1, token2)
      .withPrefabValues(List.class, list1, list2)
      .withIgnoredAnnotations(Nonnull.class)
      .verify();
      
  }
}
