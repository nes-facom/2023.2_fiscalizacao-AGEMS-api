package fiscalizacao.dsbrs.agems.apis;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerAgems
  extends PostgreSQLContainer<PostgresContainerAgems> {

  private static final String IMAGE_VERSION = "postgres:11.1";
  private static PostgresContainerAgems container;

  private PostgresContainerAgems() {
    super(IMAGE_VERSION);
  }

  public static PostgresContainerAgems getInstance() {
    if (container == null) {
      container = new PostgresContainerAgems();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    try (
      Connection connection = DriverManager.getConnection(
        container.getJdbcUrl(),
        container.getUsername(),
        container.getPassword()
      )
    ) {
      String sqlScript = new String(
        Files.readAllBytes(
          Paths.get(getClass().getClassLoader().getResource("dump.sql").toURI())
        )
      );
      Statement statement = connection.createStatement();
      statement.execute(sqlScript);
    } catch (SQLException | IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    System.setProperty("spring.datasource.url", container.getJdbcUrl());
    System.setProperty("spring.datasource.username", container.getUsername());
    System.setProperty("spring.datasource.password", container.getPassword());
  }

  @Override
  public void stop() {}
}
