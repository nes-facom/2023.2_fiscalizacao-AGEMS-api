package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.repositorio.AlternativaRespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.FormularioRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FormularioRepositorioTest {

  @Autowired
  private FormularioRepositorio formularioRepositorio;

  @Autowired
  private QuestaoRepositorio questaoRepositorio;
  
  @Autowired
  private QuestaoModeloRepositorio questaoModeloRepositorio;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private ModeloRepositorio modeloRepositorio;

  @Autowired
  private UnidadeRepositorio unidadeRepositorio;

  @Autowired
  private AlternativaRespostaRepositorio tipoRespostaRepositorio;

  private Usuario usuarioSalvo;
  private Modelo modeloSalvo;
  private Unidade unidadeSalva;
  private Formulario formulario;
  private Formulario formularioSalvo;

  @Order(1)
  @Test
  public void testSave() {
    LocalDateTime now = LocalDateTime.now();
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDataCriacao(now);
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(1,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<QuestaoModelo> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");
    Questao questaoSalva = questaoRepositorio.save(questao);
    
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setQuestao(questao);
    questaoModelo.setModelo(modeloSalvo);
    questaoModeloRepositorio.save(questaoModelo);

    AlternativaResposta tipoResposta1 = new AlternativaResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setDescricao("Resposta1");

    List<AlternativaResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    AlternativaResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setAlternativasResposta(listRespostas);

    questoes.add(questaoModelo);

    Questao questao2 = new Questao();
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");
    Questao questaoSalva2 = questaoRepositorio.save(questao2);
    
    QuestaoModelo questao2Modelo = new QuestaoModelo();
    questao2Modelo.setQuestao(questao2);
    questao2Modelo.setModelo(modeloSalvo);
    questaoModeloRepositorio.save(questao2Modelo);

    AlternativaResposta tipoResposta2 = new AlternativaResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setDescricao("Resposta2");

    List<AlternativaResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    AlternativaResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setAlternativasResposta(listRespostas2);

    questoes.add(questao2Modelo);

    modeloSalvo.setQuestoes(questoes);

    Unidade unidade = Unidade.builder()
        .id(UUID.fromString("ASIK003SDKASF0A"))
        .nome("Unidade 01")
        .endereco("Rua das Neves")
        .tipo("Tratamento de Esgoto")
        .build();
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuarioCriacao(usuarioSalvo)
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
    LocalDateTime now = LocalDateTime.now();
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha(
      "$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu"
    );
    usuario.setDataCriacao(now);
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(1,usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<QuestaoModelo> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");
    Questao questaoSalva = questaoRepositorio.save(questao);
    
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setModelo(modelo);
    questaoModelo.setQuestao(questao);
    questaoModeloRepositorio.save(questaoModelo);

    AlternativaResposta tipoResposta1 = new AlternativaResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setDescricao("Resposta1");

    List<AlternativaResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    AlternativaResposta tipoRespostaSalva = tipoRespostaRepositorio
      .findById(tipoResposta1.getId())
      .orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setAlternativasResposta(listRespostas);

    questoes.add(questaoModelo);

    Questao questao2 = new Questao();
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");
    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    QuestaoModelo questao2Modelo = new QuestaoModelo();
    questao2Modelo.setModelo(modelo);
    questao2Modelo.setQuestao(questao2);
    questaoModeloRepositorio.save(questao2Modelo);
    
    AlternativaResposta tipoResposta2 = new AlternativaResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setDescricao("Resposta2");

    List<AlternativaResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    AlternativaResposta tipoRespostaSalva2 = tipoRespostaRepositorio
      .findById(tipoResposta2.getId())
      .orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setAlternativasResposta(listRespostas2);

    questoes.add(questao2Modelo);

    modeloSalvo.setQuestoes(questoes);
    
    Unidade unidade = Unidade.builder()
        .id(UUID.fromString("38c7b15b-82cf-4606-8789-3be6336606f8"))
        .nome("Unidade 01")
        .endereco("Rua das Neves")
        .tipo("Tratamento de Esgoto")
        .build();
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuarioCriacao(usuarioSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());

    Usuario usuarioSalvoNovo = usuarioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);

    
    assertNotNull(usuarioSalvoNovo);
    Formulario formulario = formularioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(formulario);

    formulario.setUsuarioCriacao(usuarioSalvoNovo);

    Formulario formularioAtualizado = formularioRepositorio.save(formulario);
    assertEquals(formularioAtualizado.getId(), formularioSalvo.getId());
    assertEquals(formulario.getId(), formularioSalvo.getId());
    assertEquals(formularioAtualizado.getId(), formulario.getId());

    assertEquals(
      formularioAtualizado.getUnidade().getId(),
      formularioSalvo.getUnidade().getId()
    );
    assertEquals(formulario.getUnidade().getId(), formularioSalvo.getUnidade().getId());
    assertEquals(formularioAtualizado.getUnidade().getId(), formulario.getUnidade().getId());

    assertEquals(
      formularioAtualizado.getUsuarioCriacao().getId(),
      formularioSalvo.getUsuarioCriacao().getId()
    );
    assertEquals(
      formulario.getUsuarioCriacao().getId(),
      formularioSalvo.getUsuarioCriacao().getId()
    );
    assertEquals(
      formularioAtualizado.getUsuarioCriacao().getId(),
      formulario.getUsuarioCriacao().getId()
    );
  }

   @Order(3)
  @Test
  public void testEditFormModelo() { 
     LocalDateTime now = LocalDateTime.now();
     Usuario usuario = new Usuario();
     usuario.setNome("J\u00FAlia Alves Corazza");
     usuario.setEmail("juliaacorazza@gmail.com");
     usuario.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
     usuario.setDataCriacao(now);
     usuario.setFuncao(Papel.USER);
     usuario.setCargo(Cargo.COORDENADOR);
     usuarioSalvo = usuarioRepositorio.save(usuario);
     assertEquals(1, usuarioSalvo.getId());
     Modelo modelo = new Modelo();
     modelo.setNome("Modelo 01");
     modeloSalvo = modeloRepositorio.save(modelo);

     List<QuestaoModelo> questoes = new ArrayList<>();

     Questao questao = new Questao();
     questao.setObjetiva(false);
     questao.setPergunta("Pergunta1");
     questao.setPortaria("1234567");
     Questao questaoSalva = questaoRepositorio.save(questao);
     
     QuestaoModelo questaoModelo = new QuestaoModelo();
     questaoModelo.setModelo(modelo);
     questaoModelo.setQuestao(questao);
     questaoModeloRepositorio.save(questaoModelo);

     AlternativaResposta tipoResposta1 = new AlternativaResposta();
     tipoResposta1.setQuestao(questaoSalva);
     tipoResposta1.setDescricao("Resposta1");

     List<AlternativaResposta> listRespostas = new ArrayList<>();
     tipoRespostaRepositorio.save(tipoResposta1);
     AlternativaResposta tipoRespostaSalva = tipoRespostaRepositorio.findById(tipoResposta1.getId()).orElse(null);
     listRespostas.add(tipoRespostaSalva);

     questaoSalva.setAlternativasResposta(listRespostas);

     questoes.add(questaoModelo);

     Questao questao2 = new Questao();
     questao2.setObjetiva(false);
     questao2.setPergunta("Pergunta2");
     questao2.setPortaria("1234567421");
     Questao questaoSalva2 = questaoRepositorio.save(questao2);
     
     QuestaoModelo questao2Modelo = new QuestaoModelo();
     questao2Modelo.setModelo(modelo);
     questao2Modelo.setQuestao(questao2);
     questaoModeloRepositorio.save(questao2Modelo);

     AlternativaResposta tipoResposta2 = new AlternativaResposta();
     tipoResposta2.setQuestao(questaoSalva2);
     tipoResposta2.setDescricao("Resposta2");

     List<AlternativaResposta> listRespostas2 = new ArrayList<>();
     tipoRespostaRepositorio.save(tipoResposta2);
     AlternativaResposta tipoRespostaSalva2 = tipoRespostaRepositorio.findById(tipoResposta2.getId()).orElse(null);
     listRespostas2.add(tipoRespostaSalva2);

     questaoSalva2.setAlternativasResposta(listRespostas2);

     questoes.add(questao2Modelo);

     modeloSalvo.setQuestoes(questoes);

     Unidade unidade = Unidade.builder()
         .id(UUID.fromString("38c7b15b-82cf-4606-8789-3be6336606f8"))
         .nome("Unidade 01")
         .endereco("Rua das Neves")
         .tipo("Tratamento de Esgoto")
         .build();
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuarioCriacao(usuarioSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
    Modelo modeloSalvoNovo = modeloRepositorio.findById(modeloSalvo.getId()).orElse(null);
    assertNotNull(modeloSalvoNovo);
    Formulario formulario = formularioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(formulario);

    Formulario formularioAtualizado = formularioRepositorio.save(formulario);
    assertEquals(formularioAtualizado.getId(), formularioSalvo.getId());
    assertEquals(formulario.getId(), formularioSalvo.getId());
    assertEquals(formularioAtualizado.getId(), formulario.getId());

    assertEquals(
      formularioAtualizado.getUnidade().getId(),
      formularioSalvo.getUnidade().getId()
    );
    assertEquals(formulario.getUnidade().getId(), formularioSalvo.getUnidade().getId());
    assertEquals(formularioAtualizado.getUnidade().getId(), formulario.getUnidade().getId());

    assertEquals(
      formularioAtualizado.getUsuarioCriacao().getId(),
      formularioSalvo.getUsuarioCriacao().getId()
    );
    assertEquals(
      formulario.getUsuarioCriacao().getId(),
      formularioSalvo.getUsuarioCriacao().getId()
    );
    assertEquals(
      formularioAtualizado.getUsuarioCriacao().getId(),
      formulario.getUsuarioCriacao().getId()
    );
  }

  @Order(4)
  @Test
  public void testEditFormUnidade() {
    LocalDateTime now = LocalDateTime.now();
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
    usuario.setDataCriacao(now);
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(1, usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<QuestaoModelo> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");
    Questao questaoSalva = questaoRepositorio.save(questao);
    
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setModelo(modelo);
    questaoModelo.setQuestao(questao);
    questaoModeloRepositorio.save(questaoModelo);

    AlternativaResposta tipoResposta1 = new AlternativaResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setDescricao("Resposta1");

    List<AlternativaResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    AlternativaResposta tipoRespostaSalva = tipoRespostaRepositorio.findById(tipoResposta1.getId()).orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setAlternativasResposta(listRespostas);

    questoes.add(questaoModelo);

    Questao questao2 = new Questao();
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");
    Questao questaoSalva2 = questaoRepositorio.save(questao2);
    
    QuestaoModelo questao2Modelo = new QuestaoModelo();
    questao2Modelo.setModelo(modelo);
    questao2Modelo.setQuestao(questao2);
    questaoModeloRepositorio.save(questao2Modelo);

    AlternativaResposta tipoResposta2 = new AlternativaResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setDescricao("Resposta2");

    List<AlternativaResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    AlternativaResposta tipoRespostaSalva2 = tipoRespostaRepositorio.findById(tipoResposta2.getId()).orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setAlternativasResposta(listRespostas2);

    questoes.add(questao2Modelo);

    modeloSalvo.setQuestoes(questoes);

    Unidade unidade = Unidade.builder()
        .id(UUID.fromString("38c7b15b-82cf-4606-8789-3be6336606f8"))
        .nome("Unidade 01")
        .endereco("Rua das Neves")
        .tipo("Tratamento de Esgoto")
        .build();
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuarioCriacao(usuarioSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
    Unidade unidadeSalvaNova = unidadeRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(unidadeSalvaNova);
    Formulario formulario = formularioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(formulario);
    formulario.setUnidade(unidadeSalvaNova);

    Formulario formularioAtualizado = formularioRepositorio.save(formulario);
    assertEquals(formularioAtualizado.getId(), formularioSalvo.getId());
    assertEquals(formulario.getId(), formularioSalvo.getId());
    assertEquals(formularioAtualizado.getId(), formulario.getId());

    assertEquals(
      formularioAtualizado.getUnidade().getId(),
      formularioSalvo.getUnidade().getId()
    );
    assertEquals(formulario.getUnidade().getId(), formularioSalvo.getUnidade().getId());
    assertEquals(formularioAtualizado.getUnidade().getId(), formulario.getUnidade().getId());

    assertEquals(
      formularioAtualizado.getUsuarioCriacao().getId(),
      formularioSalvo.getUsuarioCriacao().getId()
    );
    assertEquals(
      formulario.getUsuarioCriacao().getId(),
      formularioSalvo.getUsuarioCriacao().getId()
    );
    assertEquals(
      formularioAtualizado.getUsuarioCriacao().getId(),
      formulario.getUsuarioCriacao().getId()
    );
  }

  @Order(5)
  @Test
  public void testDeleteFormulario() {
    LocalDateTime now = LocalDateTime.now();
    Usuario usuario = new Usuario();
    usuario.setNome("J\u00FAlia Alves Corazza");
    usuario.setEmail("juliaacorazza@gmail.com");
    usuario.setSenha("$2a$10$q/Dxa6TFXHnGBekmlmiW/ujV6HttSt/TlEADmu9Ga6JEm/zhLjiQu");
    usuario.setDataCriacao(now);
    usuario.setFuncao(Papel.USER);
    usuario.setCargo(Cargo.COORDENADOR);
    usuarioSalvo = usuarioRepositorio.save(usuario);
    assertEquals(1, usuarioSalvo.getId());
    Modelo modelo = new Modelo();
    modelo.setNome("Modelo 01");
    modeloSalvo = modeloRepositorio.save(modelo);

    List<QuestaoModelo> questoes = new ArrayList<>();

    Questao questao = new Questao();
    questao.setObjetiva(false);
    questao.setPergunta("Pergunta1");
    questao.setPortaria("1234567");
    Questao questaoSalva = questaoRepositorio.save(questao);
    
    QuestaoModelo questaoModelo = new QuestaoModelo();
    questaoModelo.setModelo(modelo);
    questaoModelo.setQuestao(questao);
    questaoModeloRepositorio.save(questaoModelo);
    
    AlternativaResposta tipoResposta1 = new AlternativaResposta();
    tipoResposta1.setQuestao(questaoSalva);
    tipoResposta1.setDescricao("Resposta1");

    List<AlternativaResposta> listRespostas = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta1);
    AlternativaResposta tipoRespostaSalva = tipoRespostaRepositorio.findById(tipoResposta1.getId()).orElse(null);
    listRespostas.add(tipoRespostaSalva);

    questaoSalva.setAlternativasResposta(listRespostas);

    questoes.add(questaoModelo);

    Questao questao2 = new Questao();
    questao2.setObjetiva(false);
    questao2.setPergunta("Pergunta2");
    questao2.setPortaria("1234567421");
    Questao questaoSalva2 = questaoRepositorio.save(questao2);

    QuestaoModelo questao2Modelo = new QuestaoModelo();
    questao2Modelo.setModelo(modelo);
    questao2Modelo.setQuestao(questao2);
    questaoModeloRepositorio.save(questao2Modelo);
    
    AlternativaResposta tipoResposta2 = new AlternativaResposta();
    tipoResposta2.setQuestao(questaoSalva2);
    tipoResposta2.setDescricao("Resposta2");

    List<AlternativaResposta> listRespostas2 = new ArrayList<>();
    tipoRespostaRepositorio.save(tipoResposta2);
    AlternativaResposta tipoRespostaSalva2 = tipoRespostaRepositorio.findById(tipoResposta2.getId()).orElse(null);
    listRespostas2.add(tipoRespostaSalva2);

    questaoSalva2.setAlternativasResposta(listRespostas2);

    questoes.add(questao2Modelo);

    modeloSalvo.setQuestoes(questoes);

    Unidade unidade = Unidade.builder()
        .id(UUID.fromString("38c7b15b-82cf-4606-8789-3be6336606f8"))
        .nome("Unidade 01")
        .endereco("Rua das Neves")
        .tipo("Tratamento de Esgoto")
        .build();
    unidadeSalva = unidadeRepositorio.save(unidade);
    usuarioSalvo = usuarioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(usuarioSalvo);

    modeloSalvo = modeloRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(modeloSalvo);

    unidadeSalva = unidadeRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(unidadeSalva);

    formulario =
      Formulario
        .builder()
        .usuarioCriacao(usuarioSalvo)
        .unidade(unidadeSalva)
        .dataCriacao(LocalDateTime.now())
        .build();

    formularioSalvo = formularioRepositorio.save(formulario);
    assertNotNull(formularioSalvo);
    assertNotNull(formularioSalvo.getId());
    formulario.setId(formularioSalvo.getId());
    Formulario formulario = formularioRepositorio.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058")).orElse(null);
    assertNotNull(formulario);
    formularioRepositorio.delete(formularioSalvo);
    Formulario formularioDeletado = formularioRepositorio
      .findById(formularioSalvo.getId())
      .orElse(null);
    assertNull(formularioDeletado);
  }
}
