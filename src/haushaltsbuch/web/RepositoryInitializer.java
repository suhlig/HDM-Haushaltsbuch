package haushaltsbuch.web;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;

@WebListener("Initializes the repository")
public class RepositoryInitializer implements ServletContextListener
{
  /** If bound, Bluemix will make the ElephantSQL service instance available under this name */
  private static final String JNDI_DATASOURCE_NAME = "jdbc/elephantsql";

  /** Fallback URL for development and test */
  public static final String JDBC_URL = "jdbc:postgresql:///haushaltsbuch";

  private ServletContextRegistry _registry;

  @Override
  public void contextDestroyed(ServletContextEvent event)
  {
    Connection connection = _registry.getConnection();

    if (null != connection)
      try
      {
        connection.close();
      }
      catch (SQLException e)
      {
        System.err.println("Error closing the DB connection: " + e.getMessage());
      }
  }

  @Override
  public void contextInitialized(ServletContextEvent event)
  {
    _registry = new ServletContextRegistry(event.getServletContext());

    Connection connection = getConnection();

    if (null == connection)
      System.err.println("Could not create a database connection; giving up.");
    else
    {
      printMetadata(connection);

      _registry.register(connection);

      EntryRepository repository = createRepository(connection);
      _registry.register(repository);
    }
  }

  /** required for testability */
  protected InitialContext getInitialContext() throws NamingException
  {
    return new InitialContext();
  }

  private Connection createConnection(String url)
  {
    try
    {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(url);
    }
    catch (ClassNotFoundException e)
    {
      System.err.println("Could not load the database driver: " + e.getMessage());
      return null;
    }
    catch (SQLException e)
    {
      System.err.println("Error creating a database connection: " + e.getMessage());
      return null;
    }
  }

  private EntryRepository createRepository(Connection connection)
  {
    try
    {
      return new JdbcRepository(connection);
    }
    catch (SQLException e)
    {
      System.err.println("Could not create the repository: " + e.getMessage());
    }
    catch (InsertException e)
    {
      System.err.println("Could not initialize the repository: " + e.getMessage());
    }

    return null;
  }

  private Connection getConnection()
  {
    Connection connection = lookupConnection(JNDI_DATASOURCE_NAME);

    if (null == connection)
    {
      System.out.println("Falling back to manually loading the driver");
      connection = createConnection(JDBC_URL);
    }

    return connection;
  }

  private Connection lookupConnection(String name)
  {
    try
    {
      return ((DataSource) getInitialContext().lookup(name)).getConnection();
    }
    catch (NamingException e)
    {
      System.out.println(MessageFormat.format("Could not find the datasource {0} in JNDI", name));
    }
    catch (SQLException e)
    {
      System.err.println("Could not connect to the database: " + e.getMessage());
    }

    return null;
  }

  private void printMetadata(Connection connection)
  {
    try
    {
      DatabaseMetaData metaData = connection.getMetaData();

      System.out.println(MessageFormat.format("Successfully connected to {0} v.{1} on {2}", metaData.getDatabaseProductName(),
          metaData.getDatabaseProductVersion(), metaData.getURL()));
    }
    catch (SQLException e)
    {
      System.err.println("Unable to get connection meta data: " + e.getMessage());
    }
  }
}
