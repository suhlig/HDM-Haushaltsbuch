package test.unit.haushaltsbuch.web;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.web.ContentTypeFilter;

public class ContentTypeFilterTest
{
  private ContentTypeFilter _subject;

  @Before
  public void setUp() throws Exception
  {
    _subject = new ContentTypeFilter();
  }

  @Test
  public void testKeepPresetCharacterEncoding() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);

    when(request.getCharacterEncoding()).thenReturn("UTF-42");

    _subject.doFilter(request, response, chain);

    verify(request, times(0)).setCharacterEncoding(anyString());
  }

  @Test
  public void testNoPresetCharacterEncoding() throws IOException, ServletException
  {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(ServletResponse.class);
    FilterChain chain = mock(FilterChain.class);

    _subject.doFilter(request, response, chain);

    verify(request).setCharacterEncoding("UTF-8");
  }
}
