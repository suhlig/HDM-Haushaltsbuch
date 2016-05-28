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
import haushaltsbuch.EntryRepository;
import haushaltsbuch.LookupException;
import haushaltsbuch.web.controllers.BaseController;
import haushaltsbuch.web.controllers.CategoriesController;

public class CategoriesControllerTest extends CategoriesController
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
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/categories/all");

    HttpServletResponse response = mock(HttpServletResponse.class);

    List<String> categories = Arrays.asList(new String[] { "Essen", "Trinken", "Kino" });

    when(_repository.categories()).thenReturn(categories);

    doGet(request, response);

    verify(request).setAttribute(eq("title"), anyString());
    verify(request).setAttribute(eq("categories"), eq(categories));
    verify(request).setAttribute(eq("view"), eq("categories/all.jsp"));
  }
}
