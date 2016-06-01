package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import haushaltsbuch.DeleteException;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.DeleteController;

public class DeleteControllerTest extends DeleteController
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
  public void testDelete() throws ServletException, IOException, DeleteException
  {
    ServletContext servletContext = mock(ServletContext.class);
    when(servletContext.getContextPath()).thenReturn("/unit-test");

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletContext()).thenReturn(servletContext);
    when(request.getParameter("id")).thenReturn("42");
    when(request.getRequestURI()).thenReturn("/entries/delete");

    Entry deletedEntry = mock(Entry.class);
    when(_repository.delete("42")).thenReturn(deletedEntry);

    HttpServletResponse response = mock(HttpServletResponse.class);

    doPost(request, response);

    verify(request).setAttribute("entry", deletedEntry);
    verify(request).setAttribute(eq("message"), contains("gelöscht"));
  }

  @Test
  public void testDeleteNotExisting() throws ServletException, IOException, DeleteException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("id")).thenReturn("42");
    when(_repository.delete("42")).thenReturn(null);

    doPost(request, response);

    verify(request).setAttribute(eq("error"), contains("gefunden"));
    verify(response).setStatus(404);
  }

  @Test
  public void testDeleteThrows() throws ServletException, IOException, DeleteException
  {
    ServletContext servletContext = mock(ServletContext.class);
    when(servletContext.getContextPath()).thenReturn("/unit-test");

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletContext()).thenReturn(servletContext);
    when(request.getParameter("id")).thenReturn("42");
    when(request.getRequestURI()).thenReturn("/entries/delete");

    mock(Entry.class);
    when(_repository.delete("11")).thenThrow(DeleteException.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    doPost(request, response);

    verify(response).setStatus(404);
    verify(request).setAttribute(eq("error"), contains("kein Eintrag"));
  }

  @Test
  public void testDeleteWithMissingParameter() throws ServletException, IOException, DeleteException
  {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    // id parameter not set
    when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(mock(RequestDispatcher.class));

    doPost(request, response);

    verify(response).setStatus(400);
    verify(request).setAttribute(eq("error"), contains("ehlender"));
  }
}
