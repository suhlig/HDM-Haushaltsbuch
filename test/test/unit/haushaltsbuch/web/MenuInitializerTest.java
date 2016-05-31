package test.unit.haushaltsbuch.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import haushaltsbuch.web.MenuInitializer;

public class MenuInitializerTest
{
  private MenuInitializer _subject;

  @Captor
  ArgumentCaptor<Map<String, String>> captor;

  @Before
  public void setUp() throws Exception
  {
    _subject = new MenuInitializer();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testContextInitialized()
  {
    ServletContextEvent event = Mockito.mock(ServletContextEvent.class);
    ServletContext ctx = Mockito.mock(ServletContext.class);
    when(event.getServletContext()).thenReturn(ctx);

    _subject.contextInitialized(event);

    verify(ctx).setAttribute(eq("menu"), captor.capture());

    Map<String, String> menu = captor.getValue();

    assertNotNull(menu);
    assertEquals(4, menu.size());

    Map<String, String> expected = new HashMap<String, String>(4);

    expected.put("Home", ".");
    expected.put("Einträge", "entries/all");
    expected.put("Eintrag nachschlagen", "entries/lookup");
    expected.put("Neuer Eintrag…", "entries/new");

    assertEquals(expected, menu);
  }
}
