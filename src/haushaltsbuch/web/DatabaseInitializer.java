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
import haushaltsbuch.web.controllers.BaseController;

@WebListener
public class DatabaseInitializer implements ServletContextListener
{
  private static final String CTX_ATTR_CONNECTION = DatabaseInitializer.class.getName() + "_DB_CONNECTION";

  @Override
  public void contextDestroyed(ServletContextEvent event)
  {
    Connection connection = (Connection) event.getServletContext().getAttribute(CTX_ATTR_CONNECTION);

    try
    {
      if (null != connection)
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
    Connection connection = lookupConnection();

    if (null == connection)
    {
      System.out.println("Could not lookup a database connection; creating a fresh one");
      connection = createConnection("jdbc:postgresql:///haushaltsbuch");
    }

    if (null == connection)
      System.err.println("Could not create a database connection; giving up.");
    else
    {
      event.getServletContext().setAttribute(CTX_ATTR_CONNECTION, connection);
      event.getServletContext().setAttribute(BaseController.CTX_ATTR_NAME, createRepository(connection));
    }
  }

  private Connection createConnection(String url)
  {
    try
    {
      Connection connection;

      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(url);

      DatabaseMetaData metaData = connection.getMetaData();
      System.out.println(MessageFormat.format("Successfully connected to {0} v.{1} on {2}", metaData.getDatabaseProductName(),
          metaData.getDatabaseProductVersion(), metaData.getURL()));

      return connection;
    }
    catch (ClassNotFoundException e)
    {
      System.err.println("Could not load the database driver: " + e.getMessage());
      return null;
    }
    catch (SQLException e)
    {
      System.err.println("Could not create a database connection: " + e.getMessage());
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

  private Connection lookupConnection()
  {
    try
    {
      return ((DataSource) new InitialContext().lookup("jdbc/elephantsql")).getConnection();
    }
    catch (SQLException e)
    {
      System.err.println("Could not connect to the database: " + e.getMessage());
      e.printStackTrace(System.err);
      return null;
    }
    catch (NamingException e)
    {
      System.err.println("Could not find a database connection in the JNDI. Perhaps there is no database service bound to this app?");
      System.err.println(e.getMessage());
      e.printStackTrace(System.err);
      return null;
    }
  }
}
