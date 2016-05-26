package haushaltsbuch.web;

import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import haushaltsbuch.web.ElephantSqlConfig.ParseException;
import haushaltsbuch.web.controllers.BaseController;

@WebListener
public class DatabaseInitializer implements ServletContextListener
{
  @Override
  public void contextDestroyed(ServletContextEvent event)
  {
    ((EntryRepository) event.getServletContext().getAttribute(BaseController.CTX_ATTR_NAME)).close();
  }

  @Override
  public void contextInitialized(ServletContextEvent event)
  {
    EntryRepository repository = null;

    repository = createFromJNDI();

    if (null == repository)
      repository = createFromEnvironment();

    event.getServletContext().setAttribute(BaseController.CTX_ATTR_NAME, repository);
  }

  private EntryRepository createFromEnvironment()
  {
    try
    {
      ElephantSqlConfig credentials = new ElephantSqlConfig(System.getenv("VCAP_SERVICES"));

      return new JdbcRepository(credentials.getURI(), credentials.getUser(), credentials.getPassword());
    }
    catch (ClassNotFoundException | ParseException | SQLException | InsertException e)
    {
      System.err.println("Could not create the repository from the environment: " + e.getMessage());
      return null;
    }
  }

  private EntryRepository createFromJNDI()
  {
    try
    {
      return new JdbcRepository("jdbc/elephantsql");
    }
    catch (NamingException | SQLException | InsertException e)
    {
      System.err.println("Could not create the repository from the JNDI: " + e.getMessage());
      return null;
    }
  }
}
