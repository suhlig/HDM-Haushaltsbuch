package haushaltsbuch;

import java.text.MessageFormat;

public class InsertException extends Exception
{
  private static final long serialVersionUID = 1L;

  private final Entry _entry;
  private final Exception _cause;

  public InsertException(Entry entry, Exception cause)
  {
    _entry = entry;
    _cause = cause;
  }

  @Override
  public String getMessage()
  {
    return MessageFormat.format("Konnte den Eintrag {0} nicht einfügen wegen des folgenden Fehlers: {1}", _entry, _cause.getMessage());
  }
}
