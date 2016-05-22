package haushaltsbuch;

import java.text.MessageFormat;

public class InsertException extends Exception
{
  private static final long serialVersionUID = 1L;

  private final Exception _cause;

  public InsertException(Entry entry, Exception cause)
  {
    _cause = cause;
  }

  @Override
  public String getMessage()
  {
    return MessageFormat.format("Konnte den Eintrag nicht einfügen wegen des folgenden Fehlers: {0}", _cause.getMessage());
  }
}
