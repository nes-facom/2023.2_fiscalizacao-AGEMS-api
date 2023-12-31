package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Imagem;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Papel;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoFormulario;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.dominio.enums.Cargo;
import fiscalizacao.dsbrs.agems.apis.repositorio.FormularioRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ImagemRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoFormularioRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.RespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ImagemRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.service.FormularioService;
import jakarta.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
public class FormularioServiceTest {

  @Mock
  private UnidadeRepositorio repositorioUnidade;

  @Mock
  private ImagemRepositorio repositorioImagem;

  @Mock
  private QuestaoRepositorio repositorioQuestao;
  
  @Mock
  private QuestaoModeloRepositorio repositorioQuestaoModelo;
  
  @Mock
  private QuestaoFormularioRepositorio repositorioQuestaoFormulario;

  @Mock
  private UsuarioRepositorio repositorioUsuario;

  @Mock
  private RespostaRepositorio repositorioResposta;

  @Mock
  private ModeloRepositorio repositorioModelo;

  @Mock
  private FormularioRepositorio repositorioFormulario;

  @InjectMocks
  private FormularioService formularioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    formularioService =
        new FormularioService(repositorioFormulario,
            repositorioUsuario,
            repositorioModelo,
            repositorioQuestao,
            repositorioQuestaoModelo,
            repositorioQuestaoFormulario,
            repositorioResposta,
            repositorioUnidade,
            repositorioImagem);
  }

  @Test
  public void cadastraFormularioUsuarioNaoReturnsErroResponse() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    FormularioRegisterRequest formularioRegisterRequest = mock(
      FormularioRegisterRequest.class
    );
    when(repositorioUsuario.findById(any(UUID.class))).thenReturn(Optional.empty());
    when(request.getAttribute("EMAIL_USUARIO")).thenReturn("user@example.com");
    Response response = formularioService.cadastraFormulario(
      request,
      formularioRegisterRequest
    );
    assertTrue(response instanceof ErroResponse);
    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals("Usuário não existe", ((ErroResponse) response).getErro());
  }

  @Test
  public void cadastraFormularioModeloNaoReturnsErroResponse() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    FormularioRegisterRequest formularioRegisterRequest = mock(
      FormularioRegisterRequest.class
    );
    Usuario usuario = mock(Usuario.class);
    when(repositorioUsuario.findByEmail("user@example.com"))
      .thenReturn(Optional.of(usuario));
    when(repositorioModelo.findById(any(UUID.class))).thenReturn(Optional.empty());
    when(request.getAttribute("EMAIL_USUARIO")).thenReturn("user@example.com");

    Response response = formularioService.cadastraFormulario(
      request,
      formularioRegisterRequest
    );

    assertTrue(response instanceof ErroResponse);
    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals("Modelo não existe", ((ErroResponse) response).getErro());
  }

  @Test
  public void cadastraFormularioUnidadeReturnsErroResponse() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    FormularioRegisterRequest formularioRegisterRequest = mock(
      FormularioRegisterRequest.class
    );
    when(repositorioUnidade.findById(any(UUID.class))).thenReturn(Optional.empty());
    Usuario usuario = mock(Usuario.class);
    Modelo modelo = mock(Modelo.class);
    when(repositorioModelo.findById(any(UUID.class))).thenReturn(Optional.of(modelo));
    when(repositorioUsuario.findByEmail("user@example.com"))
      .thenReturn(Optional.of(usuario));
    UUID modeloUUID = UUID.randomUUID();
    when(formularioRegisterRequest.getModelo()).thenReturn(modeloUUID);
    when(request.getAttribute("EMAIL_USUARIO")).thenReturn("user@example.com");
    Response response = formularioService.cadastraFormulario(
      request,
      formularioRegisterRequest
    );

    assertTrue(response instanceof ErroResponse);
    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals("Unidade não existe", ((ErroResponse) response).getErro());
  }

  @Test
  public void cadastraFormularioNumRespostasReturnsErroResponse() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    FormularioRegisterRequest formularioRegisterRequest = mock(
      FormularioRegisterRequest.class
    );
    Unidade unidade = mock(Unidade.class);
    Modelo modelo = mock(Modelo.class);
    UUID uuid = mock(UUID.class);
    Usuario usuario = mock(Usuario.class);
    when(repositorioUsuario.findByEmail("user@example.com"))
      .thenReturn(Optional.of(usuario));
    when(request.getAttribute("EMAIL_USUARIO")).thenReturn("user@example.com");
    List<QuestaoModelo> questoes = Arrays.asList(
      mock(QuestaoModelo.class),
      mock(QuestaoModelo.class)
    );
    when(repositorioUnidade.findById(any(UUID.class)))
      .thenReturn(Optional.of(unidade));
    when(repositorioModelo.findById(any(UUID.class))).thenReturn(Optional.of(modelo));
    when(modelo.getQuestoes()).thenReturn(questoes);
    when(formularioRegisterRequest.getModelo()).thenReturn(uuid);
    when(formularioRegisterRequest.getUnidade()).thenReturn(uuid);
    when(formularioRegisterRequest.getImagens())
      .thenReturn(
        Arrays.asList(
          new ImagemRegisterRequest(),
          new ImagemRegisterRequest(),
          new ImagemRegisterRequest(),
          new ImagemRegisterRequest()
        )
      );
    when(formularioRegisterRequest.getRespostas())
      .thenReturn(Collections.singletonList(new RespostaRequest()));
    when(formularioRegisterRequest.getObservacao()).thenReturn(anyString());
    Response response = formularioService.cadastraFormulario(
      request,
      formularioRegisterRequest
    );

    assertTrue(response instanceof ErroResponse);
    assertEquals(400, ((ErroResponse) response).getStatus());
    assertEquals(
      "Mande o número correto de respostas.",
      ((ErroResponse) response).getErro()
    );
  }

  @Test
  public void testCadastrarFormularioDadosCorretos() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("juliaacorazza@gmail.com");
    FormularioRegisterRequest requestForm = new FormularioRegisterRequest();
    requestForm.setModelo(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    requestForm.setUnidade(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    List<RespostaRequest> respostas = new ArrayList<>();
    RespostaRequest resposta = new RespostaRequest(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"), UUID.fromString("38c7b15b-82cf-4606-8789-3be6336606f8"), "Não", " ");
    RespostaRequest respostaRequest2 = new RespostaRequest(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"), UUID.fromString("7dde8f7f-e50b-4ccd-8682-b34feee52206"), "Não", " ");
    respostas.add(resposta);
    respostas.add(respostaRequest2);
    requestForm.setRespostas(respostas);
    List<ImagemRegisterRequest> imagenRegisterRequests = new ArrayList<>();
    ImagemRegisterRequest imagemRegisterRq1 = new ImagemRegisterRequest(
      "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"
    );
    ImagemRegisterRequest imagemRegisterRq2 = new ImagemRegisterRequest(
      "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"
    );
    ImagemRegisterRequest imagemRegisterRq3 = new ImagemRegisterRequest(
      "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"
    );
    ImagemRegisterRequest imagemRegisterRq4 = new ImagemRegisterRequest(
      "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"
    );
    imagenRegisterRequests.add(imagemRegisterRq1);
    imagenRegisterRequests.add(imagemRegisterRq2);
    imagenRegisterRequests.add(imagemRegisterRq3);
    imagenRegisterRequests.add(imagemRegisterRq4);
    requestForm.setImagens(imagenRegisterRequests);
    requestForm.setObservacao("");

    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo(Cargo.ANALISTA_DE_REGULACAO);
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    usuario.setFuncao(Papel.USER);
    when(repositorioUsuario.findByEmail("juliaacorazza@gmail.com"))
      .thenReturn(Optional.of(usuario));

    Modelo modelo = new Modelo();
    modelo.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    modelo.setNome("Modelo 1");
    List<QuestaoModelo> questoes = new ArrayList<>();
    Questao q1 = new Questao();
    q1.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    QuestaoModelo qm1 = new QuestaoModelo();
    qm1.setQuestao(q1);
    qm1.setModelo(modelo);
    q1.setModelos(Collections.singletonList(qm1));
    q1.setObjetiva(true);
    q1.setPergunta("Pergunta 1");
    q1.setPortaria("Portaria 1");
    List<AlternativaResposta> tipoRespostas = new ArrayList<>();
    AlternativaResposta tipoResposta = new AlternativaResposta();
    tipoResposta.setQuestao(q1);
    tipoResposta.setDescricao("Sim");
    tipoRespostas.add(tipoResposta);
    AlternativaResposta tipoResposta1 = new AlternativaResposta();
    tipoResposta1.setQuestao(q1);
    tipoResposta1.setDescricao("Não");
    tipoRespostas.add(tipoResposta1);
    AlternativaResposta tipoResposta2 = new AlternativaResposta();
    tipoResposta2.setQuestao(q1);
    tipoResposta2.setDescricao("NA");
    tipoRespostas.add(tipoResposta2);
    q1.setAlternativasResposta(tipoRespostas);
    Questao q2 = new Questao();
    q2.setId(UUID.fromString("7dde8f7f-e50b-4ccd-8682-b34feee52206"));
    QuestaoModelo qm2 = new QuestaoModelo();
    qm2.setModelo(modelo);
    qm2.setQuestao(q2);
    q2.setModelos(Collections.singletonList(qm2));
    q2.setObjetiva(true);
    q2.setPergunta("Pergunta 2");
    q2.setPortaria("Portaria 2");
    List<AlternativaResposta> tipoRespostas2 = new ArrayList<>();
    AlternativaResposta tipoResposta3 = new AlternativaResposta();
    tipoResposta3.setQuestao(q2);
    tipoResposta3.setDescricao("Sim");
    tipoRespostas2.add(tipoResposta3);
    AlternativaResposta tipoResposta4 = new AlternativaResposta();
    tipoResposta4.setQuestao(q2);
    tipoResposta4.setDescricao("Não");
    tipoRespostas2.add(tipoResposta4);
    AlternativaResposta tipoResposta5 = new AlternativaResposta();
    tipoResposta5.setQuestao(q2);
    tipoResposta5.setDescricao("NA");
    tipoRespostas2.add(tipoResposta5);
    q1.setAlternativasResposta(tipoRespostas);
    questoes.add(qm1);
    q2.setAlternativasResposta(tipoRespostas2);
    questoes.add(qm2);
    modelo.setQuestoes(questoes);

    when(repositorioModelo.findById(modelo.getId()))
      .thenReturn(Optional.of(modelo));

    Unidade unidade = Unidade.builder()
        .id(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"))
        .nome("Unidade 1")
        .endereco("Endereço 1")
        .tipo("Tratamento de Esgoto")
        .build();
    Formulario formulario = new Formulario();
    formulario.setDataCriacao(LocalDateTime.now());
    formulario.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    formulario.setUsuarioCriacao(usuario);
    formulario.setUnidade(unidade);
    formulario.setObservacao("");
    Resposta respostaF = Resposta.builder()
        .formulario(formulario)
        .questao(q1)
        .resposta("Não")
        .observacao(" ")
        .build();
    List<Resposta> respostasList = new ArrayList<Resposta>();
    respostasList.add(respostaF);

    when(repositorioQuestao.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"))).thenReturn(Optional.of(q1));
    when(repositorioUnidade.findById(unidade.getId()))
      .thenReturn(Optional.of(unidade));
    when(repositorioImagem.save(any(Imagem.class))).thenReturn(new Imagem());
    when(repositorioResposta.save(any(Resposta.class))).thenReturn(respostaF);
    when(repositorioQuestaoModelo.findByModelo(any(Modelo.class))).thenReturn(List.of(qm1, qm2));

    when(repositorioFormulario.save(any(Formulario.class)))
      .thenReturn(formulario);
    Response response = formularioService.cadastraFormulario(
      request,
      requestForm
    );
    assertTrue(response != null);
    assertTrue(response instanceof FormularioResponse);
  }
  
  @Test
  public void testDeletarFormularioQuestaoComRelacionamento() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("juliaacorazza@gmail.com");

    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo(Cargo.ANALISTA_DE_REGULACAO);
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    usuario.setFuncao(Papel.USER);
    when(repositorioUsuario.findByEmail("juliaacorazza@gmail.com"))
      .thenReturn(Optional.of(usuario));
    
    Questao questao = new Questao();
    questao.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    questao.setAlternativasResposta(Collections.emptyList());
    Unidade unidade = new Unidade();
    unidade.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    unidade.setNome("Unidade Teste");
    unidade.setEndereco("Endereço Teste");
    Formulario formulario = new Formulario();
    formulario.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    formulario.setUsuarioCriacao(usuario);
    formulario.setUnidade(unidade);
    QuestaoFormulario questaoFormulario = new QuestaoFormulario();
    questaoFormulario.setFormulario(formulario);
    questaoFormulario.setQuestao(questao);
    List<QuestaoFormulario> questoes = new ArrayList<>();
    questoes.add(questaoFormulario);
    formulario.setQuestoes(questoes);
    when(repositorioQuestaoFormulario.findByQuestao(any(Questao.class))).thenReturn(questoes);
    when(repositorioQuestaoModelo.findByQuestao(any(Questao.class))).thenReturn(Collections.emptyList());
    when(repositorioFormulario.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"))).thenReturn(Optional.of(formulario));
    
    formularioService.deletaFormulario(request, UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    
    verify(repositorioQuestao, times(0)).delete(any(Questao.class));
  }
  
  @Test
  public void testDeletarFormularioQuestaoSemRelacionamento() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getAttribute("EMAIL_USUARIO"))
      .thenReturn("juliaacorazza@gmail.com");

    String userEmail = "juliaacorazza@gmail.com";
    Usuario usuario = new Usuario();
    usuario.setNome("Júlia Alves Corazza");
    usuario.setEmail(userEmail);
    usuario.setCargo(Cargo.ANALISTA_DE_REGULACAO);
    usuario.setSenha(
      "$2a$10$3VCBCGty4I1OTx.gzi4c7.0IT0J9S2qZtBRmTyS3kQ8mYabar3Qv6"
    );
    usuario.setFuncao(Papel.USER);
    when(repositorioUsuario.findByEmail("juliaacorazza@gmail.com"))
      .thenReturn(Optional.of(usuario));
    
    Questao questao = new Questao();
    questao.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    questao.setAlternativasResposta(Collections.emptyList());
    Unidade unidade = new Unidade();
    unidade.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    unidade.setNome("Unidade Teste");
    unidade.setEndereco("Endereço Teste");
    Formulario formulario = new Formulario();
    formulario.setId(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    formulario.setUsuarioCriacao(usuario);
    formulario.setUnidade(unidade);
    QuestaoFormulario questaoFormulario = new QuestaoFormulario();
    questaoFormulario.setFormulario(formulario);
    questaoFormulario.setQuestao(questao);
    List<QuestaoFormulario> questoes = new ArrayList<>();
    questoes.add(questaoFormulario);
    formulario.setQuestoes(questoes);
    when(repositorioQuestaoFormulario.findByFormulario(any(Formulario.class))).thenReturn(Collections.emptyList());
    when(repositorioQuestaoModelo.findByQuestao(any(Questao.class))).thenReturn(Collections.emptyList());
    when(repositorioFormulario.findById(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"))).thenReturn(Optional.of(formulario));
    
    formularioService.deletaFormulario(request, UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"));
    
    verify(repositorioQuestao, times(1)).delete(any(Questao.class));
  }

  @Test
  public void testListaFormulariosOK() {
    Formulario form = Formulario.builder()
        .id(UUID.fromString("82acc4ec-e0f0-4da5-803c-cc3123afe058"))
        .imagens(Collections.emptyList())
        .questoes(Collections.emptyList())
        .respostas(Collections.emptyList())
        .unidade(new Unidade())
        .usuarioCriacao(new Usuario())
        .build();
    List<Formulario> listForm = Collections.singletonList(form);
    
    when(repositorioFormulario.findAll()).thenReturn(listForm);
    
    FormularioListResponse response = formularioService.listaTodosFormularios();
    
    assertEquals(response.getData().size(), 1);
    assertEquals(response.getData().get(0).getId(), form.getId());
  }
}
