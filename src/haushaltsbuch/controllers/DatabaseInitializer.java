package haushaltsbuch.controllers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import haushaltsbuch.Credentials;
import haushaltsbuch.persistence.EntryRepository;
import haushaltsbuch.persistence.JdbcRepository;

@WebListener
public class DatabaseInitializer implements ServletContextListener
{
  @Override
  public void contextDestroyed(ServletContextEvent ignored)
  {
    // unused
  }

  @Override
  public void contextInitialized(ServletContextEvent event)
  {
    try
    {
      Credentials credentials = new Credentials(System.getenv("VCAP_SERVICES"));
      EntryRepository repository = new JdbcRepository(credentials.getURI(), credentials.getUser(), credentials.getPassword());
      event.getServletContext().setAttribute(EntryRepository.CTX_ATTR_NAME, repository);
    }
    catch (Exception e)
    {
      System.err.println("Error initializing: ");
      e.printStackTrace(System.err);
    }
  }
}