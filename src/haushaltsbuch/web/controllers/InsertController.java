package haushaltsbuch.web.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;
import haushaltsbuch.web.EntryMapper;

@WebServlet("/new")
public class InsertController extends BaseController
{
  private static final long serialVersionUID = 1L;
  private EntryMapper _entryMapper = new EntryMapper();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setAttribute("entry", new BlankEntry());
    setTitle(request, "Neuen Eintrag hinzufügen");
    setView(request, "new.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Candidate<Entry> entry = _entryMapper.map(request);

    if (!entry.isValid())
    {
      request.setAttribute("entry", entry);
      request.setAttribute("validationErrors", entry.getValidationErrors());

      System.err.println("srcDst has an error: " + entry.getValidationErrors());

      setError(request, "Der Eintrag ist nicht vollständig oder enthält ungültige Angaben.");
      setTitle(request, "Fehler beim Anlegen");
      setView(request, "new.jsp");

      return;
    }

    EntryRepository repository = getRepository();

    try
    {
      String id = repository.insert(entry);

      request.setAttribute("entry", repository.lookup(id)); // read back so that we know generated fields, too
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
    catch (LookupException e)
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
