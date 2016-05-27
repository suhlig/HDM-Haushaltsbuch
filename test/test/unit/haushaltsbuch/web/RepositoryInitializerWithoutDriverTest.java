package test.unit.haushaltsbuch.web;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
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

public class RepositoryInitializerWithoutDriverTest
{
  private RepositoryInitializer _subject;
  private List<Driver> _deregistered;

  @Before
  public void setUp() throws Exception
  {
    _subject = new RepositoryInitializer();
    _deregistered = deRegister(RepositoryInitializer.JDBC_URL);
  }

  @After
  public void tearDown() throws Exception
  {
    reRegister(_deregistered);
  }

  @Test
  public void testDriverNotFound() throws SQLException
  {
    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);
    ServletContext ctx = Mockito.mock(ServletContext.class);
    when(event.getServletContext()).thenReturn(ctx);

    _subject.contextInitialized(event);

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
