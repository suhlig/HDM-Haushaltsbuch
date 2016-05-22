package haushaltsbuch.web.controllers;

import java.io.IOException;
import java.text.MessageFormat;
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
      request.setAttribute("error", "Fehlender Identifikator");
      response.setStatus(400);
      setTitle(request, "Fehler");
      setView(request, "error.jsp");
    }
    else
    {
      Entry entry = getRepository().find(id);

      if (null == entry)
      {
        response.setStatus(404);
        request.setAttribute("error", MessageFormat.format("Eintrag mit dem Identifikator {0} konnte nicht gefunden werden.", id));
        setTitle(request, "Fehler");
        setView(request, "error.jsp");
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
