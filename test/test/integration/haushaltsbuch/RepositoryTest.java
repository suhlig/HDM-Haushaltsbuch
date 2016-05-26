package test.integration.haushaltsbuch;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.DatabaseAssertions;
import test.helpers.TestDatabaseFactory;

public class RepositoryTest
{
  private String _dbName;
  private Connection _dbmsConnection;
  private TestDatabaseFactory _databaseFactory;
  private Connection _dbConnection;
  private JdbcRepository _repository;
  private DatabaseAssertions _assertionHelper;

  public JdbcRepository getRepository()
  {
    return _repository;
  }

  @Before
  public void setUp() throws Exception
  {
    _dbName = getClass().getSimpleName().toLowerCase() + "_" + UUID.randomUUID().toString().replace('-', '_');

    _dbmsConnection = connect("jdbc:postgresql:///");
    _databaseFactory = new TestDatabaseFactory(_dbmsConnection, _dbName);
    _dbConnection = connect("jdbc:postgresql:///" + _dbName);

    _repository = new JdbcRepository(_dbConnection);
    _assertionHelper = new DatabaseAssertions(_dbConnection, _dbName);
  }

  @After
  public void tearDown() throws Exception
  {
    _dbConnection.close();
    _databaseFactory.tearDown();
    _dbmsConnection.close();
  }

  protected DatabaseAssertions getAssertionHelper()
  {
    return _assertionHelper;
  }

  private Connection connect(String dbmsURL) throws ClassNotFoundException, SQLException
  {
    Class.forName("org.postgresql.Driver");
    Connection connection = DriverManager.getConnection(dbmsURL);
    DatabaseMetaData metaData = connection.getMetaData();

    System.out.println(MessageFormat.format("Successfully connected to {0} v.{1} on {2}", metaData.getDatabaseProductName(),
        metaData.getDatabaseProductVersion(), metaData.getURL()));

    return connection;
  }
}