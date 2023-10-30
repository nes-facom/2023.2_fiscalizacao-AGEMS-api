package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.repositorio.FormularioRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.TipoRespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FormularioRepositorioTest {

  @Autowired
  private FormularioRepositorio formularioRepositorio;

  @Autowired
  private QuestaoRepositorio questaoRepositorio;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private UnidadeRepositorio unidadeRepositorio;

  @Autowired
  private TipoRespostaRepositorio tipoRespostaRepositorio;

  private Usuario usuarioSalvo;
  private Modelo modeloSalvo;
  private Unidade unidadeSalva;
  private Formulario formulario;
  private Formulario formularioSalvo;

  @Order(6)
  @Test
  @BeforeEach
  public void testDeleteAll() {
    formularioRepositorio.deleteAll();
    unidadeRepositorio.deleteAll();
    usuarioRepositorio.deleteAll();
    unidadeRepositorio.deleteAll();
    modeloRepositorio.deleteAll();
  }

  @Order(1)
  @Test
  public void testSave() {
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(2,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setModeloNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setModelo(modelo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    TipoResposta tipoResposta1 = new TipoResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setResposta("Resposta1");

    List<TipoResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    TipoResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setRespostas(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    questao2.setModelo(modelo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    TipoResposta tipoResposta2 = new TipoResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setResposta("Resposta2");

    List<TipoResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    TipoResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setRespostas(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setPerguntas(questoes);

    Unidade unidade = new Unidade(0, "Unidade 01", "Rua das Neves", "Tratamento de Esgoto");
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(2).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(2).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(2).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuario(usuarioSalvo)
        .modelo(modeloSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
  }

   @Order(2)
  @Test
  public void testEditFormUsuario() {
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(5,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setModeloNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setModelo(modelo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    TipoResposta tipoResposta1 = new TipoResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setResposta("Resposta1");

    List<TipoResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    TipoResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setRespostas(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    questao2.setModelo(modelo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    TipoResposta tipoResposta2 = new TipoResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setResposta("Resposta2");

    List<TipoResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    TipoResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setRespostas(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setPerguntas(questoes);

    Unidade unidade = new Unidade(0, "Unidade 01", "Rua das Neves", "Tratamento de Esgoto");
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(5).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(5).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(5).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuario(usuarioSalvo)
        .modelo(modeloSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());

    Usuario usuarioSalvoNovo = usuarioRepositorio.findById(5).orElse(null);

    
    assertNotNull(usuarioSalvoNovo);
    Formulario formulario = formularioRepositorio.findById(5).orElse(null);
    assertNotNull(formulario);

    formulario.setUsuario(usuarioSalvoNovo);

    Formulario formularioAtualizado = formularioRepositorio.save(formulario);
    assertEquals(formularioAtualizado.getId(), formularioSalvo.getId());
    assertEquals(formulario.getId(), formularioSalvo.getId());
    assertEquals(formularioAtualizado.getId(), formulario.getId());

    assertEquals(formularioAtualizado.getModelo().getId(), formularioSalvo.getModelo().getId());
    assertEquals(formulario.getModelo().getId(), formularioSalvo.getModelo().getId());
    assertEquals(formularioAtualizado.getModelo().getId(), formulario.getModelo().getId());

    assertEquals(
      formularioAtualizado.getUnidade().getId(),
      formularioSalvo.getUnidade().getId()
    );
    assertEquals(formulario.getUnidade().getId(), formularioSalvo.getUnidade().getId());
    assertEquals(formularioAtualizado.getUnidade().getId(), formulario.getUnidade().getId());

    assertEquals(
      formularioAtualizado.getUsuario().getId(),
      formularioSalvo.getUsuario().getId()
    );
    assertEquals(
      formulario.getUsuario().getId(),
      formularioSalvo.getUsuario().getId()
    );
    assertEquals(
      formularioAtualizado.getUsuario().getId(),
      formulario.getUsuario().getId()
    );
  }

   @Order(3)
  @Test
  public void testEditFormModelo() {
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(3,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setModeloNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setModelo(modelo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    TipoResposta tipoResposta1 = new TipoResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setResposta("Resposta1");

    List<TipoResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    TipoResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setRespostas(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    questao2.setModelo(modelo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    TipoResposta tipoResposta2 = new TipoResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setResposta("Resposta2");

    List<TipoResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    TipoResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setRespostas(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setPerguntas(questoes);

    Unidade unidade = new Unidade(0, "Unidade 01", "Rua das Neves", "Tratamento de Esgoto");
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(3).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(3).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(3).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuario(usuarioSalvo)
        .modelo(modeloSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
    Modelo modeloSalvoNovo = modeloRepositorio.findById(modeloSalvo.getId()).orElse(null);
    assertNotNull(modeloSalvoNovo);
    Formulario formulario = formularioRepositorio.findById(3).orElse(null);
    assertNotNull(formulario);

    formulario.setModelo(modeloSalvoNovo);

    Formulario formularioAtualizado = formularioRepositorio.save(formulario);
    assertEquals(formularioAtualizado.getId(), formularioSalvo.getId());
    assertEquals(formulario.getId(), formularioSalvo.getId());
    assertEquals(formularioAtualizado.getId(), formulario.getId());

    assertEquals(formularioAtualizado.getModelo().getId(), formularioSalvo.getModelo().getId());
    assertEquals(formulario.getModelo().getId(), formularioSalvo.getModelo().getId());
    assertEquals(formularioAtualizado.getModelo().getId(), formulario.getModelo().getId());

    assertEquals(
      formularioAtualizado.getUnidade().getId(),
      formularioSalvo.getUnidade().getId()
    );
    assertEquals(formulario.getUnidade().getId(), formularioSalvo.getUnidade().getId());
    assertEquals(formularioAtualizado.getUnidade().getId(), formulario.getUnidade().getId());

    assertEquals(
      formularioAtualizado.getUsuario().getId(),
      formularioSalvo.getUsuario().getId()
    );
    assertEquals(
      formulario.getUsuario().getId(),
      formularioSalvo.getUsuario().getId()
    );
    assertEquals(
      formularioAtualizado.getUsuario().getId(),
      formulario.getUsuario().getId()
    );
  }

   @Order(4)
  @Test
  public void testEditFormUnidade() {
   Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(4,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setModeloNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setModelo(modelo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    TipoResposta tipoResposta1 = new TipoResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setResposta("Resposta1");

    List<TipoResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    TipoResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setRespostas(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    questao2.setModelo(modelo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    TipoResposta tipoResposta2 = new TipoResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setResposta("Resposta2");

    List<TipoResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    TipoResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setRespostas(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setPerguntas(questoes);

    Unidade unidade = new Unidade(0, "Unidade 01", "Rua das Neves", "Tratamento de Esgoto");
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(4).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(4).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(4).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuario(usuarioSalvo)
        .modelo(modeloSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
    Unidade unidadeSalvaNova = unidadeRepositorio.findById(4).orElse(null);
    assertNotNull(unidadeSalvaNova);
    Formulario formulario = formularioRepositorio.findById(4).orElse(null);
    assertNotNull(formulario);
    formulario.setUnidade(unidadeSalvaNova);

    Formulario formularioAtualizado = formularioRepositorio.save(formulario);
    assertEquals(formularioAtualizado.getId(), formularioSalvo.getId());
    assertEquals(formulario.getId(), formularioSalvo.getId());
    assertEquals(formularioAtualizado.getId(), formulario.getId());

    assertEquals(formularioAtualizado.getModelo().getId(), formularioSalvo.getModelo().getId());
    assertEquals(formulario.getModelo().getId(), formularioSalvo.getModelo().getId());
    assertEquals(formularioAtualizado.getModelo().getId(), formulario.getModelo().getId());

    assertEquals(
      formularioAtualizado.getUnidade().getId(),
      formularioSalvo.getUnidade().getId()
    );
    assertEquals(formulario.getUnidade().getId(), formularioSalvo.getUnidade().getId());
    assertEquals(formularioAtualizado.getUnidade().getId(), formulario.getUnidade().getId());

    assertEquals(
      formularioAtualizado.getUsuario().getId(),
      formularioSalvo.getUsuario().getId()
    );
    assertEquals(
      formulario.getUsuario().getId(),
      formularioSalvo.getUsuario().getId()
    );
    assertEquals(
      formularioAtualizado.getUsuario().getId(),
      formulario.getUsuario().getId()
    );
  }

  @Order(5)
  @Test
  public void testDeleteFormulario() {
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDate();
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(1,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setModeloNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<Questao> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setModelo(modelo);
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");

    Questao questaoSalva = questaoRepositorio.save(questao);

    TipoResposta tipoResposta1 = new TipoResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setResposta("Resposta1");

    List<TipoResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    TipoResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setRespostas(listRespostas);

    questoes.add(questaoSalva);

    Questao questao2 = new Questao();
    questao2.setModelo(modelo);
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");

    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    TipoResposta tipoResposta2 = new TipoResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setResposta("Resposta2");

    List<TipoResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    TipoResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setRespostas(listRespostas2);

    questoes.add(questaoSalva2);

    modeloSalvo.setPerguntas(questoes);

    Unidade unidade = new Unidade(0, "Unidade 01", "Rua das Neves", "Tratamento de Esgoto");
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(1).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(1).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(1).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuario(usuarioSalvo)
        .modelo(modeloSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
    Formulario formulario = formularioRepositorio.findById(1).orElse(null);
    assertNotNull(formulario);
    formularioRepositorio.delete(formularioSalvo);
    Formulario formularioDeletado = formularioRepositorio
      .findById(formularioSalvo.getId())
      .orElse(null);
    assertNull(formularioDeletado);
  }
}
