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

  protected EntryRepository getRepository() throws ServletException
  {
    ServletContext servletContext = getServletContext();
    EntryRepository repository = (EntryRepository) servletContext.getAttribute(EntryRepository.CTX_ATTR_NAME);

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

  protected void setView(HttpServletRequest request, String view, Object... args)
  {
    request.setAttribute("view", MessageFormat.format(view, args));
  }
}
