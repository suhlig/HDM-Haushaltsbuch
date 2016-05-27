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
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.ListController;
import test.helpers.TestEntry;

public class ListControllerTest extends ListController
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
  public void testEmpty() throws ServletException, IOException, InsertException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    List<Entry> all = Collections.emptyList();
    when(_repository.all()).thenReturn(all);

    doGet(request, response);

    verify(request).setAttribute(eq("entries"), eq(all));
    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("view"), eq("entries/all.jsp"));
  }

  @Test
  public void testSome() throws ServletException, IOException, InsertException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    List<Entry> all = asList(new TestEntry(), new TestEntry());
    when(_repository.all()).thenReturn(all);

    doGet(request, response);

    verify(request).setAttribute(eq("entries"), eq(all));
    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("view"), eq("entries/all.jsp"));
  }

  private List<Entry> asList(Entry... entries)
  {
    return Arrays.asList(entries);
  }
}
