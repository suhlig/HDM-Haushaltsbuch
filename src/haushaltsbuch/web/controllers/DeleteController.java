package haushaltsbuch.web.controllers;

import java.io.IOException;
import java.text.MessageFormat;
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
      request.setAttribute("error", "fehlender");
      response.setStatus(400);

      setTitle(request, "Fehler beim Löschen");
      setView(request, "error.jsp");
    }
    else
      try
      {
        Entry entry = getRepository().delete(id);

        if (null == entry)
        {
          response.setStatus(404);
          request.setAttribute("error", MessageFormat.format("Eintrag mit dem Identifikator {0} konnte nicht gefunden werden.", id));
          setTitle(request, "Fehler beim Löschen");
          setView(request, "error.jsp");
        }
        else
        {
          request.setAttribute("entry", entry);
          request.setAttribute("message", MessageFormat.format("Eintrag {0} erfolgreich gelöscht.", entry));
          setTitle(request, "Eintrag gelöscht");
          setView(request, "deleted.jsp");
        }
      }
      catch (DeleteException e)
      {
        e.printStackTrace(System.err);
        request.setAttribute("error", MessageFormat.format("Fehler beim Löschen von {0}: {1}", id, e.getMessage()));
        response.setStatus(500);
        setTitle(request, "Fehler beim Löschen");
        setView(request, "error.jsp");
      }
  }
}
