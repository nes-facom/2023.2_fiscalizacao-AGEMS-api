package fiscalizacao.dsbrs.agems.apis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import fiscalizacao.dsbrs.agems.apis.repositorio.AlternativaRespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AlternativaRespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResumidoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.responses.QuestaoResponse;
import fiscalizacao.dsbrs.agems.apis.service.ModeloService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ModeloServiceTest {

    @Mock
    private ModeloRepositorio modeloRepositorio;
    @Mock
    private AlternativaRespostaRepositorio tipoRespostaRepositorio;
    @Mock
    private QuestaoRepositorio questaoRepositorio;
    @Mock
    private QuestaoModeloRepositorio questaoModeloRepositorio;

    @InjectMocks
    private ModeloService modeloService;

    @Test
    public void testCadastraModelo() {
        ModeloRegisterRequest request = new ModeloRegisterRequest();
        request.setNome("Modelo Teste");

        QuestaoRegisterRequest questaoRequest1 = new QuestaoRegisterRequest();
        questaoRequest1.setPergunta("Questao Teste 1");
        questaoRequest1.setObjetiva(true);
        questaoRequest1.setPortaria("Portaria1");

        AlternativaRespostaRegisterRequest Tiporequest1 = new AlternativaRespostaRegisterRequest();
        Tiporequest1.setDescricao("SIM");
        AlternativaRespostaRegisterRequest Tiporequest2 = new AlternativaRespostaRegisterRequest();
        Tiporequest2.setDescricao("NÃO");
        AlternativaRespostaRegisterRequest Tiporequest3 = new AlternativaRespostaRegisterRequest();
        Tiporequest3.setDescricao("NAO SE APLICA");

        List<AlternativaRespostaRegisterRequest> respostas1 = new ArrayList<>();
        respostas1.add(Tiporequest1);
        respostas1.add(Tiporequest2);
        respostas1.add(Tiporequest3);
        questaoRequest1.setAlternativaRespostas(respostas1);

        List<QuestaoRegisterRequest> questoes = new ArrayList<>();
        questoes.add(questaoRequest1);

        request.setQuestoes(questoes);

        when(questaoRepositorio.save(any(Questao.class))).thenReturn(new Questao());
        when(tipoRespostaRepositorio.save(any(AlternativaResposta.class))).thenReturn(new AlternativaResposta());
        when(modeloRepositorio.save(any(Modelo.class))).thenReturn(new Modelo());
        when(questaoModeloRepositorio.save(any(QuestaoModelo.class))).thenReturn(new QuestaoModelo());

        ModeloResponse response = modeloService.cadastraModelo(request);

        assertNotNull(response);
    }

    @Test
    public void testVerModeloPorId() {

        int modeloId = 1;
        Modelo foundModelo = new Modelo();
        foundModelo.setId(modeloId);
        foundModelo.setNome("Test Modelo");
        foundModelo.setQuestoes(new ArrayList<>());

        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.of(foundModelo));
        ModeloResponse modeloResponse = modeloService.verModelo(modeloId);

        assertEquals(foundModelo.getId(), modeloResponse.getId());
        assertEquals(foundModelo.getNome(), modeloResponse.getNome());
        verify(modeloRepositorio, times(1)).findById(modeloId);
    }

    @Test
    public void testVerModeloRetornaNull() {

        int modeloId = 999;
        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.empty());

        assertNull(modeloService.verModelo(modeloId));
        verify(modeloRepositorio, times(1)).findById(modeloId);
    }


    @Test
    public void testVerModelo() {
        // Mock the Modelo object
        Modelo modelo = new Modelo();
        modelo.setId(1);
        List<QuestaoModelo> questoes = new ArrayList<>();
        modelo.setQuestoes(questoes);
        when(modeloRepositorio.findById(1)).thenReturn(Optional.of(modelo));

        // Mock the Questao objects
        Questao questao1 = new Questao();
        questao1.setId(1);
        QuestaoModelo questao1Modelo = new QuestaoModelo();
        questao1Modelo.setModelo(modelo);
        questao1Modelo.setQuestao(questao1);
        questao1.setModelos(Collections.singletonList(questao1Modelo));
        Questao questao2 = new Questao();
        questao2.setId(1);
        QuestaoModelo questao2Modelo = new QuestaoModelo();
        questao2.setModelos(Collections.singletonList(questao2Modelo));
        questao2Modelo.setQuestao(questao2);
        questao2Modelo.setModelo(modelo);
        questoes.add(questao1Modelo);
        questoes.add(questao2Modelo);

        // Mock the AlternativaResposta objects
        AlternativaResposta tipoResposta1 = new AlternativaResposta();
        tipoResposta1.setId(1);
        tipoResposta1.setDescricao("Answer 1");
        tipoResposta1.setQuestao(questao1);
        AlternativaResposta tipoResposta2 = new AlternativaResposta();
        tipoResposta2.setId(2);
        tipoResposta2.setDescricao("Answer 2");
        tipoResposta2.setQuestao(questao1);
        List<AlternativaResposta> tipoRespostas = new ArrayList<>();
        tipoRespostas.add(tipoResposta1);
        tipoRespostas.add(tipoResposta2);

        AlternativaResposta tipoResposta3 = new AlternativaResposta();
        tipoResposta3.setId(3);
        tipoResposta3.setDescricao("Answer 3");
        tipoResposta3.setQuestao(questao2);
        AlternativaResposta tipoResposta4 = new AlternativaResposta();
        tipoResposta4.setId(4);
        tipoResposta4.setDescricao("Answer 4");
        tipoResposta4.setQuestao(questao2);
        List<AlternativaResposta> tipoRespostas2 = new ArrayList<>();
        tipoRespostas2.add(tipoResposta3);
        tipoRespostas2.add(tipoResposta4);
        
        questao1.setAlternativasResposta(tipoRespostas);
        questao2.setAlternativasResposta(tipoRespostas2);

        // Perform the method invocation
        ModeloResponse response = modeloService.verModelo(1);

        // Verify the expected behavior
        assertEquals(1, response.getId());
        assertEquals(2, response.getQuestoes().size());
        assertEquals("Answer 1", response.getQuestoes().get(0).getRespostas().get(0).getDescricao());
        assertEquals("Answer 2", response.getQuestoes().get(0).getRespostas().get(1).getDescricao());

        // Verify the interactions with mocked repositories
        verify(modeloRepositorio, times(1)).findById(1);
        verifyNoMoreInteractions(modeloRepositorio);
    }

    @Test
    public void testListaTodosModelos() {

        List<Modelo> modelos = new ArrayList<>();
        Modelo modelo1 = new Modelo();

        modelo1.setNome("Modelo 1");
        modelos.add(modelo1);

        Modelo modelo2 = new Modelo();
        modelo2.setId(2);
        modelo2.setNome("Modelo 2");
        modelos.add(modelo2);

        when(modeloRepositorio.findAll()).thenReturn(modelos);
        List<ModeloResumidoResponse> modeloResponses = modeloService.listaTodosModelosResumido();

        assertEquals(modelos.size(), modeloResponses.size());
        assertEquals(modelo1.getId(), modeloResponses.get(0).getId());
        assertEquals(modelo1.getNome(), modeloResponses.get(0).getNome());
        assertEquals(modelo2.getId(), modeloResponses.get(1).getId());
        assertEquals(modelo2.getNome(), modeloResponses.get(1).getNome());
        verify(modeloRepositorio, times(1)).findAll();
    }

    @Test
    public void testdeletaModelo() {
        // Mock input data
        int modeloId = 1;
        Modelo modelo1 = new Modelo();
        modelo1.setId(modeloId);
        modelo1.setNome("modelo 1");

        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.of(modelo1));

        // Perform the service method
        ModeloAcaoResponse response = modeloService.deletaModelo(modeloId);

        // Verify repository delete method invocations
        verify(modeloRepositorio, times(1)).delete(modelo1);

        // Verify the returned result
        assertNotNull(response);
        assertEquals("Modelo deletado", response.getAcao());
        assertNotNull(response.getModelo());
        assertEquals(modeloId, response.getModelo().getId());
        assertEquals("modelo 1", response.getModelo().getNome());
    }

    @Test
    public void testDeletaModeloRetornaNull() {

        int modeloId = 1;
        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.empty());

        ModeloAcaoResponse response = modeloService.deletaModelo(modeloId);
        assertNull(response);

    }
    @Test
    public void testEditaModelo() {
        // Mock data
        int modeloId = 1;
        int questaoId = 1;
        int tipoRespostaId = 1;
        String novoModeloNome = "Updated Model";
        String novaPergunta = "Updated Question";
        String novaResposta = "Updated Answer";

        // Cria modelo
        Modelo modelo = new Modelo();
        modelo.setId(modeloId);
        modelo.setNome("Old Model");

        // Cria questão
        Questao questao = new Questao();
        questao.setId(questaoId);
        questao.setPergunta("Old Question");
        QuestaoModelo questaoModelo = new QuestaoModelo();
        questaoModelo.setQuestao(questao);
        questaoModelo.setModelo(modelo);
        questao.setModelos(Collections.singletonList(questaoModelo));

        // Cria tipo Resposta
        AlternativaResposta tipoResposta = new AlternativaResposta();
        tipoResposta.setId(tipoRespostaId);
        tipoResposta.setDescricao("Old Answer");
        tipoResposta.setQuestao(questao);

        // Adiciona tipoResposta a questão
        List<AlternativaResposta> respostas = new ArrayList<>();
        respostas.add(tipoResposta);
        questao.setAlternativasResposta(respostas);

        // Adiciona questão ao modelo
        List<QuestaoModelo> questoes = new ArrayList<>();
        questoes.add(questaoModelo);
        modelo.setQuestoes(questoes);

        // Create the edit request
        List<QuestaoEditRequest> questaoEditRequests = new ArrayList<>();
        QuestaoEditRequest questaoEditRequest = new QuestaoEditRequest();
        questaoEditRequest.setId(questaoId);
        questaoEditRequest.setAcao("edit");
        questaoEditRequest.setPergunta(novaPergunta);
        questaoEditRequest.setObjetiva(true);

        List<AlternativaRespostaEditRequest> tipoRespostaEditRequests = new ArrayList<>();
        AlternativaRespostaEditRequest tipoRespostaEditRequest = new AlternativaRespostaEditRequest();
        tipoRespostaEditRequest.setId(tipoRespostaId);
        tipoRespostaEditRequest.setAcao("edit");
        tipoRespostaEditRequest.setResposta(novaResposta);
        tipoRespostaEditRequests.add(tipoRespostaEditRequest);
        questaoEditRequest.setAlternativasResposta(tipoRespostaEditRequests);

        questaoEditRequests.add(questaoEditRequest);

        ModeloEditRequest modeloEditRequest = new ModeloEditRequest();
        modeloEditRequest.setId(modeloId);
        modeloEditRequest.setModeloNome(novoModeloNome);
        modeloEditRequest.setQuestoes(questaoEditRequests);

        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.of(modelo));
        when(questaoRepositorio.findById(questaoId)).thenReturn(Optional.of(questao));
        when(tipoRespostaRepositorio.findById(tipoRespostaId)).thenReturn(Optional.of(tipoResposta));
        when(questaoRepositorio.save(any(Questao.class))).thenReturn(questao);
        when(tipoRespostaRepositorio.save(any(AlternativaResposta.class))).thenReturn(tipoResposta);
        when(modeloRepositorio.save(any(Modelo.class))).thenReturn(modelo);

        ModeloAcaoResponse response = modeloService.editaModelo(modeloEditRequest);

        verify(modeloRepositorio).findById(modeloId);
        verify(questaoRepositorio).findById(questaoId);
        verify(tipoRespostaRepositorio).findById(tipoRespostaId);
        verify(questaoRepositorio).save(any(Questao.class));
        verify(tipoRespostaRepositorio).save(any(AlternativaResposta.class));
        verify(modeloRepositorio).save(any(Modelo.class));

        ModeloResponse modeloResponse = response.getModelo();
        assertEquals("Modelo editado", response.getAcao());
        assertEquals(modeloId, modeloResponse.getId());
        assertEquals(novoModeloNome, modeloResponse.getNome());

        QuestaoResponse questaoResponse = modeloResponse.getQuestoes().get(0);
        assertEquals(questaoId, questaoResponse.getId());
        assertEquals(novaPergunta, questaoResponse.getPergunta());

        AlternativaRespostaResponse tipoRespostaResponse = questaoResponse.getRespostas().get(0);
        assertEquals(tipoRespostaId, tipoRespostaResponse.getId());
        assertEquals(novaResposta, tipoRespostaResponse.getDescricao());
    }

    @Test
    public void testEditaModeloDeletaQuestao() {
        // Mock data
        int modeloId = 1;
        int questaoId = 1;
        int tipoRespostaId = 1;
        String novoModeloNome = "Updated Model";

        // Cria modelo
        Modelo modelo = new Modelo();
        modelo.setId(modeloId);
        modelo.setNome("Old Model");

        // Cria questão
        Questao questao = new Questao();
        questao.setId(questaoId);
        QuestaoModelo questaoModelo = new QuestaoModelo();
        questaoModelo.setQuestao(questao);
        questaoModelo.setModelo(modelo);
        questao.setPergunta("Old Question");
        questao.setModelos(Collections.singletonList(questaoModelo));

        // Cria tipo Resposta
        AlternativaResposta tipoResposta = new AlternativaResposta();
        tipoResposta.setId(tipoRespostaId);
        tipoResposta.setDescricao("Old Answer");
        tipoResposta.setQuestao(questao);

        // Adiciona tipoResposta a questão
        List<AlternativaResposta> respostas = new ArrayList<>();
        respostas.add(tipoResposta);
        questao.setAlternativasResposta(respostas);

        // Adiciona questão ao modelo
        List<QuestaoModelo> questoes = new ArrayList<>();
        questoes.add(questaoModelo);
        modelo.setQuestoes(questoes);

        // Cria edit request
        List<QuestaoEditRequest> questaoEditRequests = new ArrayList<>();
        QuestaoEditRequest questaoEditRequest = new QuestaoEditRequest();
        questaoEditRequest.setId(questaoId);
        questaoEditRequest.setAcao("delete");
        questaoEditRequests.add(questaoEditRequest);

        ModeloEditRequest modeloEditRequest = new ModeloEditRequest();
        modeloEditRequest.setId(modeloId);
        modeloEditRequest.setModeloNome(novoModeloNome);
        modeloEditRequest.setQuestoes(questaoEditRequests);

        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.of(modelo));
        when(questaoRepositorio.findById(questaoId)).thenReturn(Optional.of(questao));
        when(modeloRepositorio.save(any(Modelo.class))).thenReturn(modelo);

        ModeloAcaoResponse response = modeloService.editaModelo(modeloEditRequest);

        verify(modeloRepositorio).findById(modeloId);
        verify(questaoRepositorio).findById(questaoId);
        verify(modeloRepositorio).save(any(Modelo.class));
        verify(questaoRepositorio).delete(questao);

        ModeloResponse modeloResponse = response.getModelo();
        assertEquals("Modelo editado", response.getAcao());
        assertEquals(modeloId, modeloResponse.getId());
        assertEquals(novoModeloNome, modeloResponse.getNome());

        assertTrue(modeloResponse.getQuestoes().isEmpty());
    }
        @Test
        public void testEditaModeloDeleteAlternativaResposta() {
        // Mock data
        int modeloId = 1;
        int questaoId = 1;
        int tipoRespostaId = 1;
        String novoModeloNome = "Updated Model";

        // Cria modelo
        Modelo modelo = new Modelo();
        modelo.setId(modeloId);
        modelo.setNome("Old Model");

        // Cria questão
        Questao questao = new Questao();
        questao.setId(questaoId);
        QuestaoModelo questaoModelo = new QuestaoModelo();
        questaoModelo.setQuestao(questao);
        questaoModelo.setModelo(modelo);
        questao.setPergunta("Old Question");
        questao.setModelos(Collections.singletonList(questaoModelo));

        // Cria tipo Resposta
        AlternativaResposta tipoResposta = new AlternativaResposta();
        tipoResposta.setId(tipoRespostaId);
        tipoResposta.setDescricao("Old Answer");
        tipoResposta.setQuestao(questao);

        // Adiciona tipoResposta a questão
        List<AlternativaResposta> respostas = new ArrayList<>();
        respostas.add(tipoResposta);
        questao.setAlternativasResposta(respostas);

        // Adiciona questão ao modelo
        List<QuestaoModelo> questoes = new ArrayList<>();
        questoes.add(questaoModelo);
        modelo.setQuestoes(questoes);

        // Create the edit request
        List<QuestaoEditRequest> questaoEditRequests = new ArrayList<>();
        QuestaoEditRequest questaoEditRequest = new QuestaoEditRequest();
        questaoEditRequest.setId(questaoId);
        questaoEditRequest.setAcao("edit");
        questaoEditRequest.setObjetiva(false);
        questaoEditRequests.add(questaoEditRequest);

        List<AlternativaRespostaEditRequest> tipoRespostaEditRequests = new ArrayList<>();
        AlternativaRespostaEditRequest tipoRespostaEditRequest = new AlternativaRespostaEditRequest();
        tipoRespostaEditRequest.setId(tipoRespostaId);
        tipoRespostaEditRequest.setAcao("delete");
        tipoRespostaEditRequests.add(tipoRespostaEditRequest);

        questaoEditRequest.setAlternativasResposta(tipoRespostaEditRequests);
        questaoEditRequests.add(questaoEditRequest);

        ModeloEditRequest modeloEditRequest = new ModeloEditRequest();
        modeloEditRequest.setId(modeloId);
        modeloEditRequest.setModeloNome(novoModeloNome);
        modeloEditRequest.setQuestoes(questaoEditRequests);

        // Mock repository method calls
        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.of(modelo));
        when(questaoRepositorio.findById(questaoId)).thenReturn(Optional.of(questao));
        when(tipoRespostaRepositorio.findById(tipoRespostaId)).thenReturn(Optional.of(tipoResposta));
        when(questaoRepositorio.save(any(Questao.class))).thenReturn(questao);
        when(tipoRespostaRepositorio.save(any(AlternativaResposta.class))).thenReturn(tipoResposta);
        when(modeloRepositorio.save(any(Modelo.class))).thenReturn(modelo);

        // Perform the edit operation
        ModeloAcaoResponse response = modeloService.editaModelo(modeloEditRequest);

        // Verify the repository method calls
        verify(modeloRepositorio).findById(modeloId);
        verify(questaoRepositorio, times(2)).findById(questaoId);
        verify(tipoRespostaRepositorio, times(2)).findById(tipoRespostaId);
        verify(questaoRepositorio,times(2)).save(any(Questao.class));
        verify(modeloRepositorio).save(any(Modelo.class));
        verify(tipoRespostaRepositorio,times(2)).delete(tipoResposta);

        // Verify the response
        ModeloResponse modeloResponse = response.getModelo();
        assertEquals("Modelo editado", response.getAcao());
        assertEquals(modeloId, modeloResponse.getId());
        assertEquals(novoModeloNome, modeloResponse.getNome());

        assertTrue(modeloResponse.getQuestoes().get(1).getRespostas().isEmpty());
    }

    @Test
    public void testEditaModeloReturnNull(){
        int modeloId = 1;

        ModeloEditRequest modeloEdit = new ModeloEditRequest();
        modeloEdit.setId(modeloId);

        when(modeloRepositorio.findById(modeloId)).thenReturn(Optional.empty());
        ModeloAcaoResponse acao = modeloService.editaModelo(modeloEdit);
        
        assertNull(acao);
    }



}
