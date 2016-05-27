package haushaltsbuch.web.controllers;

import java.text.MessageFormat;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import haushaltsbuch.EntryRepository;

public abstract class BaseController extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  public static final String CTX_ATTR_NAME = "repository";

  protected EntryRepository getRepository() throws ServletException
  {
    ServletContext servletContext = getServletContext();
    EntryRepository repository = (EntryRepository) servletContext.getAttribute(BaseController.CTX_ATTR_NAME);

    if (null == repository)
      throw new ServletException("Unable to find the repository");

    return repository;
  }

  protected void setError(HttpServletRequest request, String error, Object... args)
  {
    request.setAttribute("error", MessageFormat.format(error, args));
  }

  protected void setMessage(HttpServletRequest request, String message, Object... args)
  {
    request.setAttribute("message", MessageFormat.format(message, args));
  }

  protected void setTitle(HttpServletRequest request, String title, Object... args)
  {
    request.setAttribute("title", MessageFormat.format(title, args));
  }

  /**
   * Infer the view name from the request URI. A request to <code>/entries/all</code>
   * will resolve to a <code>view = entries/all.jsp</code> attribute.
   */
  protected void setView(HttpServletRequest request)
  {
    String view = request.getRequestURI().substring(1);

    if (!view.endsWith(".jsp"))
      view = view + ".jsp";

    request.setAttribute("view", view);
  }

  protected void setView(HttpServletRequest request, String view, Object... args)
  {
    request.setAttribute("view", MessageFormat.format(view, args));
  }
}
