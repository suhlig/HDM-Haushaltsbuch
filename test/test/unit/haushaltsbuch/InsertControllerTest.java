package test.unit.haushaltsbuch;

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
import haushaltsbuch.DeleteException;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
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
  public void testInsert() throws ServletException, IOException, DeleteException, InsertException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    Entry insertEntry = mock(Entry.class);
    when(_mockEntryMapper.map(request)).thenReturn(insertEntry);
    when(_repository.insert(insertEntry)).thenReturn("4711");

    Entry readbackEntry = mock(Entry.class);
    when(_repository.find("4711")).thenReturn(readbackEntry);

    doPost(request, response);

    verify(request).setAttribute(eq("entry"), eq(readbackEntry));
    verify(request).setAttribute(eq("message"), contains("angelegt"));
  }
}
