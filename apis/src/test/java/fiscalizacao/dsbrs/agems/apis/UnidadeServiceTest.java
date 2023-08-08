package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import fiscalizacao.dsbrs.agems.apis.dominio.Unidade;
import fiscalizacao.dsbrs.agems.apis.repositorio.UnidadeRepositorio;
import fiscalizacao.dsbrs.agems.apis.requests.UnidadeRequest;
import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import fiscalizacao.dsbrs.agems.apis.responses.Response;
import fiscalizacao.dsbrs.agems.apis.responses.UnidadeResponse;
import fiscalizacao.dsbrs.agems.apis.service.UnidadeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class UnidadeServiceTest {

  @Mock
  private UnidadeRepositorio unidadeRepositorio;

  @InjectMocks
  private UnidadeService unidadeService;

  @Test
  public void adicionaUnidadeShouldReturnCreated() {
    UnidadeRequest request = new UnidadeRequest();
    request.setIdUnidade("123");
    request.setEndereco("Sample Address");
    request.setTipo("Tratamento de Esgoto");

    when(unidadeRepositorio.findByIdUnidade("123"))
      .thenReturn(java.util.Optional.empty());
    when(unidadeRepositorio.save(any(Unidade.class)))
      .thenReturn(
        new Unidade(1, "123", "Sample Address", "Tratamento de Esgoto")
      );

    Response response = unidadeService.cadastraUnidade(request);

    assertTrue(response instanceof UnidadeResponse);
    UnidadeResponse unidadeResponse = (UnidadeResponse) response;
    assertEquals(1, unidadeResponse.getId());
    assertEquals("123", unidadeResponse.getIdUnidade());
    assertEquals("Sample Address", unidadeResponse.getEndereco());
  }

  @Test
  public void adicionaUnidadeShouldReturnConflict() {
    UnidadeRequest request = new UnidadeRequest();
    request.setIdUnidade("123");
    request.setEndereco("Sample Address");
    request.setTipo("Tratamento de Esgoto");

    Unidade existingUnidade = new Unidade();
    existingUnidade.setIdUnidade("123");

    when(unidadeRepositorio.findByIdUnidade("123"))
      .thenReturn(java.util.Optional.of(existingUnidade));

    Response response = unidadeService.cadastraUnidade(request);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.CONFLICT.value(), erroResponse.getStatus());
    assertEquals("Unidade já existe", erroResponse.getErro());
  }

  @Test
  public void testAdicionaUnidadeReturnBadRequest() {
    UnidadeRequest request = new UnidadeRequest();
    Response response = unidadeService.cadastraUnidade(request);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.BAD_REQUEST.value(), erroResponse.getStatus());
    assertEquals(
      "Id, Endere\u00e7o e Tipo obrigat\u00f3rios",
      erroResponse.getErro()
    );
  }

  @Test
  public void testVerUnidade() {
    int id = 1;
    Unidade unidade = new Unidade();
    unidade.setId(id);
    unidade.setIdUnidade("123");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    when(unidadeRepositorio.findById(Integer.parseInt(unidade.getIdUnidade())))
      .thenReturn(Optional.of(unidade));

    Response response = unidadeService.verUnidade(unidade.getIdUnidade());
    assertTrue(response instanceof UnidadeResponse);
    UnidadeResponse unidadeResponse = (UnidadeResponse) response;

    assertEquals(unidade.getId(), unidadeResponse.getId());
    assertEquals(unidade.getIdUnidade(), unidadeResponse.getIdUnidade());
    assertEquals(unidade.getTipo(), unidadeResponse.getTipo());
    assertEquals(unidade.getEndereco(), unidadeResponse.getEndereco());
  }

  @Test
  public void testVerUnidadeIdInvalido() {
    String id = "invalidId";
    Response response = unidadeService.verUnidade(id);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.BAD_REQUEST.value(), erroResponse.getStatus());
    assertEquals(
      "Envie o id Num\u00E9rico da Unidade!",
      erroResponse.getErro()
    );
  }

  @Test
  public void testVerUnidadeNaoExiste() {
    String idUnidade = "123";
    when(unidadeRepositorio.findById(Integer.parseInt(idUnidade)))
      .thenReturn(Optional.empty());

    Response response = unidadeService.verUnidade(idUnidade);
    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.NOT_FOUND.value(), erroResponse.getStatus());
    assertEquals("Unidade n\u00E3o existe", erroResponse.getErro());
  }

  @Test
  public void testDeleteUnidade() {
    int id = 1;
    Unidade unidade = new Unidade();
    unidade.setId(id);
    unidade.setIdUnidade("123");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    when(unidadeRepositorio.findById(Integer.parseInt(unidade.getIdUnidade())))
      .thenReturn(Optional.of(unidade));

    Response response = unidadeService.deletarUnidade(unidade.getIdUnidade());
    assertTrue(response instanceof UnidadeResponse);
    UnidadeResponse unidadeResponse = (UnidadeResponse) response;

    assertEquals(unidade.getId(), unidadeResponse.getId());
    assertEquals(unidade.getIdUnidade(), unidadeResponse.getIdUnidade());
    assertEquals(unidade.getTipo(), unidadeResponse.getTipo());
    assertEquals(unidade.getEndereco(), unidadeResponse.getEndereco());
  }

  @Test
  public void testDeleteUnidadeIdInvalido() {
    String id = "invalidId";
    Response response = unidadeService.deletarUnidade(id);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.BAD_REQUEST.value(), erroResponse.getStatus());
    assertEquals(
      "Envie o id Num\u00E9rico da Unidade!",
      erroResponse.getErro()
    );
  }

  @Test
  public void testDeleteUnidadeNaoExiste() {
    String idUnidade = "123";
    when(unidadeRepositorio.findById(Integer.parseInt(idUnidade)))
      .thenReturn(Optional.empty());

    Response response = unidadeService.deletarUnidade(idUnidade);
    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.NOT_FOUND.value(), erroResponse.getStatus());
    assertEquals("Unidade n\u00E3o existe", erroResponse.getErro());
  }

  @Test
  public void testEditUnidadeIdInvalido() {
    String id = "invalidId";
    UnidadeRequest unidade = new UnidadeRequest();
    Response response = unidadeService.editarUnidade(id, unidade);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.BAD_REQUEST.value(), erroResponse.getStatus());
    assertEquals(
      "Envie o id Num\u00E9rico da Unidade!",
      erroResponse.getErro()
    );
  }

  @Test
  public void testEditUnidadeNaoExiste() {
    String idUnidade = "123";
    when(unidadeRepositorio.findById(Integer.parseInt(idUnidade)))
      .thenReturn(Optional.empty());

    UnidadeRequest unidade = new UnidadeRequest();
    Response response = unidadeService.editarUnidade(idUnidade, unidade);

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.NOT_FOUND.value(), erroResponse.getStatus());
    assertEquals("Unidade n\u00E3o existe", erroResponse.getErro());
  }

  @Test
  public void testEditUnidadeEmptyRequest() {
    int id = 1;
    Unidade unidade = new Unidade();
    unidade.setId(id);
    unidade.setIdUnidade("123");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    UnidadeRequest unidadeRequest = new UnidadeRequest();

    when(unidadeRepositorio.findById(Integer.parseInt(unidade.getIdUnidade())))
      .thenReturn(Optional.of(unidade));

    Response response = unidadeService.editarUnidade(
      unidade.getIdUnidade(),
      unidadeRequest
    );
    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.BAD_REQUEST.value(), erroResponse.getStatus());
    assertEquals(
      "N\u00E3o foi poss\u00EDvel atualizar nem o nome, nem o endere\u00E7o, nem o tipo.",
      erroResponse.getErro()
    );
  }

  @Test
  public void testEditUnidadeApenasEndereco() {
    int id = 1;
    Unidade unidade = new Unidade();
    unidade.setId(id);
    unidade.setIdUnidade("123");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setEndereco("new Address");

    when(unidadeRepositorio.findById(Integer.parseInt(unidade.getIdUnidade())))
      .thenReturn(Optional.of(unidade));
    when(unidadeRepositorio.save(any(Unidade.class))).thenReturn(unidade);

    Response response = unidadeService.editarUnidade(
      unidade.getIdUnidade(),
      unidadeRequest
    );

    assertTrue(response instanceof UnidadeResponse);
    UnidadeResponse unidadeResponse = (UnidadeResponse) response;

    assertEquals(unidade.getId(), unidadeResponse.getId());
    assertEquals(unidade.getIdUnidade(), unidadeResponse.getIdUnidade());
    assertEquals(unidade.getTipo(), unidadeResponse.getTipo());
    assertEquals(unidadeRequest.getEndereco(), unidadeResponse.getEndereco());
  }

  @Test
  public void testEditUnidadeApenasIdUnidadeJaExiste() {
    int id = 1;
    Unidade unidade = new Unidade();
    unidade.setId(id);
    unidade.setIdUnidade("123");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    int id2 = 2;
    Unidade unidade2 = new Unidade();
    unidade.setId(id2);
    unidade.setIdUnidade("1234");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setIdUnidade("1234");

    when(unidadeRepositorio.findById(Integer.parseInt(unidade.getIdUnidade())))
      .thenReturn(Optional.of(unidade));
    when(unidadeRepositorio.findByIdUnidade(unidadeRequest.getIdUnidade()))
      .thenReturn(Optional.of(unidade2));

    Response response = unidadeService.editarUnidade(
      unidade.getIdUnidade(),
      unidadeRequest
    );

    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(HttpStatus.CONFLICT.value(), erroResponse.getStatus());
    assertEquals(
      "J\u00E1 existe uma outra unidade com esse identificador com outras informa\u00E7\u00F5es",
      erroResponse.getErro()
    );
  }

  @Test
  public void testEditUnidadeApenasIdUnidade() {
    int id = 1;
    Unidade unidade = new Unidade();
    unidade.setId(id);
    unidade.setIdUnidade("123");
    unidade.setEndereco("Sample Address");
    unidade.setTipo("Tratamento de Esgoto");

    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setIdUnidade("1234");

    when(unidadeRepositorio.findById(Integer.parseInt(unidade.getIdUnidade())))
      .thenReturn(Optional.of(unidade));
    when(unidadeRepositorio.save(any(Unidade.class))).thenReturn(unidade);

    Response response = unidadeService.editarUnidade(
      unidade.getIdUnidade(),
      unidadeRequest
    );

    assertTrue(response instanceof UnidadeResponse);
    UnidadeResponse unidadeResponse = (UnidadeResponse) response;

    assertEquals(unidade.getId(), unidadeResponse.getId());
    assertEquals(unidadeRequest.getIdUnidade(), unidadeResponse.getIdUnidade());
    assertEquals(unidade.getTipo(), unidadeResponse.getTipo());
    assertEquals(unidade.getEndereco(), unidadeResponse.getEndereco());
  }

  @Test
  public void testEditarUnidade_ExistingUnidade_UpdatesFields() {
    // Arrange
    int id = 1;
    String idString = "1";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setEndereco("New Address");

    Unidade existingUnidade = new Unidade();
    existingUnidade.setId(id);
    existingUnidade.setEndereco("Old Address");

    when(unidadeRepositorio.findById(id))
      .thenReturn(Optional.of(existingUnidade));
    when(unidadeRepositorio.save(any(Unidade.class)))
      .thenReturn(existingUnidade);

    // Act
    Response response = unidadeService.editarUnidade(idString, unidadeRequest);

    // Assert
    assertTrue(response instanceof UnidadeResponse);
    UnidadeResponse unidadeResponse = (UnidadeResponse) response;
    assertEquals(id, unidadeResponse.getId());
    assertEquals("New Address", unidadeResponse.getEndereco());
    assertNull(unidadeResponse.getTipo());

    verify(unidadeRepositorio, times(1)).findById(id);
    verify(unidadeRepositorio, times(1)).save(existingUnidade);
  }

  @Test
  public void testEditarUnidade_NonExistingUnidade_ReturnsErroResponse() {
    // Arrange
    int id = 1;
    String idString = "1";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setEndereco("New Address");

    when(unidadeRepositorio.findById(id)).thenReturn(Optional.empty());

    // Act
    Response response = unidadeService.editarUnidade(idString, unidadeRequest);

    // Assert
    assertTrue(response instanceof ErroResponse);
    ErroResponse erroResponse = (ErroResponse) response;
    assertEquals(404, erroResponse.getStatus());
    assertEquals("Unidade não existe", erroResponse.getErro());

    verify(unidadeRepositorio, times(1)).findById(id);
    verify(unidadeRepositorio, never()).save(any(Unidade.class));
  }

  @Test
  public void testListarUnidades_ReturnsListOfUnidadeResponses() {
    // Arrange
    List<Unidade> unidades = new ArrayList<>();
    Unidade unidade1 = new Unidade();
    unidade1.setId(1);
    unidade1.setEndereco("Address 1");
    unidade1.setTipo("Type 1");
    unidades.add(unidade1);
    Unidade unidade2 = new Unidade();
    unidade2.setId(2);
    unidade2.setEndereco("Address 2");
    unidade2.setTipo("Type 2");
    unidades.add(unidade2);

    when(unidadeRepositorio.findAll()).thenReturn(unidades);

    // Act
    List<UnidadeResponse> unidadeResponses = unidadeService.listarUnidades();

    // Assert
    assertEquals(2, unidadeResponses.size());
    UnidadeResponse response1 = unidadeResponses.get(0);
    assertEquals(1, response1.getId());
    assertEquals("Address 1", response1.getEndereco());
    assertEquals("Type 1", response1.getTipo());
    UnidadeResponse response2 = unidadeResponses.get(1);
    assertEquals(2, response2.getId());
    assertEquals("Address 2", response2.getEndereco());
    assertEquals("Type 2", response2.getTipo());

    verify(unidadeRepositorio, times(1)).findAll();
  }

  @Test
  public void shouldReturnNotFoundWhenUnidadeWithIdDoesNotExist() {
    String id = "50";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    when(unidadeRepositorio.findById(Integer.parseInt(id))).thenReturn(Optional.empty());

    Response response = unidadeService.editarUnidade(id, unidadeRequest);

    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals("Unidade não existe", ((ErroResponse) response).getErro());
  }

  @Test
  public void shouldReturnBadRequestWhenAllFieldsAreEmptyOrBlank() {
    // Arrange
    String id = "1";
    UnidadeRequest unidadeRequest = new UnidadeRequest("", "", "");

    // Act
    Response response = unidadeService.editarUnidade(id, unidadeRequest);

    // Assert
    assertEquals(404, ((ErroResponse) response).getStatus());
    assertEquals(
      "Unidade n\u00E3o existe",
      ((ErroResponse) response).getErro()
    );
  }

  @Test
  public void shouldUpdateEnderecoWhenOnlyEnderecoFieldIsProvided() {
    String id = "1";
    String newEndereco = "Novo endereco";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setEndereco(newEndereco);
    Unidade existingUnidade = new Unidade();
    when(unidadeRepositorio.findById(Integer.parseInt(id)))
      .thenReturn(Optional.of(existingUnidade));
      when(unidadeRepositorio.save(any(Unidade.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Response response = unidadeService.editarUnidade(id, unidadeRequest);

    assertEquals(newEndereco, ((UnidadeResponse) response).getEndereco());

    assertEquals(
      existingUnidade.getIdUnidade(),
      ((UnidadeResponse) response).getIdUnidade()
    );
    assertEquals(
      existingUnidade.getTipo(),
      ((UnidadeResponse) response).getTipo()
    );
  }

  @Test
  public void shouldUpdateTipoWhenOnlyTipoFieldIsProvided() {
    String id = "1";
    String newTipo = "Novo tipo";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setTipo(newTipo);
    Unidade existingUnidade = new Unidade();
    when(unidadeRepositorio.findById(Integer.parseInt(id)))
      .thenReturn(Optional.of(existingUnidade));
 when(unidadeRepositorio.save(any(Unidade.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    Response response = unidadeService.editarUnidade(id, unidadeRequest);

    assertEquals(newTipo, ((UnidadeResponse) response).getTipo());

    assertEquals(
      existingUnidade.getIdUnidade(),
      ((UnidadeResponse) response).getIdUnidade()
    );
    assertEquals(
      existingUnidade.getTipo(),
      ((UnidadeResponse) response).getTipo()
    );
  }

  @Test
  public void shouldUpdateIdUnidadeWhenOnlyIdUnidadeFieldIsProvided() {
    String id = "1";
    String newIdUnidade = "Novo nome";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setIdUnidade(newIdUnidade);
    Unidade existingUnidade = new Unidade();
    when(unidadeRepositorio.findById(Integer.parseInt(id)))
      .thenReturn(Optional.of(existingUnidade));
    when(unidadeRepositorio.findByIdUnidade(newIdUnidade)).thenReturn(Optional.empty());
  when(unidadeRepositorio.save(any(Unidade.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    Response response = unidadeService.editarUnidade(id, unidadeRequest);

    assertEquals(newIdUnidade, ((UnidadeResponse) response).getIdUnidade());

    assertEquals(
      existingUnidade.getIdUnidade(),
      ((UnidadeResponse) response).getIdUnidade()
    );
    assertEquals(
      existingUnidade.getTipo(),
      ((UnidadeResponse) response).getTipo()
    );
  }

 @Test
public void shouldReturnConflictWhenNewIdUnidadeExistsWithDifferentInformation() {
    // Arrange
    String id = "1";
    String newIdUnidade = "Novo nome";
    UnidadeRequest unidadeRequest = new UnidadeRequest();
    unidadeRequest.setIdUnidade(newIdUnidade);
    Unidade existingUnidade = new Unidade();
    Unidade existingUnidade2 = new Unidade();
    existingUnidade2.setIdUnidade(newIdUnidade);
    existingUnidade2.setId(3);
    when(unidadeRepositorio.findById(Integer.parseInt(id)))
        .thenReturn(Optional.of(existingUnidade));
    when(unidadeRepositorio.findByIdUnidade(newIdUnidade))
        .thenReturn(Optional.of(existingUnidade2));

   
    Response response = unidadeService.editarUnidade(id, unidadeRequest);

    assertEquals(409, ((ErroResponse) response).getStatus());
    assertEquals("J\u00E1 existe uma outra unidade com esse identificador com outras informa\u00E7\u00F5es", ((ErroResponse) response).getErro());
}







}
