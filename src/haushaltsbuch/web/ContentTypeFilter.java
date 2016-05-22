package haushaltsbuch.web;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class ContentTypeFilter implements Filter
{
  public void destroy()
  {
    // this space intentionally left blank
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    if (null == request.getCharacterEncoding())
      request.setCharacterEncoding("UTF-8");

    response.setContentType("text/html; charset=utf-8");
    response.setLocale(Locale.GERMANY);

    chain.doFilter(request, response);
  }

  public void init(FilterConfig ignored) throws ServletException
  {
    // this space intentionally left blank
  }
}
