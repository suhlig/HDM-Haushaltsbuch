package haushaltsbuch.web.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;

@WebServlet("/show")
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
      response.setStatus(400);
      setTitle(request, "Fehler");
    }
    else
    {
      Entry entry = getRepository().find(id);

      if (null == entry)
      {
        response.setStatus(404);
        setError(request, "Es konnte kein Eintrag mit dem Identifikator {0} gefunden werden.", id);
        setTitle(request, "Fehler");
      }
      else
      {
        request.setAttribute("entry", entry);
        setTitle(request, "Eintrag");
        setView(request, "show.jsp");
      }
    }
  }
}
