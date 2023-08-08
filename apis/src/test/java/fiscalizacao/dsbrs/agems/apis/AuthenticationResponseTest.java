package fiscalizacao.dsbrs.agems.apis;

import static org.junit.jupiter.api.Assertions.*;

import fiscalizacao.dsbrs.agems.apis.responses.AuthenticationResponse;
import org.junit.Test;

public class AuthenticationResponseTest {

  @Test
  public void testEquals() {
    AuthenticationResponse response1 = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .build();

    AuthenticationResponse response2 = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .build();

    AuthenticationResponse response3 = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .build();

    assertEquals(response1, response2);

    assertNotEquals(response1, response3);
  }

  @Test
  public void testToString() {
    AuthenticationResponse response = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .build();

    String expectedToString =
      "AuthenticationResponse(accessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4, refreshToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU)";
    assertEquals(expectedToString, response.toString());
  }

  @Test
  public void testHashCode() {
   AuthenticationResponse response1 = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .build();

    AuthenticationResponse response2 = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .build();

    AuthenticationResponse response3 = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg3MzU3NDg3fQ.99ucpp1sLNp8xiM2-E6NwBPBq83nPiUrG1AffwOLbkU"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6ZXppbmhvLnNpbHZhQGdtYWlsLmNvbSIsImlhdCI6MTY4Njc1MjY4NywiZXhwIjoxNjg2ODM5MDg3fQ.1WPuKv3DytONDm2Khl37KNLkBX1eNVMvVyoz2D7meX4"
      )
      .build();

    assertEquals(response1.hashCode(), response2.hashCode());
     assertNotEquals(response1.hashCode(), response3.hashCode());
  }

  @Test
  public void testSetEGet() {
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.setAccessToken(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
    );
    authenticationResponse.setRefreshToken(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk"
    );
    assertTrue(authenticationResponse.getAccessToken() != null);
    assertTrue(authenticationResponse.getRefreshToken() != null);
    assertTrue(
      authenticationResponse
        .getAccessToken()
        .equals(
          "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
        )
    );
    assertFalse(
      authenticationResponse
        .getRefreshToken()
        .equals(
          "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
        )
    );
    assertTrue(
      authenticationResponse
        .getRefreshToken()
        .equals(
          "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk"
        )
    );
    assertFalse(
      authenticationResponse
        .getAccessToken()
        .equals(authenticationResponse.getRefreshToken())
    );
  }

  @Test
  public void testConstrutorNoArgs() {
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    assertNotNull(authenticationResponse);
    assertTrue(authenticationResponse.getAccessToken() == null);
    assertTrue(authenticationResponse.getRefreshToken() == null);
  }

  @Test
  public void testConstrutorAllArgs() {
    AuthenticationResponse authenticationResponse = new AuthenticationResponse(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E",
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk"
    );
    assertNotNull(authenticationResponse);
    assertNotNull(authenticationResponse.getAccessToken());
    assertNotNull(authenticationResponse.getRefreshToken());
    assertFalse(
      authenticationResponse
        .getAccessToken()
        .equals(authenticationResponse.getRefreshToken())
    );
    assertEquals(
      authenticationResponse.getAccessToken(),
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
    );
    assertEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk",
      authenticationResponse.getRefreshToken()
    );
  }

  @Test
  public void testBuilderNoArgs() {
    AuthenticationResponse authenticationResponse = AuthenticationResponse
      .builder()
      .build();
    assertNotNull(authenticationResponse);
    assertNull(authenticationResponse.getAccessToken());
    assertNull(authenticationResponse.getRefreshToken());
  }

  @Test
  public void testBuilderAllArgs() {
    AuthenticationResponse authenticationResponse = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
      )
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk"
      )
      .build();
    assertNotNull(authenticationResponse);
    assertNotNull(authenticationResponse.getAccessToken());
    assertNotNull(authenticationResponse.getRefreshToken());
    assertFalse(
      authenticationResponse
        .getAccessToken()
        .equals(authenticationResponse.getRefreshToken())
    );
    assertEquals(
      authenticationResponse.getAccessToken(),
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
    );
    assertEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk",
      authenticationResponse.getRefreshToken()
    );
  }

  @Test
  public void testBuilderAccessToken() {
    AuthenticationResponse authenticationResponse = AuthenticationResponse
      .builder()
      .accessToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E"
      )
      .build();
    assertNotNull(authenticationResponse);
    assertNotNull(authenticationResponse.getAccessToken());
    assertNull(authenticationResponse.getRefreshToken());
    assertEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E",
      authenticationResponse.getAccessToken()
    );
    assertNotEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk",
      authenticationResponse.getAccessToken()
    );
  }

  @Test
  public void testBuilderRefreshToken() {
    AuthenticationResponse authenticationResponse = AuthenticationResponse
      .builder()
      .refreshToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk"
      )
      .build();
    assertNotNull(authenticationResponse);
    assertNull(authenticationResponse.getAccessToken());
    assertNotNull(authenticationResponse.getRefreshToken());
    assertEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1OTI0Nzc3fQ.ZgUXiJrANfD4ligehlHyd7y7f1HFZZLTUdpo6Esj8Dk",
      authenticationResponse.getRefreshToken()
    );
    assertNotEquals(
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpYWFjb3JhenphQGdtYWlsLmNvbSIsImlhdCI6MTY4NTMxOTk3NywiZXhwIjoxNjg1NDA2Mzc3fQ.D-QbU00XdPpOlsS_Gd-m2XEOM9LJ-jZOQ_MooflzA2E",
      authenticationResponse.getAccessToken()
    );
  }
}
