package fiscalizacao.dsbrs.agems.apis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import fiscalizacao.dsbrs.agems.apis.dominio.AlternativaResposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Formulario;
import fiscalizacao.dsbrs.agems.apis.dominio.Imagem;
import fiscalizacao.dsbrs.agems.apis.dominio.Modelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Questao;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoFormulario;
import fiscalizacao.dsbrs.agems.apis.dominio.QuestaoModelo;
import fiscalizacao.dsbrs.agems.apis.dominio.Resposta;
import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.dominio.Usuario;
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
import fiscalizacao.dsbrs.agems.apis.requests.FormularioRequest;
import fiscalizacao.dsbrs.agems.apis.requests.RespostaRequest;
import fiscalizacao.dsbrs.agems.apis.responses.AlternativaRespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioAcaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioBuscaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioListResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResponse;
import fiscalizacao.dsbrs.agems.apis.responses.FormularioResumoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ImagemResponse;
import fiscalizacao.dsbrs.agems.apis.responses.ModeloFormResponse;
import fiscalizacao.dsbrs.agems.apis.responses.QuestaoResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.RespostaResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.responses.UsuarioFormResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FormularioService {

  private final FormularioRepositorio FORMULARIO_REPOSITORIO;

  private final UsuarioRepositorio USUARIO_REPOSITORIO;

  private final ModeloRepositorio MODELO_REPOSITORIO;

  private final QuestaoRepositorio QUESTAO_REPOSITORIO;
  
  private final QuestaoModeloRepositorio QUESTAO_MODELO_REPOSITORIO;
  
  private final QuestaoFormularioRepositorio QUESTAO_FORMULARIO_REPOSITORIO;

  private final RespostaRepositorio RESPOSTA_REPOSITORIO;

  private final UnidadeRepositorio UNIDADE_REPOSITORIO;

  private final ImagemRepositorio IMAGEM_REPOSITORIO;
  
  private List<Imagem> imagens;
  private FormularioResponse formularioResponse;
  private FormularioResumoResponse formularioResumoResponse;
  private UsuarioFormResponse usuarioResponse;
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

  public Response cadastraFormulario(HttpServletRequest request, FormularioRegisterRequest novoFormulario)
  {
	  
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

    formulario = new Formulario();
    formulario.setUsuarioCriacao(usuario);
    formulario.setUnidade(unidade);
    formulario.setDataCriacao(novoFormulario.getDataCriacao());

    if (
      (novoFormulario.getObservacao() == null) ||
      (novoFormulario.getObservacao()).isEmpty() ||
      (novoFormulario.getObservacao()).isBlank()
    ) {
      formulario.setObservacao("");
    } else {
      formulario.setObservacao(novoFormulario.getObservacao());
    }
    
    List<QuestaoModelo> questoesModelo =
      QUESTAO_MODELO_REPOSITORIO
        .findByModelo(modelo);
    
    if (questoesModelo.size() != novoFormulario.getRespostas().size()) {
      return ErroResponse
        .builder()
        .status(400)
        .erro("Mande o número correto de respostas.")
        .build();
    }

    Formulario novoFormularioAux = FORMULARIO_REPOSITORIO.save(formulario);
    for(QuestaoModelo questaoModelo : questoesModelo) {
      QuestaoFormulario questaoFormulario = new QuestaoFormulario();
      questaoFormulario.setFormulario(formulario);
      questaoFormulario.setQuestao(questaoModelo.getQuestao());
      QUESTAO_FORMULARIO_REPOSITORIO.save(questaoFormulario);
    }
    
    List<ImagemResponse> imagensResponses = new ArrayList<>();
    
    for (int numImagem = 0; numImagem < novoFormulario.getImagens().size(); numImagem++)
    {
	  Imagem imagem = new Imagem();
	  imagem.setFormulario(formulario);
	  imagem.setImagem(novoFormulario.getImagens().get(numImagem).getImagem());
	  imagem.setDataCriacao(novoFormulario.getDataCriacao());
	  imagem.setUsuarioCriacao(usuario);
	  Imagem novaImagem = IMAGEM_REPOSITORIO.save(imagem);
	  
	  imagemResponse =
	      ImagemResponse
	        .builder()
	        .id(novaImagem.getId())
	        .formulario(novoFormularioAux.getId())
	        .imagem(imagem.getImagem())
	        .build();
	  
	  imagensResponses.add(imagemResponse);
    }
    
    Map<UUID, QuestaoModelo> questaoMap = questoesModelo.stream().collect(Collectors.toMap(el -> el.getQuestao().getId(), el -> el));
    for (RespostaRequest respostaRequest : novoFormulario.getRespostas()) {
  	  QuestaoModelo questaoModelo = questaoMap.get(respostaRequest.getQuestao());
      if (questaoModelo != null) {
        Questao questao = questaoModelo.getQuestao();
        Resposta resposta = new Resposta();
        resposta.setFormulario(formulario);
        resposta.setQuestao(questao);
        List<AlternativaRespostaResponse> responseAlternativasResposta = new ArrayList<>();

        List<AlternativaResposta> alternativasResposta = questao.getAlternativasResposta();

        for (AlternativaResposta alternativa : alternativasResposta) {
          AlternativaRespostaResponse responseTipo = AlternativaRespostaResponse
            .builder()
            .id(alternativa.getId())
            .descricao(alternativa.getDescricao())
            .idQuestao(alternativa.getQuestao().getId())
            .build();

          responseAlternativasResposta.add(responseTipo);
        }
        
        questaoResponse =
          QuestaoResponse
            .builder()
            .id(questao.getId())
            .pergunta(questao.getPergunta())
            .portaria(questao.getPortaria())
            .objetiva(questao.isObjetiva())
            .respostas(responseAlternativasResposta)
            .build();
        
        responsesQuestao.add(questaoResponse);
        
        resposta.setResposta(respostaRequest.getResposta());
        
        resposta.setObservacao(respostaRequest.getObservacao());
        
        resposta.setUsuarioCriacao(usuario);
        
        resposta.setDataCriacao(novoFormulario.getDataCriacao());
        
        RESPOSTA_REPOSITORIO.save(resposta);
        
        respostaResponse =
          RespostaResponse
            .builder()
            .id(resposta.getId())
            .uuidLocal(respostaRequest.getUuidLocal())
            .resposta(resposta.getResposta())
            .obs(resposta.getObservacao())
            .questao(resposta.getQuestao().getId())
            .build();
        responsesResposta.add(respostaResponse);
      }
    }
    usuarioResponse =
      UsuarioFormResponse
        .builder()
        .nome(formulario.getUsuarioCriacao().getNome())
        .build();

    unidadeResponse =
      UnidadeResponse
        .builder()
        .id(formulario.getUnidade().getId())
        .endereco(formulario.getUnidade().getEndereco())
        .nome(formulario.getUnidade().getNome())
        .build();

    formularioResponse =
      FormularioResponse
        .builder()
        .id(novoFormularioAux.getId())
        .uuidLocal(novoFormulario.getUuidLocal())
        .usuario(usuarioResponse)
        .unidade(unidadeResponse)
        .respostas(responsesResposta)
        .imagens(imagensResponses)
        .observacao(novoFormularioAux.getObservacao())
        .dataCriacao(formulario.getDataCriacao())
        .build();

    return formularioResponse;
  }
  
  public FormularioListResponse listaTodosFormularios() {
    List<FormularioResponse> formulariosResponse = new ArrayList<>();
    List<Formulario> formularios = FORMULARIO_REPOSITORIO.findAll();
    for(Formulario form : formularios) {
      List<ImagemResponse> imagens = form.getImagens().stream()
          .map(imagem -> ImagemResponse.builder()
              .formulario(form.getId())
              .id(imagem.getId())
              .imagem(imagem.getImagem())
              .build())
          .collect(Collectors.toList());
      
      List<RespostaResponse> respostas = form.getRespostas().stream()
          .map(resposta -> RespostaResponse.builder()
              .questao(resposta.getQuestao().getId())
              .resposta(resposta.getResposta())
              .obs(resposta.getObservacao())
              .build())
          .collect(Collectors.toList());
      
      Unidade unidadeForm = form.getUnidade();
      UnidadeResponse unidadeResp = UnidadeResponse.builder()
          .endereco(unidadeForm.getEndereco())
          .id(unidadeForm.getId())
          .nome(unidadeForm.getNome())
          .tipo(unidadeForm.getTipo())
          .build();
      
      Usuario usuarioForm = form.getUsuarioCriacao();
      UsuarioFormResponse usuarioResp = UsuarioFormResponse.builder()
          .nome(usuarioForm.getNome())
          .build();
      
      formulariosResponse.add(FormularioResponse.builder()
          .dataCriacao(form.getDataCriacao())
          .id(form.getId())
          .imagens(imagens)
          .observacao(form.getObservacao())
          .respostas(respostas)
          .unidade(unidadeResp)
          .usuario(usuarioResp)
          .build());
    }
    FormularioListResponse response = FormularioListResponse.builder()
        .data(formulariosResponse)
        .build();
    return response;
  }

  public FormularioBuscaResponse listaTodosFormulariosResumidos(
    HttpServletRequest request,
    int pagina,
    int quantidade
  ) {
	      
    List<FormularioResumoResponse> responsesFormulario = new ArrayList<>();
    FormularioBuscaResponse response = new FormularioBuscaResponse();
    
    Page<Formulario> formularios = FORMULARIO_REPOSITORIO.findAll(PageRequest.of(pagina, quantidade));
    
    response.setPagina(pagina);
    response.setPaginaMax(Math.max(0, formularios.getTotalPages()-1));
    response.setFormularios(
        formularios.stream()
            .map(form -> new FormularioResumoResponse(
                            form.getId(),
                            form.getUnidade().getNome(),
                            form.getDataCriacao(),
                            form.getUsuarioCriacao().getNome()
                         )
             )
            .collect(Collectors.toList())
      );
    return response;
  }

  public Response verFormulario(HttpServletRequest request, UUID pedido) {
	  
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
    
    if (usuario.getId() != formulario.getUsuarioCriacao().getId()) {
      return ErroResponse
        .builder()
        .status(403)
        .erro("Você não tem permissão para acessar formulários de outros")
        .build();
    }
    
    usuarioResponse =
      UsuarioFormResponse
        .builder()
        .nome(formulario.getUsuarioCriacao().getNome())
        .build();
    
    List<RespostaResponse> responsesResposta = new ArrayList<>();
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();
    List<ImagemResponse> imagemResponses = new ArrayList<>();
    
    imagens = IMAGEM_REPOSITORIO.findByFormulario(formulario);
    
    for (Imagem imagem : imagens) {
      imagemResponse =
        ImagemResponse
          .builder()
          .id(imagem.getId())
          .formulario(imagem.getFormulario().getId())
          .imagem(imagem.getImagem())
          .build();
      imagemResponses.add(imagemResponse);
    }

    unidadeResponse =
      UnidadeResponse
        .builder()
        .id(formulario.getUnidade().getId())
        .endereco(formulario.getUnidade().getEndereco())
        .nome(formulario.getUnidade().getNome())
        .build();
    
    for (QuestaoFormulario questaoFormulario : formulario.getQuestoes()) {
    
      Questao questao = questaoFormulario.getQuestao();
    	
      List<AlternativaRespostaResponse> responseAlternativaRespostas = new ArrayList<>();

      List<AlternativaResposta> alternativaRespostas = questao.getAlternativasResposta();

      for (AlternativaResposta alternativa : alternativaRespostas) {
    	  
        AlternativaRespostaResponse responseTipo = AlternativaRespostaResponse
          .builder()
          .id(alternativa.getId())
          .descricao(alternativa.getDescricao())
          .idQuestao(questao.getId())
          .build();

        responseAlternativaRespostas.add(responseTipo);
      }
      questaoResponse =
        QuestaoResponse
          .builder()
          .id(questao.getId())
          .pergunta(questao.getPergunta())
          .portaria(questao.getPortaria())
          .objetiva(questao.isObjetiva())
          .respostas(responseAlternativaRespostas)
          .build();
      responsesQuestao.add(questaoResponse);
    }
    
    List<Resposta> respostas = RESPOSTA_REPOSITORIO.findByFormulario(
      formulario
    );
    
    for (Resposta resposta : respostas) {
      respostaResponse =
        RespostaResponse
          .builder()
          .questao(resposta.getQuestao().getId())
          .resposta(resposta.getResposta())
          .obs(resposta.getObservacao())
          .build();
      responsesResposta.add(respostaResponse);
    }
    formularioResponse =
      FormularioResponse
        .builder()
        .id(formulario.getId())
        .dataCriacao(formulario.getDataCriacao())
    	.usuario(usuarioResponse)
        .unidade(unidadeResponse)
        .respostas(responsesResposta)
        .imagens(imagemResponses)
        .observacao(formulario.getObservacao())
        .build();
    return formularioResponse;
  }

  public Response deletaFormulario(HttpServletRequest request, UUID pedido) {
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
    if (usuario.getId() != formulario.getUsuarioCriacao().getId()) {
      return ErroResponse
        .builder()
        .status(403)
        .erro("Você não tem permissão para acessar formulários de outros usuários")
        .build();
    }
    
    List<RespostaResponse> respostasResponses = new ArrayList<>();
    List<QuestaoResponse> responsesQuestao = new ArrayList<>();
    List<ImagemResponse> imagemResponses = new ArrayList<>();
    imagens = IMAGEM_REPOSITORIO.findByFormulario(formulario);
    
    for (Imagem imagem : imagens) {
      imagemResponse =
        ImagemResponse
          .builder()
          .id(imagem.getId())
          .formulario(imagem.getFormulario().getId())
          .imagem(imagem.getImagem())
          .build();
      imagemResponses.add(imagemResponse);
    }

    unidadeResponse =
      UnidadeResponse
        .builder()
        .id(formulario.getUnidade().getId())
        .endereco(formulario.getUnidade().getEndereco())
        .id(formulario.getUnidade().getId())
        .build();

    for (QuestaoFormulario questaoFormulario : formulario.getQuestoes()) {
    	
      Questao questao = questaoFormulario.getQuestao();
      
      List<AlternativaRespostaResponse> responseAlternativaRespostas = new ArrayList<>();

      List<AlternativaResposta> alternativasResposta = questao.getAlternativasResposta();

      for (AlternativaResposta alternativa : alternativasResposta) {
        AlternativaRespostaResponse responseTipo = AlternativaRespostaResponse
          .builder()
          .id(alternativa.getId())
          .descricao(alternativa.getDescricao())
          .idQuestao(alternativa.getQuestao().getId())
          .build();

        responseAlternativaRespostas.add(responseTipo);
      }
      questaoResponse =
        QuestaoResponse
          .builder()
          .id(questao.getId())
          .pergunta(questao.getPergunta())
          .portaria(questao.getPortaria())
          .objetiva(questao.isObjetiva())
          .respostas(responseAlternativaRespostas)
          .build();
      responsesQuestao.add(questaoResponse);
    }
    
    List<Resposta> respostas = RESPOSTA_REPOSITORIO.findByFormulario(formulario);
    
    for (Resposta resposta : respostas) {
      respostaResponse =
        RespostaResponse
          .builder()
          .questao(resposta.getQuestao().getId())
          .resposta(resposta.getResposta())
          .obs(resposta.getObservacao())
          .build();
      respostasResponses.add(respostaResponse);
    }
    formularioResponse =
      FormularioResponse
        .builder()
        .id(formulario.getId())
        .usuario(usuarioResponse)
        .unidade(unidadeResponse)
        .imagens(imagemResponses)
        .respostas(respostasResponses)
        .build();
    
    List<Questao> questoes = formulario.getQuestoes().stream()
        .map(QuestaoFormulario::getQuestao)
        .collect(Collectors.toList());
    
    FORMULARIO_REPOSITORIO.delete(formulario);

    for(Questao questao : questoes) {
      List<QuestaoModelo> questaoModeloList = QUESTAO_MODELO_REPOSITORIO.findByQuestao(questao);
      List<QuestaoFormulario> questaoFormularioList = QUESTAO_FORMULARIO_REPOSITORIO.findByQuestao(questao);
      if(questaoModeloList.size() == 0 && questaoFormularioList.size() == 0)
        QUESTAO_REPOSITORIO.delete(questao);
    }
    
    return FormularioAcaoResponse
      .builder()
      .acao("Formulário deletado")
      .formulario(formularioResponse)
      .build();
  }

  public Response editaFormulario(
    HttpServletRequest request,
    UUID idFormulario,
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
      if (usuario.getId() != formulario.getUsuarioCriacao().getId()) {
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
          UUID id = pedido.getImagens().get(index).getId();
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
        imagemResponse =
          ImagemResponse
            .builder()
            .id(imagem.getId())
            .formulario(imagem.getFormulario().getId())
            .imagem(imagem.getImagem())
            .build();
        imagemResponses.add(imagemResponse);
      }

      unidadeResponse =
        UnidadeResponse
          .builder()
          .id(formulario.getUnidade().getId())
          .endereco(formulario.getUnidade().getEndereco())
          .id(formulario.getUnidade().getId())
          .build();
      for (QuestaoFormulario questaoFomulario : formulario.getQuestoes()) {
    	  
    	Questao questao = questaoFomulario.getQuestao();
    	
        List<AlternativaRespostaResponse> responseAlternativaRespostas = new ArrayList<>();

        List<AlternativaResposta> alternativasResposta = questao.getAlternativasResposta();

        for (AlternativaResposta alternativa : alternativasResposta) {
          AlternativaRespostaResponse responseTipo = AlternativaRespostaResponse
            .builder()
            .id(alternativa.getId())
            .descricao(alternativa.getDescricao())
            .idQuestao(alternativa.getQuestao().getId())
            .build();

          responseAlternativaRespostas.add(responseTipo);
        }
        questaoResponse =
          QuestaoResponse
            .builder()
            .id(questao.getId())
            .pergunta(questao.getPergunta())
            .portaria(questao.getPortaria())
            .objetiva(questao.isObjetiva())
            .respostas(responseAlternativaRespostas)
            .build();
        responsesQuestao.add(questaoResponse);
      }
      
      List<Resposta> respostas = RESPOSTA_REPOSITORIO.findByFormulario(formulario);
      
      if (pedido.getRespostas().size() != 0) {
        for (int index = 0; index < pedido.getRespostas().size(); index++) {
          
          UUID id = pedido.getRespostas().get(index).getQuestao();
          String textoResposta = pedido.getRespostas().get(index).getResposta();
          String observacao = pedido.getRespostas().get(index).getObservacao();
          
          for (Resposta resposta : respostas) {
            if (resposta.getQuestao().getId() == id) {
              resposta.setResposta(textoResposta);
              resposta.setObservacao(observacao);
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
            .obs(resposta.getObservacao())
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
