package haushaltsbuch.web.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.DeleteException;
import haushaltsbuch.Entry;

@WebServlet("/delete")
public class DeleteController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String id = request.getParameter("id");

    if (null == id || id.isEmpty())
    {
      setError(request, "fehlender Identifikator");
      setTitle(request, "Fehler beim Löschen");

      response.setStatus(400);
    }
    else
      try
      {
        Entry entry = getRepository().delete(id);

        if (null == entry)
        {
          setError(request, "Es konnte kein Eintrag mit dem Identifikator {0} gefunden werden.", id);
          setTitle(request, "Fehler beim Löschen");

          response.setStatus(404);
        }
        else
        {
          request.setAttribute("entry", entry);

          setMessage(request, "Eintrag gelöscht.");
          setTitle(request, "Eintrag gelöscht");
          setView(request, "deleted.jsp");
        }
      }
      catch (DeleteException e)
      {
        e.printStackTrace(System.err);
        setError(request, "Fehler beim Löschen von {0}: {1}", id, e.getMessage());
        setTitle(request, "Fehler beim Löschen");

        response.setStatus(500);
      }
  }
}
