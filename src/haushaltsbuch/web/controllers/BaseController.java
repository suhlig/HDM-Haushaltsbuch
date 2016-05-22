package haushaltsbuch.web.controllers;

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

  protected void setTitle(HttpServletRequest request, String title)
  {
    request.setAttribute("title", title);
  }

  protected void setView(HttpServletRequest request, String view)
  {
    request.setAttribute("view", view);
  }
}