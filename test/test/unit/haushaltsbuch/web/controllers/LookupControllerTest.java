package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.LookupException;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.LookupController;

public class LookupControllerTest extends LookupController
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
  public void testInputForm() throws ServletException, IOException, LookupException
  {
    ServletContext servletContext = mock(ServletContext.class);
    when(servletContext.getContextPath()).thenReturn("/unit-test");

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletContext()).thenReturn(servletContext);
    when(request.getRequestURI()).thenReturn("/unit-test/entries/lookup");

    HttpServletResponse response = mock(HttpServletResponse.class);

    String id0 = "a";
    String id1 = "b";
    String id2 = "c";

    List<Entry> entries = Arrays.asList(new Entry[] { mockEntry(id0), mockEntry(id1), mockEntry(id2) });

    when(_repository.all()).thenReturn(entries);

    doGet(request, response);

    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("view"), eq("entries/lookup.jsp"));
    verify(request).setAttribute(eq("ids"), eq(Arrays.asList(new String[] { id0, id1, id2 })));
  }

  @Test
  public void testLookup() throws ServletException, IOException, LookupException
  {
    ServletContext servletContext = mock(ServletContext.class);
    when(servletContext.getContextPath()).thenReturn("/unit-test");

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletContext()).thenReturn(servletContext);
    when(request.getParameter("id")).thenReturn("42");

    HttpServletResponse response = mock(HttpServletResponse.class);

    Entry foundEntry = mock(Entry.class);
    when(_repository.lookup("42")).thenReturn(foundEntry);

    doPost(request, response);

    verify(response).sendRedirect("/unit-test/entries?id=42");
  }

  @Test
  public void testNoIdGiven() throws ServletException, IOException, LookupException
  {
    ServletContext servletContext = mock(ServletContext.class);
    when(servletContext.getContextPath()).thenReturn("/unit-test");

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletContext()).thenReturn(servletContext);
    when(request.getRequestURI()).thenReturn("/unit-test/entries/lookup");

    HttpServletResponse response = mock(HttpServletResponse.class);

    when(_repository.lookup(anyString())).thenReturn(null);

    doPost(request, response);

    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("error"), eq("Identifikator fehlt"));
    verify(request).setAttribute(eq("view"), eq("entries/lookup.jsp"));
  }

  private Entry mockEntry(String id0)
  {
    Entry entry0 = mock(Entry.class);
    when(entry0.getId()).thenReturn(id0);
    return entry0;
  }
}
