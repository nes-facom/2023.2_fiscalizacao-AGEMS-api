package fiscalizacao.dsbrs.agems.apis.service;

import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.UnidadeRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnidadeService {

  @Autowired
  private final UnidadeRepositorio UNIDADE_REPOSITORIO;

  public Response cadastraUnidade(UnidadeRequest unidadeRegisterRequest) {
    if (
      unidadeRegisterRequest.getIdUnidade() == null ||
      unidadeRegisterRequest.getEndereco() == null ||
      unidadeRegisterRequest.getTipo() == null
    ) {
      return ErroResponse
        .builder()
        .erro("Id, Endere\u00e7o e Tipo obrigat\u00f3rios")
        .status(HttpStatus.BAD_REQUEST.value())
        .build();
    }

    Unidade unidade = UNIDADE_REPOSITORIO
      .findByIdUnidade(unidadeRegisterRequest.getIdUnidade())
      .orElse(null);
    if (unidade == null) {
      unidade =
        Unidade
          .builder()
          .idUnidade(unidadeRegisterRequest.getIdUnidade())
          .endereco(unidadeRegisterRequest.getEndereco())
          .tipo(unidadeRegisterRequest.getTipo())
          .build();

      Unidade unidadeSalva = UNIDADE_REPOSITORIO.save(unidade);

      UnidadeResponse unidadeResponse = UnidadeResponse
        .builder()
        .id(unidadeSalva.getId())
        .idUnidade(unidadeSalva.getIdUnidade())
        .endereco(unidadeSalva.getEndereco())
        .tipo(unidadeSalva.getTipo())
        .build();
      return unidadeResponse;
    }

    ErroResponse erroResponse = ErroResponse
      .builder()
      .status(HttpStatus.CONFLICT.value())
      .erro("Unidade j\u00e1 existe")
      .build();

    return erroResponse;
  }

  public Response verUnidade(String id) {
    if (!NumberUtils.isParsable(id)) {
      ErroResponse erroResponse = ErroResponse
        .builder()
        .status(400)
        .erro("Envie o id Numérico da Unidade!")
        .build();

      return erroResponse;
    }
    int idNum = Integer.parseInt(id);
    Unidade unidade = UNIDADE_REPOSITORIO.findById(idNum).orElse(null);
    if (unidade != null) {
      UnidadeResponse unidadeResponse = UnidadeResponse
        .builder()
        .id(unidade.getId())
        .idUnidade(unidade.getIdUnidade())
        .endereco(unidade.getEndereco())
        .tipo(unidade.getTipo())
        .build();
      return unidadeResponse;
    }

    ErroResponse erroResponse = ErroResponse
      .builder()
      .status(404)
      .erro("Unidade não existe")
      .build();

    return erroResponse;
  }

  public Response deletarUnidade(String id) {
    if (!NumberUtils.isParsable(id)) {
      ErroResponse erroResponse = ErroResponse
        .builder()
        .status(400)
        .erro("Envie o id Numérico da Unidade!")
        .build();

      return erroResponse;
    }
    int idNum = Integer.parseInt(id);
    Unidade unidade = UNIDADE_REPOSITORIO.findById(idNum).orElse(null);
    if (unidade != null) {
      UNIDADE_REPOSITORIO.deleteById(idNum);
      UnidadeResponse unidadeResponse = UnidadeResponse
        .builder()
        .id(unidade.getId())
        .idUnidade(unidade.getIdUnidade())
        .endereco(unidade.getEndereco())
        .tipo(unidade.getTipo())
        .build();
      return unidadeResponse;
    }

    ErroResponse erroResponse = ErroResponse
      .builder()
      .status(404)
      .erro("Unidade não existe")
      .build();

    return erroResponse;
  }

  public Response editarUnidade(String id, UnidadeRequest unidadeRequest) {
    if (!NumberUtils.isParsable(id)) {
      ErroResponse erroResponse = ErroResponse
        .builder()
        .status(400)
        .erro("Envie o id Numérico da Unidade!")
        .build();

      return erroResponse;
    }
    int idNum = Integer.parseInt(id);
    Unidade unidade = UNIDADE_REPOSITORIO.findById(idNum).orElse(null);
    if (unidade != null) {
      if (
        (
          unidadeRequest.getIdUnidade() == null ||
          (
            unidadeRequest.getIdUnidade().isEmpty() ||
            unidadeRequest.getIdUnidade().isBlank()
          )
        ) &&
        (
          unidadeRequest.getEndereco() == null ||
          (
            unidadeRequest.getEndereco().isEmpty() ||
            unidadeRequest.getEndereco().isBlank()
          )
        ) &&
        (
          unidadeRequest.getTipo() == null ||
          (
            unidadeRequest.getTipo().isEmpty() ||
            unidadeRequest.getTipo().isBlank()
          )
        )
      ) {
        ErroResponse erroResponse = ErroResponse
          .builder()
          .status(400)
          .erro(
            "Não foi possível atualizar nem o nome, nem o endereço, nem o tipo."
          )
          .build();

        return erroResponse;
      } else if (
        (
          unidadeRequest.getIdUnidade() == null ||
          (
            unidadeRequest.getIdUnidade().isEmpty() ||
            unidadeRequest.getIdUnidade().isBlank()
          )
        ) &&
        (
          (
            unidadeRequest.getEndereco() != null &&
            !unidadeRequest.getEndereco().isEmpty() &&
            !unidadeRequest.getEndereco().isBlank()
          )
        ) &&
        (
          unidadeRequest.getTipo() == null ||
          (
            unidadeRequest.getTipo().isEmpty() ||
            unidadeRequest.getTipo().isBlank()
          )
        )
      ) {


        unidade.setEndereco(unidadeRequest.getEndereco());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      } else if (
        (
          unidadeRequest.getIdUnidade() == null ||
          (
            unidadeRequest.getIdUnidade().isEmpty() ||
            unidadeRequest.getIdUnidade().isBlank()
          )
        ) &&
        (
          (
            unidadeRequest.getEndereco() == null ||
            (
              unidadeRequest.getEndereco().isEmpty() ||
              unidadeRequest.getEndereco().isBlank()
            )
          )
        ) &&
        (
          unidadeRequest.getTipo() != null &&
          !unidadeRequest.getTipo().isEmpty() &&
          !unidadeRequest.getTipo().isBlank()
        )
      ) {

        unidade.setTipo(unidadeRequest.getTipo());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      } else if (
        (
          unidadeRequest.getIdUnidade() != null &&
          !unidadeRequest.getIdUnidade().isEmpty() &&
          !unidadeRequest.getIdUnidade().isBlank()
        ) &&
        (
          unidadeRequest.getEndereco() == null ||
          (
            unidadeRequest.getEndereco().isEmpty() ||
            unidadeRequest.getEndereco().isBlank()
          )
        ) &&
        (
          unidadeRequest.getTipo() == null ||
          (
            unidadeRequest.getTipo().isEmpty() ||
            unidadeRequest.getTipo().isBlank()
          )
        )
      ) {
        Unidade outraUnidadeExiste = UNIDADE_REPOSITORIO
          .findByIdUnidade(unidadeRequest.getIdUnidade())
          .orElse(null);
        if (outraUnidadeExiste != null) {
          if (unidade.getId() != outraUnidadeExiste.getId()) {
            return ErroResponse
              .builder()
              .status(409)
              .erro(
                "Já existe uma outra unidade com esse identificador com outras informações"
              )
              .build();
          }
        }
        unidade.setIdUnidade(unidadeRequest.getIdUnidade());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      } else if (
        (
          unidadeRequest.getIdUnidade() != null &&
          !unidadeRequest.getIdUnidade().isEmpty() &&
          !unidadeRequest.getIdUnidade().isBlank()
        ) &&
        (
          unidadeRequest.getEndereco() == null ||
          (
            unidadeRequest.getEndereco().isEmpty() ||
            unidadeRequest.getEndereco().isBlank()
          )
        ) &&
        (
          unidadeRequest.getTipo() != null &&
          !unidadeRequest.getTipo().isEmpty() &&
          !unidadeRequest.getTipo().isBlank()
        )
      ) {
        Unidade outraUnidadeExiste = UNIDADE_REPOSITORIO
          .findByIdUnidade(unidadeRequest.getIdUnidade())
          .orElse(null);
        if (outraUnidadeExiste != null) {
          if (unidade.getId() != outraUnidadeExiste.getId()) {
            return ErroResponse
              .builder()
              .status(409)
              .erro(
                "Já existe uma outra unidade com esse identificador com outras informações"
              )
              .build();
          }
        }
        unidade.setIdUnidade(unidadeRequest.getIdUnidade());
        unidade.setTipo(unidadeRequest.getTipo());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      } else if (
        (
          unidadeRequest.getIdUnidade() == null ||
          (
            unidadeRequest.getIdUnidade().isEmpty() ||
            unidadeRequest.getIdUnidade().isBlank()
          )
        ) &&
        (
          unidadeRequest.getEndereco() != null &&
          !unidadeRequest.getEndereco().isEmpty() &&
          !unidadeRequest.getEndereco().isBlank()
        ) &&
        (
          unidadeRequest.getTipo() != null &&
          !unidadeRequest.getTipo().isEmpty() &&
          !unidadeRequest.getTipo().isBlank()
        )
      ) {
        unidade.setEndereco(unidadeRequest.getEndereco());
        unidade.setTipo(unidadeRequest.getTipo());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      } else if (
        (
          unidadeRequest.getIdUnidade() != null &&
          !unidadeRequest.getIdUnidade().isEmpty() &&
          !unidadeRequest.getIdUnidade().isBlank()
        ) &&
        (
          unidadeRequest.getEndereco() != null &&
          !unidadeRequest.getEndereco().isEmpty() &&
          !unidadeRequest.getEndereco().isBlank()
        ) &&
        (
          unidadeRequest.getTipo() == null ||
           (
              unidadeRequest.getTipo().isEmpty() ||
              unidadeRequest.getTipo().isBlank()
            )
        )
      ) {
        Unidade outraUnidadeExiste = UNIDADE_REPOSITORIO
          .findByIdUnidade(unidadeRequest.getIdUnidade())
          .orElse(null);
        if (outraUnidadeExiste != null) {
          if (unidade.getId() != outraUnidadeExiste.getId()) {
            return ErroResponse
              .builder()
              .status(409)
              .erro(
                "Já existe uma outra unidade com esse identificador com outras informações"
              )
              .build();
          }
        }
        unidade.setIdUnidade(unidadeRequest.getIdUnidade());
        unidade.setEndereco(unidadeRequest.getEndereco());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      } else {
        Unidade outraUnidadeExiste = UNIDADE_REPOSITORIO
          .findByIdUnidade(unidadeRequest.getIdUnidade())
          .orElse(null);
        if (outraUnidadeExiste != null) {
          if (unidade.getId() != outraUnidadeExiste.getId()) {
            return ErroResponse
              .builder()
              .status(409)
              .erro(
                "Já existe uma outra unidade com esse identificador com outras informações"
              )
              .build();
          }
        }
        unidade.setIdUnidade(unidadeRequest.getIdUnidade());
        unidade.setEndereco(unidadeRequest.getEndereco());
        unidade.setTipo(unidadeRequest.getTipo());
        Unidade unidadeAtualizada = UNIDADE_REPOSITORIO.save(unidade);
        UnidadeResponse unidadeResponse = UnidadeResponse
          .builder()
          .id(unidadeAtualizada.getId())
          .idUnidade(unidadeAtualizada.getIdUnidade())
          .endereco(unidadeAtualizada.getEndereco())
          .tipo(unidadeAtualizada.getTipo())
          .build();
        return unidadeResponse;
      }
    }

    ErroResponse erroResponse = ErroResponse
      .builder()
      .status(404)
      .erro("Unidade não existe")
      .build();

    return erroResponse;
  }

  public List<UnidadeResponse> listarUnidades() {
    List<UnidadeResponse> unidadeResponses = new ArrayList<>();
    Iterable<Unidade> unidades = UNIDADE_REPOSITORIO.findAll();
    for (Unidade unidade : unidades) {
      UnidadeResponse unidadeResponse = UnidadeResponse
        .builder()
        .id(unidade.getId())
        .idUnidade(unidade.getIdUnidade())
        .endereco(unidade.getEndereco())
        .tipo(unidade.getTipo())
        .build();
      unidadeResponses.add(unidadeResponse);
    }
    return unidadeResponses;
  }
}