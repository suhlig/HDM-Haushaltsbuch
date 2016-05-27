package test.integration.haushaltsbuch;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.web.RepositoryInitializer;
import haushaltsbuch.web.controllers.BaseController;

public class RepositoryInitializerTest
{
  private RepositoryInitializer _subject;

  @Before
  public void setUp() throws Exception
  {
    _subject = new RepositoryInitializer();
  }

  @Test
  public void testFallbackToLocalDriver() throws SQLException
  {
    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);
    ServletContext ctx = Mockito.mock(ServletContext.class);
    when(event.getServletContext()).thenReturn(ctx);

    _subject.contextInitialized(event);

    verify(ctx).setAttribute(eq(BaseController.CTX_ATTR_NAME), any(EntryRepository.class));
  }
}
