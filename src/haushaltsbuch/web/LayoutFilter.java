package haushaltsbuch.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class LayoutFilter extends BasicFilter
{
  private static final String[] ELIGIBLE_ATTRIBUTE_NAMES = new String[] { "view", "error", "message" };
  private final static Set<String> ELIGIBLE_ATTRIBUTES = new HashSet<String>(Arrays.asList(ELIGIBLE_ATTRIBUTE_NAMES));

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    chain.doFilter(request, response);

    if (hasLayout(request))
      request.getRequestDispatcher("/WEB-INF/jsp/layout.jsp").forward(request, response);
  }

  private boolean hasLayout(ServletRequest request)
  {
    Enumeration<String> attributeNames = request.getAttributeNames();

    while (attributeNames.hasMoreElements())
      if (ELIGIBLE_ATTRIBUTES.contains(attributeNames.nextElement()))
        return true;

    return false;
  }
}
