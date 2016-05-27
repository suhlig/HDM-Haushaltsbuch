package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.InsertException;
import haushaltsbuch.web.controllers.RootController;

public class RootControllerTest extends RootController
{
  @Before
  public void setUp() throws Exception
  {
    init(mock(ServletConfig.class));
  }

  @Test
  public void testEmpty() throws ServletException, IOException, InsertException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/index.jsp");

    HttpServletResponse response = mock(HttpServletResponse.class);

    doGet(request, response);

    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("view"), eq("index.jsp"));
  }
}
