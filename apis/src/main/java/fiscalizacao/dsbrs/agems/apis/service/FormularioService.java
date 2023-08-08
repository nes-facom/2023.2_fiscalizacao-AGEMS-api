package fiscalizacao.dsbrs.agems.apis.service;

import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Imagem;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import fiscalizacao.dsbrs.agems.apis.dominio.TipoResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
import fiscalizacao.dsbrs.agems.apis.repositorio.FormularioRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ImagemRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.ModeloRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.QuestaoRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.RespostaRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;
import fiscalizacao.dsbrs.agems.apis.repositorio.UsuarioRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRegisterRequest;
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResumoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ImagemResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloFormResponse;
import fiscalizacao.dsbrs.agems.apis.responses.QuestaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.RespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.TipoRespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UsuarioFormResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormularioService {

  private final FormularioRepositorio FORMULARIO_REPOSITORIO;

  private final UsuarioRepositorio USUARIO_REPOSITORIO;

  private final ModeloRepositorio MODELO_REPOSITORIO;

  private final QuestaoRepositorio QUESTAO_REPOSITORIO;

  private final RespostaRepositorio RESPOSTA_REPOSITORIO;

  private final UnidadeRepositorio UNIDADE_REPOSITORIO;

  private final ImagemRepositorio IMAGEM_REPOSITORIO;
  private List<Questao> questoes;
  private List<Imagem> imagens;
  private FormularioResponse formularioResponse;
  private FormularioResumoResponse formularioResumoResponse;
  private UsuarioFormResponse usuarioResponse;
  private ModeloFormResponse modeloResponse;
  private UnidadeResponse unidadeResponse;
  private RespostaResponse respostaResponse;
  private QuestaoResponse questaoResponse;
  private ImagemResponse imagemResponse;
  private Formulario formulario;

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

  public Response cadastraFormulario(
    HttpServletRequest request,
    FormularioRegisterRequest novoFormulario
  ) {
    List<RespostaResponse> responsesResposta = new ArrayList<>();
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();
    Usuario usuario = extrairUsuarioEmailHeader(request);
    if (usuario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Usuário não existe")
        .build();
    }
    Modelo modelo = MODELO_REPOSITORIO
      .findById(novoFormulario.getModelo())
      .orElse(null);
    if (modelo == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Modelo não existe")
        .build();
    }

    Unidade unidade = UNIDADE_REPOSITORIO
      .findById(novoFormulario.getUnidade())
      .orElse(null);
    if (unidade == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Unidade não existe")
        .build();
    }
    if (novoFormulario.getImagens().size() < 4) {
      return ErroResponse
        .builder()
        .status(400)
        .erro("No mínimo 4 imagens.")
        .build();
    }
    formulario = new Formulario();
    formulario.setUsuario(usuario);
    formulario.setModelo(modelo);
    formulario.setUnidade(unidade);
    formulario.setDate();
    if (
      novoFormulario.getObservacao().isEmpty() ||
      (novoFormulario.getObservacao()).isBlank() ||
      (novoFormulario.getObservacao()).equals(null)
    ) {
      formulario.setObservacao("");
    } else {
      formulario.setObservacao(novoFormulario.getObservacao());
    }

    questoes =
      MODELO_REPOSITORIO
        .findById(novoFormulario.getModelo())
        .get()
        .getPerguntas();
    if (questoes.size() != novoFormulario.getRespostas().size()) {
      return ErroResponse
        .builder()
        .status(400)
        .erro("Mande o número correto de respostas.")
        .build();
    }
    Formulario newForm = FORMULARIO_REPOSITORIO.save(formulario);
    List<ImagemResponse> imagensResponses = new ArrayList<>();
    for (
      int numImagem = 0;
      numImagem < novoFormulario.getImagens().size();
      numImagem++
    ) {
      if (novoFormulario.getImagens().get(numImagem).getQuestao() == 0) {
        Imagem imagem = new Imagem();
        imagem.setFormulario(formulario);
        imagem.setImagem(
          novoFormulario.getImagens().get(numImagem).getImagem()
        );
        imagem.setDate();
        Imagem newImagem = IMAGEM_REPOSITORIO.save(imagem);
        imagemResponse =
          ImagemResponse
            .builder()
            .id(newImagem.getId())
            .formulario(newForm.getId())
            .imagem(imagem.getImagem())
            .questao(0)
            .build();
        imagensResponses.add(imagemResponse);
      } else {
        for (int questao = 0; questao < questoes.size(); questao++) {
          if (
            novoFormulario.getImagens().get(numImagem).getQuestao() != 0 &&
            novoFormulario.getImagens().get(numImagem).getQuestao() ==
            questoes.get(questao).getId()
          ) {
            Imagem imagem = new Imagem();
            imagem.setFormulario(formulario);
            imagem.setImagem(
              novoFormulario.getImagens().get(numImagem).getImagem()
            );
            imagem.setDate();
            imagem.setQuestao(
              QUESTAO_REPOSITORIO
                .findById(
                  novoFormulario.getImagens().get(numImagem).getQuestao()
                )
                .get()
            );
            Imagem newImagem = IMAGEM_REPOSITORIO.save(imagem);
            imagemResponse =
              ImagemResponse
                .builder()
                .id(newImagem.getId())
                .formulario(newForm.getId())
                .imagem(imagem.getImagem())
                .questao(imagem.getQuestao().getId())
                .build();
            imagensResponses.add(imagemResponse);
          }
        }
      }
    }
    for (int questao = 0; questao < questoes.size(); questao++) {
      for (RespostaRequest respostaRequest : novoFormulario.getRespostas()) {
        if (respostaRequest.getQuestao() == questoes.get(questao).getId()) {
          Resposta resposta = new Resposta();
          resposta.setFormulario(formulario);
          resposta.setQuestao(questoes.get(questao));
          List<TipoRespostaResponse> responsetipoRespostas = new ArrayList<>();

          List<TipoResposta> tipoRespostas = questoes
            .get(questao)
            .getRespostas();

          for (TipoResposta tipoRes : tipoRespostas) {
            TipoRespostaResponse responseTipo = TipoRespostaResponse
              .builder()
              .id(tipoRes.getId())
              .resposta(tipoRes.getResposta())
              .idQuestao(tipoRes.getQuestao().getId())
              .build();

            responsetipoRespostas.add(responseTipo);
          }
          questaoResponse =
            QuestaoResponse
              .builder()
              .id(questoes.get(questao).getId())
              .pergunta(questoes.get(questao).getPergunta())
              .idModelo(questoes.get(questao).getModelo().getId())
              .portaria(questoes.get(questao).getPortaria())
              .objetiva(questoes.get(questao).isObjetiva())
              .respostas(responsetipoRespostas)
              .build();
          responsesQuestao.add(questaoResponse);
          resposta.setResposta(
            novoFormulario.getRespostas().get(questao).getResposta()
          );
          resposta.setObs(novoFormulario.getRespostas().get(questao).getObs());
          RESPOSTA_REPOSITORIO.save(resposta);
          respostaResponse =
            RespostaResponse
              .builder()
              .questao(resposta.getQuestao().getId())
              .resposta(resposta.getResposta())
              .obs(resposta.getObs())
              .build();
          responsesResposta.add(respostaResponse);
        }
      }
    }
    usuarioResponse =
      UsuarioFormResponse
        .builder()
        .nome(formulario.getUsuario().getNome())
        .build();
    modeloResponse =
      ModeloFormResponse
        .builder()
        .nome(formulario.getModelo().getModeloNome())
        .questoes(responsesQuestao)
        .build();
    unidadeResponse =
      UnidadeResponse
        .builder()
        .id(formulario.getUnidade().getId())
        .endereco(formulario.getUnidade().getEndereco())
        .idUnidade(formulario.getUnidade().getIdUnidade())
        .build();
    formularioResponse =
      FormularioResponse
        .builder()
        .id(newForm.getId())
        .usuario(usuarioResponse)
        .unidade(unidadeResponse)
        .modelo(modeloResponse)
        .respostas(responsesResposta)
        .imagens(imagensResponses)
        .observacao(newForm.getObservacao())
        .build();

    return formularioResponse;
  }

  public List<FormularioResumoResponse> listaTodosFormularios(
    HttpServletRequest request
  ) {
    Usuario usuario = extrairUsuarioEmailHeader(request);
    List<FormularioResumoResponse> responsesFormulario = new ArrayList<>();
    List<Formulario> formularios = FORMULARIO_REPOSITORIO.findByUsuario(
      usuario
    );
    for (Formulario formulario : formularios) {
      formularioResumoResponse =
        FormularioResumoResponse.builder().id(formulario.getId()).build();
      responsesFormulario.add(formularioResumoResponse);
    }
    return responsesFormulario;
  }

  public Response verFormulario(HttpServletRequest request, int pedido) {
    Usuario usuario = extrairUsuarioEmailHeader(request);
    if (usuario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Usuário não existe")
        .build();
    }
    Formulario formulario = FORMULARIO_REPOSITORIO
      .findById(pedido)
      .orElse(null);

    if (formulario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Formulário não existe")
        .build();
    }
    if (usuario.getId() != formulario.getUsuario().getId()) {
      return ErroResponse
        .builder()
        .status(403)
        .erro("Você não tem permissão para acessar formulários de outros")
        .build();
    }
    usuarioResponse =
      UsuarioFormResponse
        .builder()
        .nome(formulario.getUsuario().getNome())
        .build();
    List<RespostaResponse> responsesResposta = new ArrayList<>();
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();
    List<ImagemResponse> imagemResponses = new ArrayList<>();
    imagens = IMAGEM_REPOSITORIO.findByFormulario(formulario);
    for (Imagem imagem : imagens) {
      if (imagem.getQuestao() == null) {
        imagemResponse =
          ImagemResponse
            .builder()
            .id(imagem.getId())
            .formulario(imagem.getFormulario().getId())
            .imagem(imagem.getImagem())
            .questao(0)
            .build();
        imagemResponses.add(imagemResponse);
      } else {
        imagemResponse =
          ImagemResponse
            .builder()
            .id(imagem.getId())
            .formulario(imagem.getFormulario().getId())
            .questao(imagem.getQuestao().getId())
            .imagem(imagem.getImagem())
            .build();
        imagemResponses.add(imagemResponse);
      }
    }

    Modelo modelo = MODELO_REPOSITORIO
      .findById(formulario.getModelo().getId())
      .get();

    unidadeResponse =
      UnidadeResponse
        .builder()
        .id(formulario.getUnidade().getId())
        .endereco(formulario.getUnidade().getEndereco())
        .idUnidade(formulario.getUnidade().getIdUnidade())
        .build();
    for (Questao questao : modelo.getPerguntas()) {
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
      questaoResponse =
        QuestaoResponse
          .builder()
          .id(questao.getId())
          .pergunta(questao.getPergunta())
          .idModelo(questao.getModelo().getId())
          .portaria(questao.getPortaria())
          .objetiva(questao.isObjetiva())
          .respostas(responsetipoRespostas)
          .build();
      responsesQuestao.add(questaoResponse);
    }

    modeloResponse =
      ModeloFormResponse
        .builder()
        .nome(formulario.getModelo().getModeloNome())
        .questoes(responsesQuestao)
        .build();
    List<Resposta> respostas = RESPOSTA_REPOSITORIO.findByFormulario(
      formulario
    );
    for (Resposta resposta : respostas) {
      respostaResponse =
        RespostaResponse
          .builder()
          .questao(resposta.getQuestao().getId())
          .resposta(resposta.getResposta())
          .obs(resposta.getObs())
          .build();
      responsesResposta.add(respostaResponse);
    }
    formularioResponse =
      FormularioResponse
        .builder()
        .id(formulario.getId())
        .usuario(usuarioResponse)
        .unidade(unidadeResponse)
        .modelo(modeloResponse)
        .respostas(responsesResposta)
        .imagens(imagemResponses)
        .observacao(formulario.getObservacao())
        .build();
    return formularioResponse;
  }

  public Response deletaFormulario(HttpServletRequest request, int pedido) {
    Usuario usuario = extrairUsuarioEmailHeader(request);
    if (usuario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Usuário não existe")
        .build();
    }
    Formulario formulario = FORMULARIO_REPOSITORIO
      .findById(pedido)
      .orElse(null);
    if (formulario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Formulário não existe")
        .build();
    }
    if (usuario.getId() != formulario.getUsuario().getId()) {
      return ErroResponse
        .builder()
        .status(403)
        .erro("Você não tem permissão para acessar formulários de outros")
        .build();
    }
    List<RespostaResponse> respostasResponses = new ArrayList<>();
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();
    List<ImagemResponse> imagemResponses = new ArrayList<>();
    imagens = IMAGEM_REPOSITORIO.findByFormulario(formulario);
    for (Imagem imagem : imagens) {
      if (imagem.getQuestao() == null) {
        imagemResponse =
          ImagemResponse
            .builder()
            .id(imagem.getId())
            .formulario(imagem.getFormulario().getId())
            .imagem(imagem.getImagem())
            .questao(0)
            .build();
        imagemResponses.add(imagemResponse);
      } else {
        imagemResponse =
          ImagemResponse
            .builder()
            .id(imagem.getId())
            .formulario(imagem.getFormulario().getId())
            .questao(imagem.getQuestao().getId())
            .imagem(imagem.getImagem())
            .build();
        imagemResponses.add(imagemResponse);
      }
    }
    Modelo modelo = MODELO_REPOSITORIO
      .findById(formulario.getModelo().getId())
      .get();
    usuarioResponse =
      UsuarioFormResponse
        .builder()
        .nome(formulario.getUsuario().getNome())
        .build();

    unidadeResponse =
      UnidadeResponse
        .builder()
        .id(formulario.getUnidade().getId())
        .endereco(formulario.getUnidade().getEndereco())
        .idUnidade(formulario.getUnidade().getIdUnidade())
        .build();

    for (Questao questao : modelo.getPerguntas()) {
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
      questaoResponse =
        QuestaoResponse
          .builder()
          .id(questao.getId())
          .pergunta(questao.getPergunta())
          .idModelo(questao.getModelo().getId())
          .portaria(questao.getPortaria())
          .objetiva(questao.isObjetiva())
          .respostas(responsetipoRespostas)
          .build();
      responsesQuestao.add(questaoResponse);
    }

    modeloResponse =
      ModeloFormResponse
        .builder()
        .nome(formulario.getModelo().getModeloNome())
        .questoes(responsesQuestao)
        .build();
    List<Resposta> respostas = RESPOSTA_REPOSITORIO.findByFormulario(
      formulario
    );
    for (Resposta resposta : respostas) {
      respostaResponse =
        RespostaResponse
          .builder()
          .questao(resposta.getQuestao().getId())
          .resposta(resposta.getResposta())
          .obs(resposta.getObs())
          .build();
      respostasResponses.add(respostaResponse);
    }
    formularioResponse =
      FormularioResponse
        .builder()
        .id(formulario.getId())
        .usuario(usuarioResponse)
        .unidade(unidadeResponse)
        .modelo(modeloResponse)
        .imagens(imagemResponses)
        .respostas(respostasResponses)
        .build();

    for (Imagem imagem : imagens) {
      IMAGEM_REPOSITORIO.delete(imagem);
    }
    for (Resposta resposta : respostas) {
      RESPOSTA_REPOSITORIO.delete(resposta);
    }
    FORMULARIO_REPOSITORIO.delete(formulario);
    return FormularioAcaoResponse
      .builder()
      .acao("Formulário deletado")
      .formulario(formularioResponse)
      .build();
  }

  public Response editaFormulario(
    HttpServletRequest request,
    int idFormulario,
    FormularioRequest pedido
  ) {
    Usuario usuario = extrairUsuarioEmailHeader(request);
    if (usuario == null) {
      return ErroResponse
        .builder()
        .status(404)
        .erro("Usuário não existe")
        .build();
    }
    if (pedido.getId() != idFormulario) {
      return ErroResponse
        .builder()
        .status(400)
        .erro("Requisição mal formada - Ids de formulários divergem.")
        .build();
    }
    Formulario formulario = FORMULARIO_REPOSITORIO
      .findById(pedido.getId())
      .orElse(null);
    if (formulario != null) {
      if (usuario.getId() != formulario.getUsuario().getId()) {
        return ErroResponse
          .builder()
          .status(403)
          .erro("Você não tem permissão para acessar formulários de outros")
          .build();
      }
      List<RespostaResponse> respostasResponses = new ArrayList<>();
      List<QuestaoResponse> responsesQuestao = new ArrayList<>();
      List<ImagemResponse> imagemResponses = new ArrayList<>();
      imagens = IMAGEM_REPOSITORIO.findByFormulario(formulario);
      if (pedido.getImagens().size() != 0) {
        for (int index = 0; index < pedido.getImagens().size(); index++) {
          int id = pedido.getImagens().get(index).getId();
          if (
            pedido.getImagens().get(index).getAcao().equalsIgnoreCase("delete")
          ) {
            if (imagens.size() - 1 >= 4) {
              for (Imagem imagem : imagens) {
                if (imagem.getId() == id) {
                  IMAGEM_REPOSITORIO.delete(imagem);
                  imagens.remove(imagem);
                }
              }
            }
          } else if (
            pedido.getImagens().get(index).getAcao().equalsIgnoreCase("edit")
          ) {
            String img = pedido.getImagens().get(index).getImagem();
            for (Imagem imagem : imagens) {
              if (imagem.getId() == id) {
                imagem.setImagem(img);
                IMAGEM_REPOSITORIO.save(imagem);
              }
            }
          }
        }
      }
      for (Imagem imagem : imagens) {
        if (imagem.getQuestao() == null) {
          imagemResponse =
            ImagemResponse
              .builder()
              .id(imagem.getId())
              .formulario(imagem.getFormulario().getId())
              .imagem(imagem.getImagem())
              .questao(0)
              .build();
          imagemResponses.add(imagemResponse);
        } else {
          imagemResponse =
            ImagemResponse
              .builder()
              .id(imagem.getId())
              .formulario(imagem.getFormulario().getId())
              .questao(imagem.getQuestao().getId())
              .imagem(imagem.getImagem())
              .build();
          imagemResponses.add(imagemResponse);
        }
      }

      Modelo modelo = MODELO_REPOSITORIO
        .findById(formulario.getModelo().getId())
        .get();
      usuarioResponse =
        UsuarioFormResponse
          .builder()
          .nome(formulario.getUsuario().getNome())
          .build();

      unidadeResponse =
        UnidadeResponse
          .builder()
          .id(formulario.getUnidade().getId())
          .endereco(formulario.getUnidade().getEndereco())
          .idUnidade(formulario.getUnidade().getIdUnidade())
          .build();
      for (Questao questao : modelo.getPerguntas()) {
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
        questaoResponse =
          QuestaoResponse
            .builder()
            .id(questao.getId())
            .pergunta(questao.getPergunta())
            .idModelo(questao.getModelo().getId())
            .portaria(questao.getPortaria())
            .objetiva(questao.isObjetiva())
            .respostas(responsetipoRespostas)
            .build();
        responsesQuestao.add(questaoResponse);
      }
      modeloResponse =
        ModeloFormResponse
          .builder()
          .nome(formulario.getModelo().getModeloNome())
          .questoes(responsesQuestao)
          .build();
      List<Resposta> respostas = RESPOSTA_REPOSITORIO.findByFormulario(
        formulario
      );
      if (pedido.getRespostas().size() != 0) {
        for (int index = 0; index < pedido.getRespostas().size(); index++) {
          int id = pedido.getRespostas().get(index).getQuestao();
          String textoResposta = pedido.getRespostas().get(index).getResposta();
          String obs = pedido.getRespostas().get(index).getObs();
          for (Resposta resposta : respostas) {
            if (resposta.getQuestao().getId() == id) {
              resposta.setResposta(textoResposta);
              resposta.setObs(obs);
              RESPOSTA_REPOSITORIO.save(resposta);
            } 
          }
        }
      }
      for (Resposta resposta : respostas) {
        respostaResponse =
          RespostaResponse
            .builder()
            .questao(resposta.getQuestao().getId())
            .resposta(resposta.getResposta())
            .obs(resposta.getObs())
            .build();
        respostasResponses.add(respostaResponse);
      }
      if (!formulario.getObservacao().equals(pedido.getObservacao())) {
        formulario.setObservacao(pedido.getObservacao());
      }

      formularioResponse =
        FormularioResponse
          .builder()
          .id(formulario.getId())
          .usuario(usuarioResponse)
          .unidade(unidadeResponse)
          .modelo(modeloResponse)
          .imagens(imagemResponses)
          .respostas(respostasResponses)
          .observacao(formulario.getObservacao())
          .build();

      for (Imagem imagem : imagens) {
        IMAGEM_REPOSITORIO.save(imagem);
      }
      for (Resposta resposta : respostas) {
        RESPOSTA_REPOSITORIO.save(resposta);
      }
      FORMULARIO_REPOSITORIO.save(formulario);
      return FormularioAcaoResponse
        .builder()
        .acao("Formulário editado")
        .formulario(formularioResponse)
        .build();
    }
    return ErroResponse
      .builder()
      .status(404)
      .erro("Formulário não existe.")
      .build();
  }
}
