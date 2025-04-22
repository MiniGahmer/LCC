package lcc.lusocraftchampionship.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.file.DataManager;

public enum DB {
  INSTANCE;

  private MysqlDataSource dataSource;
  private DataManager dataManager = new DataManager("database");

  DB() {
    try {
      dataSource = (MysqlDataSource) initMySQLDataSource();

      initDb();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initDb() throws SQLException, IOException {
    String setup;
    try (InputStream in = getClass().getResourceAsStream("dbsetup.sql")) {
      setup = new String(in.readAllBytes());
      setup = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      LCCPlugin.LOGGER.log(Level.SEVERE, "Could not load dbsetup.sql");
      throw e;
    }

    String[] queries = setup.split(";");

    for (String query : queries) {
      if (query.isBlank())
        continue;
      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.execute();
      }
    }
  }

  private DataSource initMySQLDataSource() throws SQLException {
    MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();

    dataManager.reloadConfig();

    String host = dataManager.getConfig().getString("host");
    String port = dataManager.getConfig().getString("port");
    String database = dataManager.getConfig().getString("database");
    String user = dataManager.getConfig().getString("user");
    String password = dataManager.getConfig().getString("password");

    dataSource.setServerName(host);
    dataSource.setPort(Integer.parseInt(port));
    dataSource.setDatabaseName(database);
    dataSource.setUser(user);
    dataSource.setPassword(password);

    try (Connection conn = dataSource.getConnection()) {
      if (!conn.isValid(1)) {
        throw new SQLException("Could not establish database connection.");
      }
    }

    return dataSource;
  }
}
