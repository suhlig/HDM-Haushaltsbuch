package test.helpers;

import static org.junit.Assert.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.UUID;
import junit.framework.AssertionFailedError;

public class TestDatabase
{
  private String _dbName;
  private String _dbmsURL;

  public TestDatabase(String dbmsURL) throws ClassNotFoundException, SQLException
  {
    _dbmsURL = dbmsURL;
    _dbName = getClass().getSimpleName().toLowerCase() + "_" + UUID.randomUUID().toString().replace('-', '_');
    createDatabase();
  }

  public String getURL()
  {
    return _dbmsURL + _dbName;
  }

  public void tearDown() throws SQLException
  {
    dropDatabase();
  }

  private void createDatabase() throws SQLException, ClassNotFoundException
  {
    Class.forName("org.postgresql.Driver");

    Connection connection = null;
    Statement statement = null;

    try
    {
      connection = DriverManager.getConnection(_dbmsURL);
      statement = connection.createStatement();
      statement.executeUpdate("CREATE DATABASE " + _dbName);
    }
    finally
    {
      tryClose(statement);

      tryClose(connection);
    }
  }

  private void dropDatabase() throws SQLException
  {
    Connection connection = null;
    Statement statement = null;

    try
    {
      connection = DriverManager.getConnection(_dbmsURL);
      statement = connection.createStatement();
      statement.executeUpdate("DROP DATABASE " + _dbName);
    }
    finally
    {
      tryClose(statement);

      tryClose(connection);
    }
  }

  public void assertExists(String id, String tableName) throws AssertionFailedError, SQLException
  {
    if (!entryExists(id, tableName))
    {
      String msg = "Expected record with id {0} to exist in table {1}, but it doesn''t.";
      throw new AssertionFailedError(MessageFormat.format(msg, id, tableName));
    }
  }

  public void assertNotExists(String id, String tableName) throws AssertionFailedError, SQLException
  {
    if (entryExists(id, tableName))
    {
      String msg = "Expected record with id {0} to NOT exist in table {1}, but it does.";
      throw new AssertionFailedError(MessageFormat.format(msg, id, tableName));
    }
  }

  private boolean entryExists(String id, String tableName) throws SQLException
  {
    boolean exists;
    Connection connection = null;
    Statement statement = null;
    ResultSet results = null;

    try
    {
      connection = DriverManager.getConnection(getURL());
      statement = connection.createStatement();
      exists = statement.executeQuery(MessageFormat.format("SELECT * FROM {0} WHERE id = ''{1}''", tableName, id)).next();
    }
    finally
    {
      tryClose(results);
      tryClose(statement);
      tryClose(connection);
    }
    return exists;
  }

  private void tryClose(Connection connection) throws SQLException
  {
    if (null != connection)
      connection.close();
  }

  private void tryClose(Statement statement) throws SQLException
  {
    if (null != statement)
      statement.close();
  }

  private void tryClose(ResultSet results) throws SQLException
  {
    if (null != results)
      results.close();
  }
}