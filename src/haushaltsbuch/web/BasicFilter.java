package haushaltsbuch.web;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public abstract class BasicFilter implements Filter
{
  public void destroy()
  {
    // this space intentionally left blank
  }

  public void init(FilterConfig ignored) throws ServletException
  {
    // this space intentionally left blank
  }
}