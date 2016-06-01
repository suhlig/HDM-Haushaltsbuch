package test.unit.haushaltsbuch.web;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import haushaltsbuch.web.RepositoryInitializer;
import haushaltsbuch.web.ServletContextRegistry;

public class RepositoryInitializerWithoutDriverTest extends RepositoryInitializer
{
  private List<Driver> _deregistered;

  @Before
  public void setUp() throws Exception
  {
    _deregistered = deRegister(RepositoryInitializer.JDBC_URL);
  }

  @After
  public void tearDown() throws Exception
  {
    reRegister(_deregistered);
  }

  @Test
  public void testConnectionCloseFails() throws SQLException
  {
    Connection connection = mock(Connection.class);
    doThrow(SQLException.class).when(connection).close();

    ServletContextRegistry servletContextRegistry = mock(ServletContextRegistry.class);
    when(servletContextRegistry.getConnection()).thenReturn(connection);

    setServletContextRegistry(servletContextRegistry);

    PrintStream sysErr = mock(PrintStream.class);
    System.setErr(sysErr);

    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);

    contextDestroyed(event);

    verify(sysErr).println(contains("Error"));
  }

  @Test
  public void testConnectionIsClosed() throws SQLException
  {
    Connection connection = mock(Connection.class);

    ServletContextRegistry servletContextRegistry = mock(ServletContextRegistry.class);
    when(servletContextRegistry.getConnection()).thenReturn(connection);
    setServletContextRegistry(servletContextRegistry);

    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);

    contextDestroyed(event);

    verify(connection).close();
  }

  @Test
  public void testDriverNotFound() throws SQLException
  {
    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);
    ServletContext ctx = Mockito.mock(ServletContext.class);
    when(event.getServletContext()).thenReturn(ctx);

    contextInitialized(event);

    verifyZeroInteractions(ctx);
  }

  private List<Driver> deRegister(String jdbc_url) throws SQLException
  {
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    List<Driver> result = new ArrayList<>();

    while (drivers.hasMoreElements())
    {
      Driver driver = drivers.nextElement();

      if (driver.acceptsURL(jdbc_url))
      {
        System.out.println("De-registering driver " + driver.toString() + " for this test run");
        DriverManager.deregisterDriver(driver);
        result.add(driver);
      }
    }

    return result;
  }

  private void reRegister(List<Driver> deregistered) throws SQLException
  {
    for (Driver driver : deregistered)
      DriverManager.registerDriver(driver);
  }
}
