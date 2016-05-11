package haushaltsbuch.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import haushaltsbuch.persistence.EntryRepository;

public abstract class BaseController extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  protected EntryRepository getRepository() throws ServletException
  {
    EntryRepository repository = (EntryRepository) getServletContext().getAttribute(EntryRepository.CTX_ATTR_NAME);

    if (null == repository)
      throw new ServletException("Unable to find the repository");

    return repository;
  }
}