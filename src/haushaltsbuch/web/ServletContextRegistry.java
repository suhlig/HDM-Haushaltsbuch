package haushaltsbuch.web;

import java.sql.Connection;
import javax.servlet.ServletContext;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.web.controllers.BaseController;

public class ServletContextRegistry
{
  public final static String CTX_ATTR_CONNECTION = ServletContextRegistry.class.getName() + "_DB_CONNECTION";
  private final ServletContext _servletContext;

  public ServletContextRegistry(ServletContext servletContext)
  {
    _servletContext = servletContext;
  }

  public Connection getConnection()
  {
    return (Connection) _servletContext.getAttribute(CTX_ATTR_CONNECTION);
  }

  public void register(Connection connection)
  {
    _servletContext.setAttribute(CTX_ATTR_CONNECTION, connection);
  }

  public void register(EntryRepository repository)
  {
    _servletContext.setAttribute(BaseController.CTX_ATTR_NAME, repository);
  }
}
