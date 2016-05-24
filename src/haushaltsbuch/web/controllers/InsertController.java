package haushaltsbuch.web.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.FindException;
import haushaltsbuch.InsertException;
import haushaltsbuch.web.EntryMapper;

@WebServlet("/new")
public class InsertController extends BaseController
{
  private static final long serialVersionUID = 1L;
  private EntryMapper _entryMapper = new EntryMapper();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setAttribute("entry", _entryMapper.map(request));
    setTitle(request, "Neuen Eintrag hinzufügen");
    setView(request, "new.jsp");
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
      setMessage(request, "Eintrag erfolgreich angelegt.");

      setTitle(request, "Eintrag hinzugefügt");
      setView(request, "show.jsp");
    }
    catch (InsertException e)
    {
      e.printStackTrace(System.err);

      request.setAttribute("entry", entry);

      setError(request, e.getMessage());
      setTitle(request, "Fehler beim Anlegen");
      setView(request, "new.jsp");

      response.setStatus(500);
    }
    catch (FindException e)
    {
      e.printStackTrace(System.err);

      request.setAttribute("entry", entry);

      setError(request, e.getMessage());
      setTitle(request, "Fehler beim Lesen");
      setView(request, "new.jsp");

      response.setStatus(500);
    }
  }

  protected void setEntryMapper(EntryMapper entryMapper)
  {
    _entryMapper = entryMapper;
  }
}
