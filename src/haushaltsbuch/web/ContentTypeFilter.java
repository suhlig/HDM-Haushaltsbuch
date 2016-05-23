package haushaltsbuch.web;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class ContentTypeFilter extends BasicFilter
{
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    if (null == request.getCharacterEncoding())
      request.setCharacterEncoding("UTF-8");

    if (null == response.getContentType())
      response.setContentType("text/html; charset=utf-8");

    response.setLocale(Locale.GERMANY);

    chain.doFilter(request, response);
  }
}
