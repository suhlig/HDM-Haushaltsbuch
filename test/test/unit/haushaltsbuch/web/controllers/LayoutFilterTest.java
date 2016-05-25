package test.unit.haushaltsbuch.web.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.web.LayoutFilter;

public class LayoutFilterTest
{
  private LayoutFilter _subject;

  @Before
  public void setUp() throws Exception
  {
    _subject = new LayoutFilter();
  }

  @Test
  public void testError() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    when(request.getRequestDispatcher("WEB-INF/jsp/layout.jsp")).thenReturn(requestDispatcher);
    when(request.getAttributeNames()).thenReturn(asEnumeration("error"));

    _subject.doFilter(request, response, chain);

    verify(requestDispatcher).forward(request, response);
  }

  @Test
  public void testMessage() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    when(request.getRequestDispatcher("WEB-INF/jsp/layout.jsp")).thenReturn(requestDispatcher);
    when(request.getAttributeNames()).thenReturn(asEnumeration("message"));

    _subject.doFilter(request, response, chain);

    verify(requestDispatcher).forward(request, response);
  }

  @Test
  public void testMultipleAttributes() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    when(request.getRequestDispatcher("WEB-INF/jsp/layout.jsp")).thenReturn(requestDispatcher);
    when(request.getAttributeNames()).thenReturn(asEnumeration("message", "view", "error"));

    _subject.doFilter(request, response, chain);

    verify(requestDispatcher).forward(request, response);
  }

  @Test
  public void testNoLayout() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    when(request.getRequestDispatcher("WEB-INF/jsp/layout.jsp")).thenReturn(requestDispatcher);
    when(request.getAttributeNames()).thenReturn(Collections.emptyEnumeration());

    _subject.doFilter(request, response, chain);

    verifyZeroInteractions(requestDispatcher);
  }

  @Test
  public void testView() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    when(request.getRequestDispatcher("WEB-INF/jsp/layout.jsp")).thenReturn(requestDispatcher);
    when(request.getAttributeNames()).thenReturn(asEnumeration("view"));

    _subject.doFilter(request, response, chain);

    verify(requestDispatcher).forward(request, response);
  }

  private Enumeration<String> asEnumeration(String... names)
  {
    return Collections.enumeration(Arrays.asList(names));
  }
}
