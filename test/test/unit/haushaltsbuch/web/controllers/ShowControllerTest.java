package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.ArgumentException;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.LookupException;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.ShowController;

public class ShowControllerTest extends ShowController
{
  private EntryRepository _repository;

  @Before
  public void setUp() throws Exception
  {
    _repository = mock(EntryRepository.class);

    ServletConfig servletConfig = mock(ServletConfig.class);
    ServletContext servletContext = mock(ServletContext.class);

    when(servletConfig.getServletContext()).thenReturn(servletContext);
    when(servletContext.getAttribute(BaseController.CTX_ATTR_NAME)).thenReturn(_repository);

    init(servletConfig);
  }

  @Test
  public void testShow() throws ServletException, IOException, LookupException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("id")).thenReturn("42");

    Entry shownEntry = mock(Entry.class);
    when(_repository.lookup("42")).thenReturn(shownEntry);

    doGet(request, response);

    verify(request).setAttribute("entry", shownEntry);
  }

  @Test
  public void testShowNotExisting() throws ServletException, IOException, LookupException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("id")).thenReturn("42");

    when(_repository.lookup("42")).thenReturn(null);

    doGet(request, response);

    verify(response).setStatus(404);
    verify(request).setAttribute(eq("error"), contains("ein Eintrag"));
  }

  @Test
  public void testShowWithMissingParameter() throws ServletException, IOException, LookupException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    // id parameter not set

    Entry deletedEntry = mock(Entry.class);
    when(_repository.lookup("42")).thenReturn(deletedEntry);

    doGet(request, response);

    verify(response).setStatus(400);
    verify(request).setAttribute(eq("error"), contains("ehlender"));
  }
}
