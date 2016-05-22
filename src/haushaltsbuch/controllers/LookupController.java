package haushaltsbuch.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lookup")
public class LookupController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    setTitle(request, "Eintrag nachschlagen");
    setView(request, "lookup.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String id = request.getParameter("id");

    if (null == id || id.isEmpty())
    {
      request.setAttribute("error", "Identifikator fehlt");
      request.setAttribute("id", id);
      response.setStatus(400);

      setTitle(request, "Fehler beim Nachschlagen");
      setView(request, "lookup.jsp");
    }
    else
      response.sendRedirect("show?id=" + id);
  }
}
