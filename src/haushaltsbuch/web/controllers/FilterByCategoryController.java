package haushaltsbuch.web.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;

@WebServlet("entries/by-category")
public class FilterByCategoryController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String category = request.getParameter("name");

    if (null == category || category.isEmpty())
    {
      setTitle(request, "Fehler");
      setError(request, "Name der Kategorie fehlt");
      response.setStatus(400);

      return;
    }

    List<Entry> entries_by_category = getRepository().by_category(category);

    request.setAttribute("entries", entries_by_category);
    setTitle(request, "Einträge in der Kategorie ''{0}''", category);
    setView(request, "entries/all.jsp");
  }
}
