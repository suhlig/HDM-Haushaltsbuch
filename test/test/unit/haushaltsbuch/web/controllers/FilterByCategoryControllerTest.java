package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.FilterByCategoryController;
import test.helpers.TestEntry;

public class FilterByCategoryControllerTest extends FilterByCategoryController
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
  public void testNameMissing() throws ServletException, IOException, LookupException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/entries/by-category");

    HttpServletResponse response = mock(HttpServletResponse.class);

    when(_repository.by_category(null)).thenThrow(ArgumentException.class);

    doGet(request, response);

    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("error"), eq("Name der Kategorie fehlt"));
  }

  @Test
  public void testNoResults() throws ServletException, IOException, InsertException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/entries/by-category");
    when(request.getParameter("name")).thenReturn("test-category");

    HttpServletResponse response = mock(HttpServletResponse.class);

    List<Entry> empty = Collections.emptyList();
    when(_repository.by_category(anyString())).thenReturn(empty);

    doGet(request, response);

    verify(request).setAttribute(eq("entries"), eq(empty));
    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("view"), eq("entries/all.jsp"));
  }

  @Test
  public void testSomeResults() throws ServletException, IOException, InsertException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/entries/by-category");
    when(request.getParameter("name")).thenReturn("test-category");

    HttpServletResponse response = mock(HttpServletResponse.class);

    List<Entry> some = asList(new TestEntry(), new TestEntry());
    when(_repository.by_category("test-category")).thenReturn(some);

    doGet(request, response);

    verify(request).setAttribute(eq("entries"), eq(some));
    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("view"), eq("entries/all.jsp"));
  }

  private List<Entry> asList(Entry... entries)
  {
    return Arrays.asList(entries);
  }
}
