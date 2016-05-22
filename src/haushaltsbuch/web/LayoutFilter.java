package haushaltsbuch.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class LayoutFilter implements Filter
{
  private Set<String> _eligible;

  public void destroy()
  {
    // this space intentionally left blank
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    chain.doFilter(request, response);

    if (hasLayout(request))
      request.getRequestDispatcher("WEB-INF/jsp/layout.jsp").include(request, response);
  }

  public void init(FilterConfig ignored) throws ServletException
  {
    _eligible = new HashSet<String>(Arrays.asList(new String[] { "view", "error", "message" }));
  }

  private boolean hasLayout(ServletRequest request)
  {
    Enumeration<String> attributeNames = request.getAttributeNames();

    while (attributeNames.hasMoreElements())
      if (_eligible.contains(attributeNames.nextElement()))
        return true;

    return false;
  }
}
