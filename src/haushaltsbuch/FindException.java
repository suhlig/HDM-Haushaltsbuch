package haushaltsbuch;

import java.text.MessageFormat;

public class FindException extends Exception
{
  private static final long serialVersionUID = 1L;
  private final String _id;
  private final Exception _cause;

  public FindException(String id, Exception cause)
  {
    _id = id;
    _cause = cause;
  }

  @Override
  public String getMessage()
  {
    return MessageFormat.format("Konnte den Eintrag mit der ID {0} nicht finden wegen des folgenden Fehlers: {1}", _id, _cause.getMessage());
  }
}
