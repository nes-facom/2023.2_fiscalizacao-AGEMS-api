package fiscalizacao.dsbrs.agems.apis.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoFormulario;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.repositorio.AlternativaRespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoFormularioRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.AlternativaRespostaRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AlternativaRespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloBuscaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResumidoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.QuestaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModeloService {

  private final ModeloRepositorio MODELO_REPOSITORIO;
  private final QuestaoRepositorio QUESTAO_REPOSITORIO;
  private final QuestaoModeloRepositorio QUESTAO_MODELO_REPOSITORIO;
  private final QuestaoFormularioRepositorio QUESTAO_FORMULARIO_REPOSITORIO;
  private final AlternativaRespostaRepositorio ALTERNATIVA_RESPOSTA_REPOSITORIO;
  private final UsuarioRepositorio USUARIO_REPOSITORIO;

  private ModeloResponse modeloResponse;
  
  private Usuario extrairUsuarioEmailHeader(HttpServletRequest request) {
    final String EMAIL_HEADER = (String) request.getAttribute("EMAIL_USUARIO");
    final String EMAIL_USUARIO = EMAIL_HEADER
      .substring(EMAIL_HEADER.indexOf(":") + 1)
      .trim();
    Usuario usuario = USUARIO_REPOSITORIO
      .findByEmail(EMAIL_USUARIO)
      .orElse(null);
    return usuario;
  }
  
  public Response cadastraModelo(HttpServletRequest request, ModeloRegisterRequest novoModelo) {
    
    Usuario usuario = extrairUsuarioEmailHeader(request);
    
    if (usuario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Usuário não existe")
        .build();
    }
    
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();

    Modelo modelo = new Modelo();
    LocalDateTime dataCriacao = LocalDateTime.now();
    
    modelo.setNome(novoModelo.getNome());
    modelo.setDataCriacao(dataCriacao);
    modelo.setUsuarioCriacao(usuario);
    
    Modelo newModelo = MODELO_REPOSITORIO.save(modelo);

    for (QuestaoRegisterRequest questaoRegister : novoModelo.getQuestoes()) {
      Questao questao = new Questao();
      questao.setPergunta(questaoRegister.getPergunta());
      questao.setPortaria(questaoRegister.getPortaria());
      questao.setObjetiva(questaoRegister.getObjetiva());
      questao.setDataCriacao(dataCriacao);
      questao.setUsuarioCriacao(usuario);
      
      Questao newQuestao = QUESTAO_REPOSITORIO.save(questao);

      QuestaoModelo modeloQuestao = new QuestaoModelo();
      modeloQuestao.setModelo(newModelo);
      modeloQuestao.setQuestao(newQuestao);
      
      QUESTAO_MODELO_REPOSITORIO.save(modeloQuestao);
      
      List<AlternativaRespostaResponse> responsesAlternativaRespostas = new ArrayList<>();
      for (AlternativaRespostaRegisterRequest alternativaRespostaRegister : questaoRegister.getAlternativaRespostas()) {
        AlternativaResposta alternativaResposta = new AlternativaResposta();

        alternativaResposta.setDescricao(alternativaRespostaRegister.getDescricao());
        alternativaResposta.setQuestao(newQuestao);

        AlternativaResposta newAlternativaResposta = ALTERNATIVA_RESPOSTA_REPOSITORIO.save(
          alternativaResposta
        );

        AlternativaRespostaResponse alternativaRespostaResponse = AlternativaRespostaResponse
          .builder()
          .idQuestao(newQuestao.getId())
          .id(newAlternativaResposta.getId())
          .descricao(newAlternativaResposta.getDescricao())
          .build();

        responsesAlternativaRespostas.add(alternativaRespostaResponse);
      }

      QuestaoResponse questaoResponse = QuestaoResponse
        .builder()
        .id(questao.getId())
        .uuidLocal(questaoRegister.getUuidLocal())
        .pergunta(questao.getPergunta())
        .portaria(questao.getPortaria())
        .objetiva(questao.isObjetiva())
        .respostas(responsesAlternativaRespostas)
        .build();

      responsesQuestao.add(questaoResponse);
    }
    
    modeloResponse =
      ModeloResponse
        .builder()
        .id(newModelo.getId())
        .uuidLocal(novoModelo.getUuidLocal())
        .nome(newModelo.getNome())
        .questoes(responsesQuestao)
        .build();

    return modeloResponse;
  }

  public ModeloAcaoResponse deletaModelo(UUID pedido) {
    Modelo modelo = MODELO_REPOSITORIO.findById(pedido).orElse(null);
    if (modelo != null) {
      List<Questao> questoes = modelo.getQuestoes().stream()
          .map(QuestaoModelo::getQuestao)
          .collect(Collectors.toList());
      modeloResponse =
        ModeloResponse
          .builder()
          .id(modelo.getId())
          .nome(modelo.getNome())
          .build();

      MODELO_REPOSITORIO.delete(modelo);

      for(Questao questao : questoes) {
        List<QuestaoModelo> questaoModeloList = QUESTAO_MODELO_REPOSITORIO.findByQuestao(questao);
        List<QuestaoFormulario> questaoFormularioList = QUESTAO_FORMULARIO_REPOSITORIO.findByQuestao(questao);
        if(questaoModeloList.size() == 0 && questaoFormularioList.size() == 0)
          QUESTAO_REPOSITORIO.delete(questao);
      }
      
      return ModeloAcaoResponse
        .builder()
        .acao("Modelo deletado")
        .modelo(modeloResponse)
        .build();
    }
    return null;
  }
  
  public ModeloBuscaResponse listaTodosModelosResumido(int pagina, int quantidade) {
    ModeloBuscaResponse response = new ModeloBuscaResponse();
    Page<Modelo> modelos = MODELO_REPOSITORIO.findAll(PageRequest.of(pagina, quantidade));

    response.setPagina(pagina);
    response.setPaginaMax(Math.max(0, modelos.getTotalPages()-1));
    response.setData(
        modelos.stream()
            .map(modelo -> new ModeloResumidoResponse(modelo.getId(), modelo.getNome()))
            .collect(Collectors.toList())
    );
    return response;
  }

  public ModeloListResponse listaTodosModelos() {
    
    List<ModeloResponse> responsesModelo = new ArrayList<>();
    Iterable<Modelo> modelos = MODELO_REPOSITORIO.findAll();

    for (Modelo modelo : modelos) {

      List<QuestaoResponse> responseQuestoes = new ArrayList<>();
      List<QuestaoModelo> questoesModelo = modelo.getQuestoes();

      for (QuestaoModelo questaoModelo : questoesModelo) {
        
        Questao questao = questaoModelo.getQuestao();
        List<AlternativaRespostaResponse> responseAlternativaRespostas = new ArrayList<>();
        List<AlternativaResposta> alternativaRespostas = questao.getAlternativasResposta();

        for (AlternativaResposta tipoRes : alternativaRespostas) {
          AlternativaRespostaResponse responseTipo = AlternativaRespostaResponse
            .builder()
            .id(tipoRes.getId())
            .descricao(tipoRes.getDescricao())
            .idQuestao(tipoRes.getQuestao().getId())
            .build();

          responseAlternativaRespostas.add(responseTipo);
        }

        QuestaoResponse responseQuest = QuestaoResponse
          .builder()
          .id(questao.getId())
          .pergunta(questao.getPergunta())
          .objetiva(questao.isObjetiva())
          .portaria(questao.getPortaria())
          .respostas(responseAlternativaRespostas)
          .build();

        responseQuestoes.add(responseQuest);
      }

      responsesModelo.add(
        ModeloResponse
        .builder()
        .id(modelo.getId())
        .nome(modelo.getNome())
        .questoes(responseQuestoes)
        .build());
    
    }
    
    return ModeloListResponse
        .builder()
        .data(responsesModelo)
        .build();
  }

  public ModeloResponse verModelo(UUID idModelo) {
	  
    Modelo modelo = MODELO_REPOSITORIO.findById(idModelo).orElse(null);

    if (modelo != null) {
    	
      List<QuestaoResponse> responseQuestoes = new ArrayList<>();

      List<QuestaoModelo> questoesModelo = modelo.getQuestoes();

      for (QuestaoModelo questaoModelo : questoesModelo) {
    	
    	Questao questao = questaoModelo.getQuestao();
    			
        List<AlternativaRespostaResponse> responseAlternativaRespostas = new ArrayList<>();
                
        List<AlternativaResposta> alternativaRespostas = questao.getAlternativasResposta();

        for (AlternativaResposta tipoRes : alternativaRespostas) {
          AlternativaRespostaResponse responseTipo = AlternativaRespostaResponse
            .builder()
            .id(tipoRes.getId())
            .descricao(tipoRes.getDescricao())
            .idQuestao(tipoRes.getQuestao().getId())
            .build();

          responseAlternativaRespostas.add(responseTipo);
        }

        QuestaoResponse responseQuest = QuestaoResponse
          .builder()
          .id(questao.getId())
          .pergunta(questao.getPergunta())
          .objetiva(questao.isObjetiva())
          .portaria(questao.getPortaria())
          .respostas(responseAlternativaRespostas)
          .build();

        responseQuestoes.add(responseQuest);
      }

      return ModeloResponse
        .builder()
        .id(modelo.getId())
        .nome(modelo.getNome())
        .questoes(responseQuestoes)
        .build();
    }
    return null;
  }

  public ModeloAcaoResponse editaModelo(ModeloEditRequest modeloEditRequest) {
	
    Modelo modelo = MODELO_REPOSITORIO.findById(modeloEditRequest.getId()).orElse(null);
    
    if (modelo != null) {
    	
      modelo.setNome(modeloEditRequest.getNome());

      List<QuestaoEditRequest> questaoEditRequests = modeloEditRequest.getQuestoes();
      List<QuestaoResponse> responsesQuestao = new ArrayList<>();
      
      for (QuestaoEditRequest questEdit : questaoEditRequests) {
        if (questEdit.getAcao().equalsIgnoreCase("delete")) {
        	
          Questao questao = QUESTAO_REPOSITORIO
            .findById(questEdit.getId())
            .orElse(null);

          QUESTAO_REPOSITORIO.delete(questao);
          
        } else if (questEdit.getAcao().equalsIgnoreCase("edit")) {
        	
          Questao questao = QUESTAO_REPOSITORIO
            .findById(questEdit.getId())
            .orElse(null);

          if (questao != null) {
        	  
            List<AlternativaRespostaEditRequest> pedidosAlternativaResposta = questEdit.getAlternativasResposta();
            List<AlternativaRespostaResponse> responsesAlternativaRespostas = new ArrayList<>();
            
            for (AlternativaRespostaEditRequest tipoEdit : pedidosAlternativaResposta) {
              AlternativaResposta AlternativaResposta = ALTERNATIVA_RESPOSTA_REPOSITORIO
                .findById(tipoEdit.getId())
                .orElse(null);

              if (tipoEdit.getAcao().equalsIgnoreCase("delete")) {
                ALTERNATIVA_RESPOSTA_REPOSITORIO.delete(AlternativaResposta);
              } else if (tipoEdit.getAcao().equalsIgnoreCase("edit")) {
                if (AlternativaResposta != null) {
                  AlternativaResposta.setDescricao(tipoEdit.getResposta());

                  ALTERNATIVA_RESPOSTA_REPOSITORIO.save(AlternativaResposta);

                  AlternativaRespostaResponse alternativaRespostaResponse = AlternativaRespostaResponse
                    .builder()
                    .id(AlternativaResposta.getId())
                    .descricao(AlternativaResposta.getDescricao())
                    .build();

                  responsesAlternativaRespostas.add(alternativaRespostaResponse);
                }
              }
            }

            questao.setPergunta(questEdit.getPergunta());
            questao.setObjetiva(questEdit.getObjetiva());
            questao.setPortaria(questEdit.getPortaria());

            QUESTAO_REPOSITORIO.save(questao);

            QuestaoResponse questaoResponse = QuestaoResponse
              .builder()
              .id(modeloEditRequest.getId())
              .pergunta(questao.getPergunta())
              .portaria(questEdit.getPortaria())
              .objetiva(questEdit.getObjetiva())
              .respostas(responsesAlternativaRespostas)
              .build();

            responsesQuestao.add(questaoResponse);
          }
        }
      }

      MODELO_REPOSITORIO.save(modelo);

      ModeloResponse moldResponse = ModeloResponse
        .builder()
        .id(modelo.getId())
        .nome(modelo.getNome())
        .questoes(responsesQuestao)
        .build();

      return ModeloAcaoResponse
        .builder()
        .acao("Modelo editado")
        .modelo(moldResponse)
        .build();
    }

    return null;
  }
}
