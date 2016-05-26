package test.helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import junit.framework.AssertionFailedError;

public class DatabaseAssertions
{
  private final Connection _connection;

  public DatabaseAssertions(Connection connection, String dbName)
  {
    _connection = connection;
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
    Statement statement = null;
    ResultSet results = null;

    try
    {
      statement = _connection.createStatement();
      exists = statement.executeQuery(MessageFormat.format("SELECT * FROM {0} WHERE id = ''{1}''", tableName, id)).next();
    }
    finally
    {
      tryClose(results);
      tryClose(statement);
    }
    return exists;
  }

  private void tryClose(ResultSet results) throws SQLException
  {
    if (null != results)
      results.close();
  }

  private void tryClose(Statement statement) throws SQLException
  {
    if (null != statement)
      statement.close();
  }
}