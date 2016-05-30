package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.notNull;
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
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;
import haushaltsbuch.web.EntryMapper;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.InsertController;

public class InsertControllerTest extends InsertController
{
  private EntryRepository _repository;
  private EntryMapper _mockEntryMapper;

  @Before
  public void setUp() throws Exception
  {
    _repository = mock(EntryRepository.class);

    ServletConfig servletConfig = mock(ServletConfig.class);
    ServletContext servletContext = mock(ServletContext.class);

    when(servletConfig.getServletContext()).thenReturn(servletContext);
    when(servletContext.getAttribute(BaseController.CTX_ATTR_NAME)).thenReturn(_repository);

    init(servletConfig);

    _mockEntryMapper = mock(EntryMapper.class);
    setEntryMapper(_mockEntryMapper);
  }

  @Test
  public void testCategories() throws ServletException, IOException, InsertException, LookupException
  {
    ServletContext servletContext = mock(ServletContext.class);
    when(servletContext.getContextPath()).thenReturn("/unit-test");

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletContext()).thenReturn(servletContext);
    when(request.getRequestURI()).thenReturn("/entries/delete");

    HttpServletResponse response = mock(HttpServletResponse.class);

    Entry blankEntry = mock(Entry.class);
    when(_mockEntryMapper.getBlankEntry()).thenReturn(blankEntry);

    doGet(request, response);

    verify(request).setAttribute(eq("entry"), eq(blankEntry));
    verify(request).setAttribute(eq("categories"), notNull());
  }

  @Test
  public void testInsert() throws ServletException, IOException, InsertException, LookupException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    Entry insertEntry = mock(Entry.class);
    when(_mockEntryMapper.map(request)).thenReturn(insertEntry);
    when(_repository.insert(insertEntry)).thenReturn("4711");

    Entry readbackEntry = mock(Entry.class);
    when(_repository.lookup("4711")).thenReturn(readbackEntry);

    doPost(request, response);

    verify(request).setAttribute(eq("entry"), eq(readbackEntry));
    verify(request).setAttribute(eq("message"), contains("angelegt"));
  }
}
