package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fiscalizacao.dsbrs.agems.apis.responses.ErroResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ErroResponseTest {

  @Autowired
  private Validator validador = Validation
    .buildDefaultValidatorFactory()
    .getValidator();

  @Test
  public void testErrorResponseValidationSuccessResponses() {
    ErroResponse erroResponse200 = new ErroResponse(200, "Ok");
    ErroResponse erroResponse201 = new ErroResponse(201, "Created");
    ErroResponse erroResponse202 = new ErroResponse(202, "Accepted");
    ErroResponse erroResponse203 = new ErroResponse(
      203,
      "Non-authoritative Information"
    );
    ErroResponse erroResponse204 = new ErroResponse(204, "No content");
    ErroResponse erroResponse205 = new ErroResponse(205, "Reset content");
    ErroResponse erroResponse206 = new ErroResponse(206, "Partial content");
    ErroResponse erroResponse207 = new ErroResponse(207, "Multi Status");
    ErroResponse erroResponse208 = new ErroResponse(208, "AlreadyReported");
    ErroResponse erroResponse226 = new ErroResponse(226, "I'm used");
    List<ErroResponse> errosMalFormados = new ArrayList<>();
    errosMalFormados.add(erroResponse200);
    errosMalFormados.add(erroResponse201);
    errosMalFormados.add(erroResponse202);
    errosMalFormados.add(erroResponse203);
    errosMalFormados.add(erroResponse204);
    errosMalFormados.add(erroResponse205);
    errosMalFormados.add(erroResponse206);
    errosMalFormados.add(erroResponse207);
    errosMalFormados.add(erroResponse208);
    errosMalFormados.add(erroResponse226);

    Set<ConstraintViolation<ErroResponse>> violacoes = new HashSet<>();

    for (ErroResponse erroResponse : errosMalFormados) {
      violacoes.addAll(validador.validate(erroResponse));
    }
    assertEquals(10, violacoes.size());
    for (int i = 0; i <= violacoes.size(); i++) {
      ConstraintViolation<ErroResponse> violation = violacoes.iterator().next();
    }
  }

  @Test
  public void testErrorResponseValidationWellFormedResponses() {
    ErroResponse erroResponse400 = new ErroResponse(400, "Bad Request");
    ErroResponse erroResponse401 = new ErroResponse(401, "Not Authorized");
    ErroResponse erroResponse402 = new ErroResponse(402, "Payment Required");
    ErroResponse erroResponse403 = new ErroResponse(403, "Forbidden");
    ErroResponse erroResponse404 = new ErroResponse(404, "Not Found");
    ErroResponse erroResponse405 = new ErroResponse(405, "Method Not Allowed");
    ErroResponse erroResponse406 = new ErroResponse(406, "Not Acceptable");
    ErroResponse erroResponse407 = new ErroResponse(
      407,
      "Proxy Authentication Required"
    );
    ErroResponse erroResponse408 = new ErroResponse(408, "Request Timeout");
    ErroResponse erroResponse409 = new ErroResponse(409, "Conflict");
    ErroResponse erroResponse500 = ErroResponse
      .builder()
      .erro("Internal Server Error")
      .status(500)
      .build();
    ErroResponse erroResponse501 = ErroResponse
      .builder()
      .erro("Not Implemented")
      .status(501)
      .build();
    ErroResponse erroResponse502 = ErroResponse
      .builder()
      .erro("Bad Gateway")
      .status(502)
      .build();
    ErroResponse erroResponse505 = ErroResponse
      .builder()
      .erro("HTTP Version Not Supported")
      .status(505)
      .build();
    List<ErroResponse> errosBemFormados = new ArrayList<>();
    errosBemFormados.add(erroResponse400);
    errosBemFormados.add(erroResponse401);
    errosBemFormados.add(erroResponse402);
    errosBemFormados.add(erroResponse403);
    errosBemFormados.add(erroResponse404);
    errosBemFormados.add(erroResponse405);
    errosBemFormados.add(erroResponse406);
    errosBemFormados.add(erroResponse407);
    errosBemFormados.add(erroResponse408);
    errosBemFormados.add(erroResponse409);
    errosBemFormados.add(erroResponse500);
    errosBemFormados.add(erroResponse501);
    errosBemFormados.add(erroResponse502);
    errosBemFormados.add(erroResponse505);
    errosBemFormados.add(new ErroResponse());

    Set<ConstraintViolation<ErroResponse>> violacoes = new HashSet<>();

    for (ErroResponse erroResponse : errosBemFormados) {
      violacoes.addAll(validador.validate(erroResponse));
    }
    assertEquals(0, violacoes.size());
  }

  @Test
  public void testErrorResponseValidationInformalResponses() {
    ErroResponse erroResponse100 = new ErroResponse(100, "Continue");
    ErroResponse erroResponse101 = new ErroResponse(101, "Switching Protocols");
    ErroResponse erroResponse102 = new ErroResponse(102, "Processing");

    List<ErroResponse> errosMalFormados = new ArrayList<>();
    errosMalFormados.add(erroResponse100);
    errosMalFormados.add(erroResponse101);
    errosMalFormados.add(erroResponse102);

    Set<ConstraintViolation<ErroResponse>> violacoes = new HashSet<>();

    for (ErroResponse erroResponse : errosMalFormados) {
      violacoes.addAll(validador.validate(erroResponse));
    }
    assertEquals(3, violacoes.size());
    for (int i = 0; i <= violacoes.size(); i++) {
      ConstraintViolation<ErroResponse> violation = violacoes.iterator().next();
      assertNotNull(violation.getMessage());
      assertEquals("Status inválido", violation.getMessage());
    }
  }

  @Test
  public void testErrorResponseValidationRedirectionResponses() {
    ErroResponse erroResponse300 = new ErroResponse(300, "Multiple Choices");
    ErroResponse erroResponse301 = new ErroResponse(301, "Moved Permanently");
    ErroResponse erroResponse302 = new ErroResponse(302, "Found");
    ErroResponse erroResponse303 = new ErroResponse(303, "See other");
    ErroResponse erroResponse304 = new ErroResponse(304, "Not Modified");
    ErroResponse erroResponse305 = new ErroResponse(305, "Use Proxy");
    ErroResponse erroResponse307 = new ErroResponse(307, "Temporary Redirect");
    ErroResponse erroResponse308 = new ErroResponse(308, "Permanent Redirect");
    List<ErroResponse> errosMalFormados = new ArrayList<>();
    errosMalFormados.add(erroResponse300);
    errosMalFormados.add(erroResponse301);
    errosMalFormados.add(erroResponse302);
    errosMalFormados.add(erroResponse303);
    errosMalFormados.add(erroResponse304);
    errosMalFormados.add(erroResponse305);
    errosMalFormados.add(erroResponse307);
    errosMalFormados.add(erroResponse308);

    Set<ConstraintViolation<ErroResponse>> violacoes = new HashSet<>();

    for (ErroResponse erroResponse : errosMalFormados) {
      violacoes.addAll(validador.validate(erroResponse));
    }
    assertEquals(8, violacoes.size());
    for (int i = 0; i <= violacoes.size(); i++) {
      ConstraintViolation<ErroResponse> violation = violacoes.iterator().next();
      assertNotNull(violation.getMessage());
      assertEquals("Status inválido", violation.getMessage());
    }
  }

  @Test
  public void testErrorResponseValidationUpperValueResponses() {
    ErroResponse erroResponse600 = new ErroResponse(
      600,
      "Esse erro não existe"
    );
    ErroResponse erroResponse601 = new ErroResponse(
      601,
      "Esse erro não existe"
    );
    ErroResponse erroResponse602 = new ErroResponse(
      602,
      "Esse erro n\u00E3o existe"
    );
    ErroResponse erroResponse603 = new ErroResponse(
      603,
      "Esse erro n\u00E3o existe"
    );
    ErroResponse erroResponse604 = new ErroResponse(
      604,
      "Esse erro n\u00E3o existe"
    );
    ErroResponse erroResponse605 = new ErroResponse(
      605,
      "Esse erro n\u00E3o existe"
    );
    ErroResponse erroResponse606 = new ErroResponse(
      606,
      "Esse erro n\u00E3o existe"
    );
    ErroResponse erroResponse607 = new ErroResponse(
      607,
      "Esse erro n\u00E3o existe"
    );
    List<ErroResponse> errosMalFormados = new ArrayList<>();
    errosMalFormados.add(erroResponse600);
    errosMalFormados.add(erroResponse601);
    errosMalFormados.add(erroResponse602);
    errosMalFormados.add(erroResponse603);
    errosMalFormados.add(erroResponse604);
    errosMalFormados.add(erroResponse605);
    errosMalFormados.add(erroResponse606);
    errosMalFormados.add(erroResponse607);

    Set<ConstraintViolation<ErroResponse>> violacoes = new HashSet<>();

    for (ErroResponse erroResponse : errosMalFormados) {
      violacoes.addAll(validador.validate(erroResponse));
    }
    assertEquals(8, violacoes.size());
    for (int i = 0; i <= violacoes.size(); i++) {
      ConstraintViolation<ErroResponse> violation = violacoes.iterator().next();
      assertNotNull(violation.getMessage());
      assertEquals("Status inválido", violation.getMessage());
    }
  }
}
