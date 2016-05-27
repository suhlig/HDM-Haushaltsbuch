package haushaltsbuch.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;

@WebServlet("entries/lookup")
public class LookupController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    setTitle(request, "Eintrag nachschlagen");
    setView(request);
    request.setAttribute("ids", getIDs());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String id = request.getParameter("id");

    if (null == id || id.isEmpty())
    {
      setTitle(request, "Fehler beim Nachschlagen");
      setError(request, "Identifikator fehlt");
      setView(request);
      response.setStatus(400);
    }
    else
      response.sendRedirect("/entries?id=" + id);
  }

  private List<String> getIDs() throws ServletException
  {
    List<Entry> all = getRepository().all();
    List<String> result = new ArrayList<>(all.size());

    for (Entry entry : all)
      result.add(entry.getId());

    return result;
  }
}
