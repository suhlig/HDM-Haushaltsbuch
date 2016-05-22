package haushaltsbuch.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class LayoutFilter implements Filter
{
  public void destroy()
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    chain.doFilter(request, response);

    // we only do HTTP
    ((HttpServletRequest) request).getRequestDispatcher("WEB-INF/jsp/layout.jsp").include(request, response);
  }

  public void init(FilterConfig ignored) throws ServletException
  {
  }
}
