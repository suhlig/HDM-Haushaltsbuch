package haushaltsbuch;

import java.text.MessageFormat;

public class DeleteException extends Exception
{
  private static final long serialVersionUID = 1L;

  private final Exception _cause;
  private final String _id;

  public DeleteException(String id, Exception cause)
  {
    _id = id;
    _cause = cause;
  }

  @Override
  public String getMessage()
  {
    return MessageFormat.format("Konnte den Eintrag mit der ID {0} nicht löschen wegen des folgenden Fehlers: {1}", _id, _cause.getMessage());
  }
}
