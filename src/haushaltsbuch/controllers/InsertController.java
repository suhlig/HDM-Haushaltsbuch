package haushaltsbuch.controllers;

import java.io.IOException;
import java.text.MessageFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;

@WebServlet("/new")
public class InsertController extends BaseController
{
  private static final long serialVersionUID = 1L;
  private EntryMapper _entryMapper = new EntryMapper();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = _entryMapper.map(request);
    request.setAttribute("entry", entry);
    request.getRequestDispatcher("WEB-INF/jsp/add.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = _entryMapper.map(request);
    EntryRepository repository = getRepository();

    try
    {
      String id = repository.insert(entry);

      request.setAttribute("entry", repository.find(id)); // read back so that we know generated fields, too
      request.setAttribute("message", MessageFormat.format("Eintrag {0} erfolgreich angelegt.", entry));
      request.getRequestDispatcher("WEB-INF/jsp/show.jsp").include(request, response);
    }
    catch (InsertException e)
    {
      e.printStackTrace(System.err);
      request.setAttribute("error", "Fehler beim Anlegen: " + e.getMessage());
      request.setAttribute("entry", entry);
      request.getRequestDispatcher("WEB-INF/jsp/add.jsp").include(request, response);
    }
  }

  protected void setEntryMapper(EntryMapper entryMapper)
  {
    _entryMapper = entryMapper;
  }
}
