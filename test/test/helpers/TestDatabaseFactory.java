package test.helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseFactory
{
  private final String _dbName;
  private final Connection _connection;

  public TestDatabaseFactory(Connection connection, String dbName) throws SQLException
  {
    _connection = connection;
    _dbName = dbName;

    createDatabase();
  }

  public void tearDown() throws SQLException
  {
    dropDatabase();
  }

  private void createDatabase() throws SQLException
  {
    Statement statement = null;

    try
    {
      statement = _connection.createStatement();
      statement.executeUpdate("CREATE DATABASE " + _dbName);
    }
    finally
    {
      tryClose(statement);
    }
  }

  private void dropDatabase() throws SQLException
  {
    Statement statement = null;

    try
    {
      statement = _connection.createStatement();
      statement.executeUpdate("DROP DATABASE " + _dbName);
    }
    finally
    {
      tryClose(statement);
    }
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