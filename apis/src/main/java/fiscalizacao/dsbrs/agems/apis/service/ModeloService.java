package fiscalizacao.dsbrs.agems.apis.service;

import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.TipoRespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.ModeloRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.QuestaoRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaEditRequest;
import fiscalizacao.dsbrs.agems.apis.requests.TipoRespostaRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloResponse;
import fiscalizacao.dsbrs.agems.apis.responses.QuestaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.TipoRespostaResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeloService {

  private final ModeloRepositorio MODELO_REPOSITORIO;
  private final QuestaoRepositorio QUESTAO_REPOSITORIO;
  private final TipoRespostaRepositorio TIPO_RESPOSTA_REPOSITORIO;

  private ModeloResponse modeloResponse;

  public ModeloResponse cadastraModelo(ModeloRegisterRequest novoModelo) {
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();

    Modelo modelo = new Modelo();

    modelo.setModeloNome(novoModelo.getNome());
    Modelo newModelo = MODELO_REPOSITORIO.save(modelo);

    for (QuestaoRegisterRequest questaoRegister : novoModelo.getQuestoes()) {
      //-----------------------------------------------------
      Questao questao = new Questao();
      questao.setPergunta(questaoRegister.getPergunta());
      questao.setPortaria(questaoRegister.getPortaria());
      questao.setObjetiva(questaoRegister.getObjetiva());
      questao.setModelo(newModelo);
      Questao newQuestao = QUESTAO_REPOSITORIO.save(questao);

      List<TipoRespostaResponse> responsesTipoRespostas = new ArrayList<>();
      for (TipoRespostaRegisterRequest tipoRespostaRegister : questaoRegister.getTipoRespostas()) {
        TipoResposta tipoResposta = new TipoResposta();

        tipoResposta.setResposta(tipoRespostaRegister.getResposta());
        tipoResposta.setQuestao(newQuestao);

        TipoResposta newTipoResposta = TIPO_RESPOSTA_REPOSITORIO.save(
          tipoResposta
        );

        TipoRespostaResponse tipoRespostaResponse = TipoRespostaResponse
          .builder()
          .idQuestao(newQuestao.getId())
          .id(newTipoResposta.getId())
          .resposta(newTipoResposta.getResposta())
          .build();

        responsesTipoRespostas.add(tipoRespostaResponse);
      }

      QuestaoResponse questaoResponse = QuestaoResponse
        .builder()
        .id(questao.getId())
        .idModelo(newModelo.getId())
        .pergunta(questao.getPergunta())
        .portaria(questao.getPortaria())
        .objetiva(questao.isObjetiva())
        .respostas(responsesTipoRespostas)
        .build();

      responsesQuestao.add(questaoResponse);
    }
    modeloResponse =
      ModeloResponse
        .builder()
        .id(newModelo.getId())
        .nome(newModelo.getModeloNome())
        .questoes(responsesQuestao)
        .build();

    return modeloResponse;
  }

  public ModeloAcaoResponse deletaModelo(int pedido) {
    Modelo modelo = MODELO_REPOSITORIO.findById(pedido).orElse(null);
    if (modelo != null) {
      modeloResponse =
        ModeloResponse
          .builder()
          .id(modelo.getId())
          .nome(modelo.getModeloNome())
          .build();

      MODELO_REPOSITORIO.delete(modelo);

      return ModeloAcaoResponse
        .builder()
        .acao("Modelo deletado")
        .modelo(modeloResponse)
        .build();
    }
    return null;
  }

  public List<ModeloListResponse> listaTodosModelos() {
    List<ModeloListResponse> responsesModelo = new ArrayList<>();
    Iterable<Modelo> modelos = MODELO_REPOSITORIO.findAll();

    for (Modelo modelo : modelos) {
      ModeloListResponse response = new ModeloListResponse();
      response.setId(modelo.getId());
      response.setNome(modelo.getModeloNome());

      responsesModelo.add(response);
    }
    return responsesModelo;
  }

  public ModeloResponse verModelo(int pedido) {
    Modelo modelo = MODELO_REPOSITORIO.findById(pedido).orElse(null);

    if (modelo != null) {
      List<QuestaoResponse> responseQuestoes = new ArrayList<>();

      List<Questao> questoes = modelo.getPerguntas();

      for (Questao questao : questoes) {
        List<TipoRespostaResponse> responsetipoRespostas = new ArrayList<>();

        List<TipoResposta> tipoRespostas = questao.getRespostas();

        for (TipoResposta tipoRes : tipoRespostas) {
          TipoRespostaResponse responseTipo = TipoRespostaResponse
            .builder()
            .id(tipoRes.getId())
            .resposta(tipoRes.getResposta())
            .idQuestao(tipoRes.getQuestao().getId())
            .build();

          responsetipoRespostas.add(responseTipo);
        }

        QuestaoResponse responseQuest = QuestaoResponse
          .builder()
          .id(questao.getId())
          .idModelo(questao.getModelo().getId())
          .pergunta(questao.getPergunta())
          .objetiva(questao.isObjetiva())
          .portaria(questao.getPortaria())
          .respostas(responsetipoRespostas)
          .build();

        responseQuestoes.add(responseQuest);
      }

      return ModeloResponse
        .builder()
        .id(modelo.getId())
        .nome(modelo.getModeloNome())
        .questoes(responseQuestoes)
        .build();
    }
    return null;
  }

  public ModeloAcaoResponse editaModelo(ModeloEditRequest pedido) {
    Modelo modelo = MODELO_REPOSITORIO.findById(pedido.getId()).orElse(null);
    if (modelo != null) {
      modelo.setModeloNome(pedido.getNome());

      List<QuestaoEditRequest> questaoEditRequests = pedido.getQuestoes();
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
            List<TipoRespostaEditRequest> pedidosTipoResposta = questEdit.getTipoRespostas();

            List<TipoRespostaResponse> responsesTipoRespostas = new ArrayList<>();
            for (TipoRespostaEditRequest tipoEdit : pedidosTipoResposta) {
              TipoResposta tipoResposta = TIPO_RESPOSTA_REPOSITORIO
                .findById(tipoEdit.getId())
                .orElse(null);

              if (tipoEdit.getAcao().equalsIgnoreCase("delete")) {
                TIPO_RESPOSTA_REPOSITORIO.delete(tipoResposta);
              } else if (tipoEdit.getAcao().equalsIgnoreCase("edit")) {
                if (tipoResposta != null) {
                  tipoResposta.setResposta(tipoEdit.getResposta());

                  TIPO_RESPOSTA_REPOSITORIO.save(tipoResposta);

                  TipoRespostaResponse tipoRespostaResponse = TipoRespostaResponse
                    .builder()
                    .id(tipoResposta.getId())
                    .resposta(tipoResposta.getResposta())
                    .build();

                  responsesTipoRespostas.add(tipoRespostaResponse);
                }
              }
            }

            questao.setPergunta(questEdit.getPergunta());
            questao.setObjetiva(questEdit.getObjetiva());
            questao.setPortaria(questEdit.getPortaria());

            QUESTAO_REPOSITORIO.save(questao);

            QuestaoResponse questaoResponse = QuestaoResponse
              .builder()
              .id(pedido.getId())
              .pergunta(questao.getPergunta())
              .portaria(questEdit.getPortaria())
              .objetiva(questEdit.getObjetiva())
              .respostas(responsesTipoRespostas)
              .build();

            responsesQuestao.add(questaoResponse);
          }
        }
      }

      MODELO_REPOSITORIO.save(modelo);

      ModeloResponse moldResponse = ModeloResponse
        .builder()
        .id(modelo.getId())
        .nome(modelo.getModeloNome())
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
