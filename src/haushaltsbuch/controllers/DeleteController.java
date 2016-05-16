package haushaltsbuch.controllers;

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
    response.setContentType("text/html; charset=utf-8");

    Entry entry;

    try
    {
      String id = request.getParameter("id");
      entry = getRepository().delete(id);

      request.setAttribute("entry", entry);
      request.setAttribute("message", "Successfully deleted " + entry);

      request.getRequestDispatcher("WEB-INF/jsp/deleted.jsp").include(request, response);
    }
    catch (DeleteException e)
    {
      e.printStackTrace(System.err);
      request.setAttribute("message", "Fehler beim löschen: " + e.getMessage());
      request.getRequestDispatcher("WEB-INF/jsp/list.jsp").include(request, response);
    }
  }
}
