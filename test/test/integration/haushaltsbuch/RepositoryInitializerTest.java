package test.integration.haushaltsbuch;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.web.RepositoryInitializer;
import haushaltsbuch.web.ServletContextRegistry;
import haushaltsbuch.web.controllers.BaseController;

public class RepositoryInitializerTest extends RepositoryInitializer
{
  private InitialContext _initialContext;

  @Before
  public void setUp() throws Exception
  {
    _initialContext = mock(InitialContext.class);
  }

  @After
  public void tearDown() throws Exception
  {
    _initialContext = null;
  }

  @Test
  public void testFallbackToLocalDriver() throws SQLException, NamingException
  {
    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);
    ServletContext ctx = Mockito.mock(ServletContext.class);
    when(event.getServletContext()).thenReturn(ctx);

    // lookup fails with exception (not found)
    when(_initialContext.lookup(JNDI_DATASOURCE_NAME)).thenThrow(NamingException.class);

    contextInitialized(event);

    verify(ctx).setAttribute(eq(BaseController.CTX_ATTR_NAME), any(EntryRepository.class));
    verify(ctx).setAttribute(eq(ServletContextRegistry.CTX_ATTR_CONNECTION), any(Connection.class));
  }

  @Test
  public void testJndiPreference() throws SQLException, NamingException
  {
    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);
    ServletContext ctx = Mockito.mock(ServletContext.class);
    when(event.getServletContext()).thenReturn(ctx);

    // we'll find a datasource
    when(_initialContext.lookup(JNDI_DATASOURCE_NAME)).thenReturn(mock(DataSource.class));

    contextInitialized(event);

    verify(ctx).setAttribute(eq(BaseController.CTX_ATTR_NAME), any(EntryRepository.class));
    verify(ctx).setAttribute(eq(ServletContextRegistry.CTX_ATTR_CONNECTION), any(Connection.class));
  }

  @Override
  protected InitialContext getInitialContext() throws NamingException
  {
    return _initialContext;
  }
}
