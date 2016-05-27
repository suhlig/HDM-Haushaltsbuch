package haushaltsbuch.web.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;
import haushaltsbuch.LookupException;

@WebServlet("entries")
public class ShowController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String id = request.getParameter("id");

    if (null == id || id.isEmpty())
    {
      setError(request, "Fehlender Identifikator");
      setTitle(request, "Fehler");

      response.setStatus(400);
    }
    else
    {
      Entry entry;

      try
      {
        entry = getRepository().lookup(id);

        if (null == entry)
        {
          setError(request, "Es konnte kein Eintrag mit dem Identifikator {0} gefunden werden.", id);
          setTitle(request, "Fehler");

          response.setStatus(404);
        }
        else
        {
          request.setAttribute("entry", entry);
          setTitle(request, "Eintrag");
          setView(request, "entries/entry.jsp");
        }
      }
      catch (LookupException e)
      {
        setError(request, e.getMessage());
        setTitle(request, "Fehler beim Lesen");
        response.setStatus(500);
      }
    }
  }
}
